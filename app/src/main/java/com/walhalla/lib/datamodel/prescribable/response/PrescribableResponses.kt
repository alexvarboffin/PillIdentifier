package com.walhalla.lib.datamodel.prescribable.response

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.walhalla.lib.datamodel.pkg_base.PropConcept

@Keep
data class PrescribableAllConceptsResponse(
    @SerializedName("conceptGroup")
    @Expose
    val conceptGroup: List<PrescribableConceptGroup> = emptyList()
)

@Keep
data class PrescribableConceptGroup(
    @SerializedName("tty")
    @Expose
    val tty: String? = null,
    @SerializedName("conceptProperties")
    @Expose
    val conceptProperties: List<PrescribableConceptProperty> = emptyList()
)

@Keep
data class PrescribableConceptProperty(
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
data class PrescribableApproximateMatchResponse(
    @SerializedName("approximateGroup")
    @Expose
    val approximateGroup: ApproximateGroup? = null
)

@Keep
data class ApproximateGroup(
    @SerializedName("candidate")
    @Expose
    val candidate: List<Candidate> = emptyList()
)

@Keep
data class Candidate(
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("score")
    @Expose
    val score: String? = null,
    @SerializedName("matchType")
    @Expose
    val matchType: String? = null
)

@Keep
data class PrescribableDisplayTermsResponse(
    @SerializedName("displayTermList")
    @Expose
    val displayTermList: DisplayTermList? = null
)

@Keep
data class DisplayTermList(
    @SerializedName("term")
    @Expose
    val term: List<String> = emptyList()
)

@Keep
data class PrescribableDrugsResponse(
    @SerializedName("drugGroup")
    @Expose
    val drugGroup: DrugGroup? = null
)

@Keep
data class DrugGroup(
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("conceptGroup")
    @Expose
    val conceptGroup: List<DrugConceptGroup> = emptyList()
)

@Keep
data class DrugConceptGroup(
    @SerializedName("tty")
    @Expose
    val tty: String? = null,
    @SerializedName("conceptProperties")
    @Expose
    val conceptProperties: List<DrugConceptProperty> = emptyList()
)

@Keep
data class DrugConceptProperty(
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
data class PrescribableIdTypesResponse(
    @SerializedName("idTypeList")
    @Expose
    val idTypeList: IdTypeList? = null
)

@Keep
data class IdTypeList(
    @SerializedName("idName")
    @Expose
    val idName: List<String> = emptyList()
)

@Keep
data class PrescribableMultiIngredBrandResponse(
    @SerializedName("brandGroup")
    @Expose
    val brandGroup: BrandGroup? = null
)

@Keep
data class BrandGroup(
    @SerializedName("brand")
    @Expose
    val brand: List<Brand> = emptyList()
)

@Keep
data class Brand(
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
data class PrescribableNDCsResponse(
    @SerializedName("ndcGroup")
    @Expose
    val ndcGroup: NdcGroup? = null
)

@Keep
data class NdcGroup(
    @SerializedName("ndc")
    @Expose
    val ndc: List<String> = emptyList()
)

@Keep
data class PrescribablePropCategoriesResponse(
    @SerializedName("propCategoryList")
    @Expose
    val propCategoryList: PropCategoryList? = null
)

@Keep
data class PropCategoryList(
    @SerializedName("propCategory")
    @Expose
    val propCategory: List<String> = emptyList()
)

@Keep
data class PrescribablePropNamesResponse(
    @SerializedName("propNameList")
    @Expose
    val propNameList: PropNameList? = null
)

@Keep
data class PropNameList(
    @SerializedName("propName")
    @Expose
    val propName: List<String> = emptyList()
)

@Keep
data class PrescribableRelaPathsResponse(
    @SerializedName("relaPathList")
    @Expose
    val relaPathList: RelaPathList? = null
)

@Keep
data class RelaPathList(
    @SerializedName("relaPath")
    @Expose
    val relaPath: List<String> = emptyList()
)

@Keep
data class PrescribableRelaTypesResponse(
    @SerializedName("relaTypeList")
    @Expose
    val relaTypeList: RelaTypeList? = null
)

@Keep
data class RelaTypeList(
    @SerializedName("relaType")
    @Expose
    val relaType: List<String> = emptyList()
)

@Keep
data class PrescribableSourceTypesResponse(
    @SerializedName("sourceTypeList")
    @Expose
    val sourceTypeList: SourceTypeList? = null
)

@Keep
data class SourceTypeList(
    @SerializedName("sourceName")
    @Expose
    val sourceName: List<String> = emptyList()
)

@Keep
data class PrescribableSpellingSuggestionsResponse(
    @SerializedName("suggestionGroup")
    @Expose
    val suggestionGroup: SuggestionGroup? = null
)

@Keep
data class SuggestionGroup(
    @SerializedName("suggestion")
    @Expose
    val suggestion: List<String> = emptyList()
)

@Keep
data class PrescribableTermTypesResponse(
    @SerializedName("termTypeList")
    @Expose
    val termTypeList: TermTypeList? = null
)

@Keep
data class TermTypeList(
    @SerializedName("termType")
    @Expose
    val termType: List<String> = emptyList()
)

@Keep
data class PrescribableVersionResponse(
    @SerializedName("version")
    @Expose
    val version: String? = null
)

@Keep
data class PrescribableFilterResponse(
    @SerializedName("filterGroup")
    @Expose
    val filterGroup: PrescribableFilterGroup? = null
)

@Keep
data class PrescribableFilterGroup(
    @SerializedName("conceptGroup")
    @Expose
    val conceptGroup: List<PrescribableFilterConceptGroup> = emptyList()
)

@Keep
data class PrescribableFilterConceptGroup(
    @SerializedName("tty")
    @Expose
    val tty: String? = null,
    @SerializedName("conceptProperties")
    @Expose
    val conceptProperties: List<PrescribableFilterConceptProperty> = emptyList()
)

@Keep
data class PrescribableFilterConceptProperty(
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
data class PrescribableAllPropertiesResponse(
    @SerializedName("propConceptGroup")
    @Expose
    val propConceptGroup: PrescribablePropConceptGroup? = null
)

@Keep
data class PrescribablePropConceptGroup(
    @SerializedName("propConcept")
    @Expose
    val propConcept: List<PropConcept> = emptyList()
)



@Keep
data class PrescribableAllRelatedResponse(
    @SerializedName("relatedGroup")
    @Expose
    val relatedGroup: PrescribableRelatedGroup? = null
)

@Keep
data class PrescribableRelatedGroup(
    @SerializedName("conceptGroup")
    @Expose
    val conceptGroup: List<PrescribableRelatedConceptGroup> = emptyList()
)

@Keep
data class PrescribableRelatedConceptGroup(
    @SerializedName("tty")
    @Expose
    val tty: String? = null,
    @SerializedName("conceptProperties")
    @Expose
    val conceptProperties: List<PrescribableRelatedConceptProperty> = emptyList()
)

@Keep
data class PrescribableRelatedConceptProperty(
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
data class PrescribableRelatedResponse(
    @SerializedName("relatedGroup")
    @Expose
    val relatedGroup: PrescribableRelatedGroup? = null
)

@Keep
data class PrescribablePropertiesResponse(
    @SerializedName("propConceptGroup")
    @Expose
    val propConceptGroup: PrescribablePropConceptGroup? = null
)

@Keep
data class PrescribableNameResponse(
    @SerializedName("idGroup")
    @Expose
    val idGroup: PrescribableNameIdGroup? = null
)

@Keep
data class PrescribableNameIdGroup(
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("rxcui")
    @Expose
    val rxcui: String? = null
)

@Keep
data class PrescribablePropertyResponse(
    @SerializedName("propValue")
    @Expose
    val propValue: String? = null
) 