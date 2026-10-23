package com.walhalla.lib.service

import com.walhalla.pillfinder.Constants
import gov.nih.nlm.rxnorm.apis.DefaultApi
import gov.nih.nlm.rxnorm.infrastructure.ApiClient
import gov.nih.nlm.rxnorm.infrastructure.HttpResponse
import gov.nih.nlm.rxnorm.models.AllSourcesFlag
import gov.nih.nlm.rxnorm.models.ApproximateGroupResponse
import gov.nih.nlm.rxnorm.models.ApproximateOption
import gov.nih.nlm.rxnorm.models.IdGroupResponse
import gov.nih.nlm.rxnorm.models.MinConceptGroupResponse
import gov.nih.nlm.rxnorm.models.NdcInfoListResponse
import gov.nih.nlm.rxnorm.models.NdcPropertyListResponse
import gov.nih.nlm.rxnorm.models.NdcRelation
import gov.nih.nlm.rxnorm.models.NdcStatusResponse
import gov.nih.nlm.rxnorm.models.NdcStatusFilter
import gov.nih.nlm.rxnorm.models.PropConceptGroupResponse
import com.walhalla.pillfinder.fragment.api2.PrescribeMode
import gov.nih.nlm.rxnorm.models.SearchMode
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * RxNorm REST client backed by the generated KMP library :network:lk0-kmp (gov.nih.nlm.rxnorm).
 * Replaces the hand-written Retrofit RxnormApi methods for RxNorm endpoints.
 * RxClass / RxTerms / Prescribable / Interaction stay on Retrofit (not covered by the library).
 */
object RxNormLib {

    private const val TIMEOUT_MS: Int = (Constants.KEY_TIMEOUT * 1000L).toInt()

    /** Same base URL and timeouts as the old Retrofit client (RxnormRepository.ENDPOINT_RXNAV, Constants.KEY_TIMEOUT). */
    @JvmStatic
    val api: DefaultApi by lazy {
        DefaultApi(
            baseUrl = ApiClient.BASE_URL, // https://rxnav.nlm.nih.gov/REST
            httpClientEngine = Android.create {
                connectTimeout = TIMEOUT_MS
                socketTimeout = TIMEOUT_MS
            },
            httpClientConfig = { config ->
                config.followRedirects = true
            }
        )
    }

    /** Retrofit-like contract: non-2xx -> body null; network/parse errors -> failure. */
    suspend fun <T : Any> fetch(block: suspend DefaultApi.() -> HttpResponse<T>): Result<T?> =
        try {
            val response = api.block()
            Result.success(if (response.success) response.body() else null)
        } catch (e: CancellationException) {
            throw e
        } catch (t: Throwable) {
            Result.failure(t)
        }

    private val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    /**
     * Was RxnormApi.searchRxcui(name, search = 2, allsrc = 1) -> rxcui.json.
     * Callback is delivered on the main thread, like Retrofit enqueue().
     */
    @JvmStatic
    fun globalRequest(query: String?, callback: RxnormRepositoryCallback?) {
        mainScope.launch {
//            fetch {
//                findRxcui(
//                    name = query,
//                    search = SearchMode._2,
//                    allsrc = AllSourcesFlag._1,
//                    srclist = null // old request did not send srclist
//                )
//            }.onSuccess
            // roadmap #12: Prescribe switch ON -> /Prescribe/rxcui.json (same params), OFF -> unchanged
            fetch {
                if (PrescribeMode.enabled) prescribeFindRxcui(
                    name = query,
                    search = SearchMode._2,
                    allsrc = AllSourcesFlag._1,
                    srclist = null
                ) else findRxcui(
                    name = query,
                    search = SearchMode._2,
                    allsrc = AllSourcesFlag._1,
                    srclist = null // old request did not send srclist
                )
            }.onSuccess { body -> callback?.successResponse(body) }
                .onFailure { t -> callback?.handleThrowable(t) }
        }
    }

    /** Result callback for [ndcProperties]; delivered on the main thread. */
    interface NdcPropertiesCallback {
        /** body == null when the server answered non-2xx (same contract as [fetch]). */
        fun onResult(body: NdcPropertyListResponse?)
        fun onError(t: Throwable)
    }

