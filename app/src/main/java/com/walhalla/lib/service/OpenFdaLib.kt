package com.walhalla.lib.service

import com.walhalla.pillfinder.BuildConfig
import com.walhalla.pillfinder.Constants
import gov.fda.openfda.apis.DefaultApi
import gov.fda.openfda.infrastructure.ApiClient
import gov.fda.openfda.infrastructure.HttpResponse
import gov.fda.openfda.models.CountResult
import gov.fda.openfda.models.DrugLabel
import gov.fda.openfda.models.DrugLabelResponse
import gov.fda.openfda.models.DrugRecall
import gov.fda.openfda.models.NdcProduct
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.api.createClientPlugin
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

/**
 * openFDA REST client backed by the generated KMP library :network:openfda-kmp (gov.fda.openfda).
 * Spec: C:/Users/combo/Desktop/OpenFDA-OpenAPI/openapi.json (build_spec.py from FDA/openfda schemas:
 * druglabel, recall (enforcement), ndc + event count term/count).
 * Replaces the hand-written Retrofit API2 (drug/label.json).
 *
 * - optional api_key: BuildConfig.OPENFDA_API_KEY (gradle property OPENFDA_API_KEY, default empty = no key,
 *   1000 req/day per IP); appended as ?api_key= only when non-empty.
 * - 404 (openFDA "No matches found") = empty result, not an error.
 * - successful results are cached in memory per rxcui.
 */
object OpenFdaLib {

    private const val TIMEOUT_MS: Int = (Constants.KEY_TIMEOUT * 1000L).toInt()

    private val apiKey: String = BuildConfig.OPENFDA_API_KEY

    private val ApiKeyPlugin = createClientPlugin("OpenFdaApiKey") {
        onRequest { request, _ ->
            if (apiKey.isNotEmpty() && !request.url.parameters.contains("api_key")) {
                request.url.parameters.append("api_key", apiKey)
            }
        }
    }

    @JvmStatic
    val api: DefaultApi by lazy {
        DefaultApi(
            baseUrl = ApiClient.BASE_URL, // https://api.fda.gov
            httpClientEngine = Android.create {
                connectTimeout = TIMEOUT_MS
                socketTimeout = TIMEOUT_MS
            },
            httpClientConfig = { config ->
                config.followRedirects = true
                config.install(ApiKeyPlugin)
            }
        )
    }

    private val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val cache = ConcurrentHashMap<String, Any>()

    /** HTTP error other than 404 (e.g. 429 rate limit, 5xx). */
    class OpenFdaHttpException(val status: Int) : RuntimeException("openFDA HTTP $status")

    /** GET /drug/label.json?search={query}. Retrofit-like: non-2xx (e.g. 404 no matches) -> null body; network/parse errors -> failure. */
    suspend fun searchDrugLabel(query: String): Result<DrugLabelResponse?> =
        try {
            val response = api.searchDrugLabel(search = query)
            Result.success(if (response.success) response.body() else null)
        } catch (e: CancellationException) {
            throw e
        } catch (t: Throwable) {
            Result.failure(t)
        }

    private fun rxcuiQuery(field: String, rxcui: String) = "$field:\"$rxcui\""

    @Suppress("UNCHECKED_CAST")
    private suspend fun <B : Any, T> cached(
        key: String,
        call: suspend () -> HttpResponse<B>,
        extract: (B?) -> List<T>
    ): Result<List<T>> {
        (cache[key] as? List<T>)?.let { return Result.success(it) }
        return try {
            val r = call()
            val list: List<T> = when {
                r.success -> extract(r.body())
                r.status == 404 -> emptyList() // NOT_FOUND = "No matches found"
                else -> return Result.failure(OpenFdaHttpException(r.status))
            }
            cache[key] = list
            Result.success(list)
        } catch (e: CancellationException) {
            throw e
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    /** Newest SPL label for the rxcui (empty list = none). */
    suspend fun labelByRxcui(rxcui: String): Result<List<DrugLabel>> = cached("label:$rxcui",
        { api.searchDrugLabel(search = rxcuiQuery("openfda.rxcui", rxcui), sort = "effective_time:desc", limit = 1) },
        { it?.results.orEmpty() })

    /** Drug enforcement reports (recalls), newest first. */
    suspend fun recalls(rxcui: String): Result<List<DrugRecall>> = cached("recall:$rxcui",
        { api.searchDrugEnforcement(search = rxcuiQuery("openfda.rxcui", rxcui), sort = "recall_initiation_date:desc", limit = 20) },
        { it?.results.orEmpty() })

    /** NDC directory products (with packaging / package_ndc). */
    suspend fun ndcProducts(rxcui: String): Result<List<NdcProduct>> = cached("ndc:$rxcui",
        { api.searchDrugNdc(search = rxcuiQuery("openfda.rxcui", rxcui), limit = 20) },
        { it?.results.orEmpty() })

    /** FAERS top reported reactions (count mode, term/count). */
    suspend fun topReactions(rxcui: String, limit: Int = 10): Result<List<CountResult>> = cached("event:$rxcui:$limit",
        {
            api.countDrugEvents(
                count = "patient.reaction.reactionmeddrapt.exact",
                search = rxcuiQuery("patient.drug.openfda.rxcui", rxcui),
                limit = limit
            )
        },
        { it?.results.orEmpty() })

    /** Callback flavour for non-coroutine callers (DrugHeaderBar), same as RxNormLib. */
    fun recalls(rxcui: String, callback: RxNormLib.LibCallback<List<DrugRecall>>): Job = mainScope.launch {
        recalls(rxcui).fold({ callback.onResult(it) }, { callback.onError(it) })
    }
}
