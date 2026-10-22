package com.walhalla.lib.datamodel.rxnorm.response

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.walhalla.lib.datamodel.pkg_base.PropConcept

@Keep
data class RxNormAllStatusResponse(
    @SerializedName("statusGroup")
    @Expose
    val statusGroup: StatusGroup? = null
)

@Keep
data class StatusGroup(
    @SerializedName("status")
    @Expose
    val status: List<String> = emptyList()
)

@Keep
data class RxNormAllConceptsResponse(
    @SerializedName("conceptGroup")
    @Expose
    val conceptGroup: List<RxNormConceptGroup> = emptyList()
)

@Keep
data class RxNormConceptGroup(
    @SerializedName("tty")
    @Expose
    val tty: String? = null,
    @SerializedName("conceptProperties")
    @Expose
    val conceptProperties: List<RxNormConceptProperty> = emptyList()
)

@Keep
data class RxNormConceptProperty(
    @SerializedName("rxcui")
    @Expose
    val rxcui: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("synonym")
    @Expose
    val synonym: String? = null,
    @SerializedName("tty")
    @Expose
    val tty: String? = null,
    @SerializedName("language")
    @Expose
    val language: String? = null,
    @SerializedName("suppress")
    @Expose
    val suppress: String? = null,
    @SerializedName("umlscui")
    @Expose
    val umlscui: String? = null
)

@Keep
data class RxNormAllNDCStatusResponse(
    @SerializedName("ndcStatusGroup")
    @Expose
    val ndcStatusGroup: NdcStatusGroup? = null
)

@Keep
data class NdcStatusGroup(
    @SerializedName("ndcStatus")
    @Expose
    val ndcStatus: List<String> = emptyList()
)

@Keep
data class RxNormPropCategoriesResponse(
    @SerializedName("propCategoryList")
    @Expose
    val propCategoryList: RxNormPropCategoryList? = null
)

@Keep
data class RxNormPropCategoryList(
    @SerializedName("propCategory")
    @Expose
    val propCategory: List<String> = emptyList()
)

@Keep
data class RxNormPropNamesResponse(
    @SerializedName("propNameList")
    @Expose
    val propNameList: RxNormPropNameList? = null
)

@Keep
data class RxNormPropNameList(
    @SerializedName("propName")
    @Expose
    val propName: List<String> = emptyList()
)

@Keep
data class RxNormReformulationConceptsResponse(
    @SerializedName("reformulationConceptList")
    @Expose
    val reformulationConceptList: ReformulationConceptList? = null
)

@Keep
data class ReformulationConceptList(
    @SerializedName("reformulationConcept")
    @Expose
    val reformulationConcept: List<String> = emptyList()
)

@Keep
data class RxNormRelaPathsResponse(
    @SerializedName("relaPathList")
    @Expose
    val relaPathList: RxNormRelaPathList? = null
)

@Keep
data class RxNormRelaPathList(
    @SerializedName("relaPath")
    @Expose
    val relaPath: List<String> = emptyList()
)

@Keep
data class RxNormRelaTypesResponse(
    @SerializedName("relaTypeList")
    @Expose
    val relaTypeList: RxNormRelaTypeList? = null
)

@Keep
data class RxNormRelaTypeList(
    @SerializedName("relaType")
    @Expose
    val relaType: List<String> = emptyList()
)

@Keep
data class RxNormSourceTypesResponse(
    @SerializedName("sourceTypeList")
    @Expose
    val sourceTypeList: RxNormSourceTypeList? = null
)

@Keep
data class RxNormSourceTypeList(
    @SerializedName("sourceName")
    @Expose
    val sourceName: List<String> = emptyList()
)

@Keep
data class RxNormSpellingSuggestionsResponse(
    @SerializedName("suggestionGroup")
    @Expose
    val suggestionGroup: RxNormSuggestionGroup? = null
)

@Keep
data class RxNormSuggestionGroup(
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("suggestionList")
    @Expose
    val suggestionList: RxNormSuggestionList? = null
)

@Keep
data class RxNormSuggestionList(
    @SerializedName("suggestion")
    @Expose
    val suggestion: List<String> = emptyList()
)

