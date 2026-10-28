package com.walhalla.lib.service

import com.walhalla.pillfinder.Constants
import gov.fda.openfda.apis.DefaultApi
import gov.fda.openfda.infrastructure.ApiClient
import gov.fda.openfda.models.DrugLabelResponse
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.CancellationException

/**
 * openFDA REST client backed by the generated KMP library :network:openfda-kmp (gov.fda.openfda).
 * Spec: C:/Users/combo/Desktop/OpenFDA-OpenAPI/openapi.json (built from FDA/openfda schemas/druglabel_schema.json).
 * Replaces the hand-written Retrofit API2 (drug/label.json).
 */
object OpenFdaLib {

    private const val TIMEOUT_MS: Int = (Constants.KEY_TIMEOUT * 1000L).toInt()

    @JvmStatic
    val api: DefaultApi by lazy {
        DefaultApi(
            baseUrl = ApiClient.BASE_URL, // https://api.fda.gov
            httpClientEngine = Android.create {
                connectTimeout = TIMEOUT_MS
                socketTimeout = TIMEOUT_MS
            },
            httpClientConfig = { config -> config.followRedirects = true }
        )
    }

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
}