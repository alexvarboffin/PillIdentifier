package com.walhalla.lib.datamodel.rxterms.response

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class RxTermsAllConceptsResponse(
    @SerializedName("rxtermsConceptList")
    @Expose
    val rxtermsConceptList: RxtermsConceptList? = null
)

@Keep
data class RxtermsConceptList(
    @SerializedName("rxtermsConcept")
    @Expose
    val rxtermsConcept: List<RxtermsConcept> = emptyList()
)

@Keep
data class RxtermsConcept(
    @SerializedName("rxcui")
    @Expose
    val rxcui: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("synonym")
    @Expose
    val synonym: String? = null,
    @SerializedName("termType")
    @Expose
    val termType: String? = null
)

@Keep
data class RxTermsDisplayNameResponse(
    @SerializedName("rxtermsDisplayName")
    @Expose
    val rxtermsDisplayName: String? = null
)

@Keep
data class RxTermsVersionResponse(
    @SerializedName("version")
    @Expose
    val version: String? = null
) 