@Keep
data class RxNormTermTypesResponse(
    @SerializedName("termTypeList")
    @Expose
    val termTypeList: RxNormTermTypeList? = null
)

@Keep
data class RxNormTermTypeList(
    @SerializedName("termType")
    @Expose
    val termType: List<String> = emptyList()
)

@Keep
data class RxNormVersionResponse(
    @SerializedName("version")
    @Expose
    val version: String? = null
)

@Keep
data class RxNormNDCsResponse(
    @SerializedName("ndcGroup")
    @Expose
    val ndcGroup: RxNormNdcGroup? = null
)

@Keep
data class RxNormNdcGroup(
    @SerializedName("ndcList")
    @Expose
    val ndcList: RxNormNdcList? = null,
    @SerializedName("rxcui")
    @Expose
    val rxcui: String? = null
)

@Keep
data class RxNormNdcList(
    @SerializedName("ndc")
    @Expose
    val ndc: List<String> = emptyList()
)

@Keep
data class RxNormAllRelatedResponse(
    @SerializedName("allRelatedGroup")
    @Expose
    val relatedGroup: RxNormRelatedGroup? = null
)

@Keep
data class RxNormRelatedGroup(
    @SerializedName("conceptGroup")
    @Expose
    val conceptGroup: List<RxNormRelatedConceptGroup> = emptyList()
)

@Keep
data class RxNormRelatedConceptGroup(
    @SerializedName("tty")
    @Expose
    val tty: String? = null,
    @SerializedName("conceptProperties")
    @Expose
    val conceptProperties: List<RxNormRelatedConceptProperty> = emptyList()
)

@Keep
data class RxNormRelatedConceptProperty(
    @SerializedName("rxcui")
    @Expose
    val rxcui: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("synonym")
    @Expose
    val synonym: String? = null,
    @SerializedName("tty")
    @Expose
    val tty: String? = null,
    @SerializedName("language")
    @Expose
    val language: String? = null,
    @SerializedName("suppress")
    @Expose
    val suppress: String? = null,
    @SerializedName("umlscui")
    @Expose
    val umlscui: String? = null
)

@Keep
data class RxNormPropertiesResponse(
    @SerializedName("properties")
    @Expose
    val properties: RxNormProperties? = null
)

@Keep
data class RxNormProperties(
    @SerializedName("rxcui")
    @Expose
    val rxcui: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("synonym")
    @Expose
    val synonym: String? = null,
    @SerializedName("tty")
    @Expose
    val tty: String? = null,
    @SerializedName("language")
    @Expose
    val language: String? = null,
    @SerializedName("suppress")
    @Expose
    val suppress: String? = null,
    @SerializedName("umlscui")
    @Expose
    val umlscui: String? = null
)

@Keep
data class RxNormNameResponse(
    @SerializedName("idGroup")
    @Expose
    val idGroup: RxNormNameIdGroup? = null
)

@Keep
data class RxNormNameIdGroup(
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("rxcui")
    @Expose
    val rxcui: String? = null
)

@Keep
data class RxNormPropertyResponse(
    @SerializedName("propValue")
    @Expose
    val propValue: String? = null
)

@Keep
data class RxNormRelatedResponse(
    @SerializedName("relatedGroup")
    @Expose
    val relatedGroup: RxNormRelatedGroup? = null
)

@Keep
data class RxNormProprietaryResponse(
    @SerializedName("proprietaryGroup")
    @Expose
    val proprietaryGroup: RxNormProprietaryGroup? = null
)

@Keep
data class RxNormProprietaryGroup(
    @SerializedName("conceptGroup")
    @Expose
    val conceptGroup: List<RxNormProprietaryConceptGroup> = emptyList()
)

@Keep
data class RxNormProprietaryConceptGroup(
    @SerializedName("tty")
    @Expose
    val tty: String? = null,
    @SerializedName("conceptProperties")
    @Expose
    val conceptProperties: List<RxNormProprietaryConceptProperty> = emptyList()
)

@Keep
data class RxNormProprietaryConceptProperty(
    @SerializedName("rxcui")
    @Expose
    val rxcui: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("synonym")
    @Expose
    val synonym: String? = null,
    @SerializedName("tty")
    @Expose
    val tty: String? = null,
    @SerializedName("language")
    @Expose
    val language: String? = null,
    @SerializedName("suppress")
    @Expose
    val suppress: String? = null,
    @SerializedName("umlscui")
    @Expose
    val umlscui: String? = null
)