    /**
     * getNDCProperties -> /ndcproperties.json?id={ndc}[&ndcstatus=ALL] (roadmap item #1, NDC pill card).
     * @param allStatuses true for historical/obsolete NDCs (ndcstatus=ALL), false = API default (active).
     * @return Job so the caller can cancel when its UI goes away.
     */
    @JvmStatic
    fun ndcProperties(ndc: String, allStatuses: Boolean, callback: NdcPropertiesCallback): Job =
        mainScope.launch {
            fetch {
                getNDCProperties(
                    id = ndc,
                    ndcstatus = listOf(if (allStatuses) NdcStatusFilter.ALL else NdcStatusFilter.active)
                )
            }.onSuccess { body -> callback.onResult(body) }
                .onFailure { t -> callback.onError(t) }
        }

    /** Generic main-thread result callback (roadmap item #2). body == null when the server answered non-2xx. */
    interface LibCallback<T> {
        fun onResult(body: T?)
        fun onError(t: Throwable)
    }

    private fun <T : Any> launchWithCallback(
        callback: LibCallback<T>,
        block: suspend gov.nih.nlm.rxnorm.apis.DefaultApi.() -> gov.nih.nlm.rxnorm.infrastructure.HttpResponse<T>
    ): Job = mainScope.launch {
        fetch(block)
            .onSuccess { body -> callback.onResult(body) }
            .onFailure { t -> callback.onError(t) }
    }

    /** getNDCStatus -> /ndcstatus.json?ndc={ndc} (API defaults: history=0, altpkg=0; ndcHistory is still returned). */
    @JvmStatic
    fun ndcStatus(ndc: String, callback: LibCallback<NdcStatusResponse>): Job =
        launchWithCallback(callback) { getNDCStatus(ndc = ndc) }

    /** findRelatedNDCs -> /relatedndc.json?ndc={ndc}&relation={relation} (ndcstatus = API default: active). */
    @JvmStatic
    fun relatedNdcs(ndc: String, relation: NdcRelation, callback: LibCallback<NdcInfoListResponse>): Job =
        launchWithCallback(callback) { findRelatedNDCs(ndc = ndc, relation = relation) }

    /**
     * Roadmap item #3. Raw rows are atoms (one rxcui repeats per source), and the API cuts at rank ties:
     * live, maxEntries=20 gave only 1-2 distinct rxcuis for "rifampn 150" / "rifampin 150 capsule",
     * 50 gives 8-9, which is what the "closest matches" list needs.
     */
    const val APPROX_MAX_ENTRIES: Int = 50

    /** getApproximateMatch -> /approximateTerm.json?term=..&maxEntries=50&option=1 (option 1 = active RxNorm concepts only, so every match opens in the drug tabs). */
    @JvmStatic
    fun approximateMatch(term: String, callback: LibCallback<ApproximateGroupResponse>): Job =
        launchWithCallback(callback) {
            //getApproximateMatch(term = term, maxEntries = APPROX_MAX_ENTRIES, option = ApproximateOption._1)
            // roadmap #12: Prescribe switch ON -> /Prescribe/approximateTerm.json (same params)
            if (PrescribeMode.enabled) prescribeGetApproximateMatch(term = term, maxEntries = APPROX_MAX_ENTRIES, option = ApproximateOption._1)
            else getApproximateMatch(term = term, maxEntries = APPROX_MAX_ENTRIES, option = ApproximateOption._1)
        }

    /** getRxNormName -> /rxcui/{rxcui}.json, idGroup.name (canonical RxNorm name). */
    @JvmStatic
    fun rxNormName(rxcui: String, callback: LibCallback<IdGroupResponse>): Job =
        launchWithCallback(callback) { getRxNormName(rxcui = rxcui) }

    /** getGenericProduct -> /rxcui/{rxcui}/generic.json (roadmap #4). Returns the concept itself for generics. */
    @JvmStatic
    fun genericProduct(rxcui: String, callback: LibCallback<MinConceptGroupResponse>): Job =
        launchWithCallback(callback) { getGenericProduct(rxcui = rxcui) }

    /**
     * getRxProperty -> /rxcui/{rxcui}/property.json?propName={propName} (roadmap #6, PRESCRIBABLE badge).
     * Concepts without the property answer {} (propConceptGroup == null). Always the standard API (badge is not mode dependent).
     */
    @JvmStatic
    fun rxProperty(rxcui: String, propName: String, callback: LibCallback<PropConceptGroupResponse>): Job =
        launchWithCallback(callback) { getRxProperty(rxcui = rxcui, propName = propName) }
}
