package com.walhalla.lib.service

import com.walhalla.pillfinder.Constants
import gov.nih.nlm.rxnorm.apis.DefaultApi
import gov.nih.nlm.rxnorm.infrastructure.ApiClient
import gov.nih.nlm.rxnorm.infrastructure.HttpResponse
import gov.nih.nlm.rxnorm.models.AllSourcesFlag
import gov.nih.nlm.rxnorm.models.SearchMode
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
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
            fetch {
                findRxcui(
                    name = query,
                    search = SearchMode._2,
                    allsrc = AllSourcesFlag._1,
                    srclist = null // old request did not send srclist
                )
            }.onSuccess { body -> callback?.successResponse(body) }
                .onFailure { t -> callback?.handleThrowable(t) }
        }
    }
}