// --- Дополнительные классы для RxNorm API ---

@Keep
data class RxNormHistoricalNDCsResponse(
    @SerializedName("historicalNdcConcept")
    @Expose
    val historicalNdcConcept: HistoricalNdcConcept? = null
)

@Keep
data class HistoricalNdcConcept(
    @SerializedName("historicalNdcTime")
    @Expose
    val historicalNdcTime: List<HistoricalNdcTime> = emptyList()
)

@Keep
data class HistoricalNdcTime(
    @SerializedName("status")
    @Expose
    val status: String? = null,
    @SerializedName("rxcui")
    @Expose
    val rxcui: String? = null,
    @SerializedName("ndcTime")
    @Expose
    val ndcTime: List<NdcTime> = emptyList()
)

@Keep
data class NdcTime(
    @SerializedName("ndc")
    @Expose
    val ndc: List<String> = emptyList(),
    @SerializedName("startDate")
    @Expose
    val startDate: String? = null,
    @SerializedName("endDate")
    @Expose
    val endDate: String? = null
)

@Keep
data class RxNormNDCPropertiesResponse(
    @SerializedName("ndcPropertyList")
    @Expose
    val ndcPropertyList: RxNormNdcPropertyList? = null
)

@Keep
data class RxNormNdcPropertyList(
    @SerializedName("ndcProperty")
    @Expose
    val ndcProperty: List<RxNormNdcProperty> = emptyList()
)

@Keep
data class RxNormNdcProperty(
    @SerializedName("ndc")
    @Expose
    val ndc: String? = null,
    @SerializedName("propertyName")
    @Expose
    val propertyName: String? = null,
    @SerializedName("propertyValue")
    @Expose
    val propertyValue: String? = null
)

@Keep
data class RxNormDrugsResponse(
    @SerializedName("drugGroup")
    @Expose
    val drugGroup: RxNormDrugGroup? = null
)

@Keep
data class RxNormDrugGroup(
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("conceptGroup")
    @Expose
    val conceptGroup: List<RxNormDrugConceptGroup> = emptyList()
)

@Keep
data class RxNormDrugConceptGroup(
    @SerializedName("tty")
    @Expose
    val tty: String? = null,
    @SerializedName("conceptProperties")
    @Expose
    val conceptProperties: List<RxNormDrugConceptProperty> = emptyList()
)

@Keep
data class RxNormDrugConceptProperty(
    @SerializedName("rxcui")
    @Expose
    val rxcui: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("synonym")
    @Expose
    val synonym: String? = null,
    @SerializedName("tty")
    @Expose
    val tty: String? = null,
    @SerializedName("language")
    @Expose
    val language: String? = null,
    @SerializedName("suppress")
    @Expose
    val suppress: String? = null,
    @SerializedName("umlscui")
    @Expose
    val umlscui: String? = null
)

@Keep
data class RxNormFilterResponse(
    @SerializedName("filterGroup")
    @Expose
    val filterGroup: RxNormFilterGroup? = null
)

@Keep
data class RxNormFilterGroup(
    @SerializedName("conceptGroup")
    @Expose
    val conceptGroup: List<RxNormFilterConceptGroup> = emptyList()
)

@Keep
data class RxNormFilterConceptGroup(
    @SerializedName("tty")
    @Expose
    val tty: String? = null,
    @SerializedName("conceptProperties")
    @Expose
    val conceptProperties: List<RxNormFilterConceptProperty> = emptyList()
)

@Keep
data class RxNormFilterConceptProperty(
    @SerializedName("rxcui")
    @Expose
    val rxcui: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("synonym")
    @Expose
    val synonym: String? = null,
    @SerializedName("tty")
    @Expose
    val tty: String? = null,
    @SerializedName("language")
    @Expose
    val language: String? = null,
    @SerializedName("suppress")
    @Expose
    val suppress: String? = null,
    @SerializedName("umlscui")
    @Expose
    val umlscui: String? = null
) 