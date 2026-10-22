package com.walhalla.lib.datamodel.rxclass.response

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class RxClassResponse(
    @SerializedName("rxclassMinConceptList")
    @Expose
    val rxclassMinConceptList: RxclassMinConceptList? = null
)

@Keep
data class RxclassMinConceptList(
    @SerializedName("rxclassMinConcept")
    @Expose
    val rxclassMinConcept: List<RxclassMinConcept> = emptyList()
)

@Keep
data class RxclassMinConcept(
    @SerializedName("classId")
    @Expose
    val classId: String? = null,
    @SerializedName("className")
    @Expose
    val className: String? = null,
    @SerializedName("classType")
    @Expose
    val classType: String? = null
)

@Keep
data class RxClassContextsResponse(
    @SerializedName("classContextsList")
    @Expose
    val classContextsList: RxClassContextsList? = null
)

@Keep
data class RxClassContextsList(
    @SerializedName("classContext")
    @Expose
    val classContext: List<RxClassContext> = emptyList()
)

@Keep
data class RxClassContext(
    @SerializedName("classId")
    @Expose
    val classId: String? = null,
    @SerializedName("className")
    @Expose
    val className: String? = null,
    @SerializedName("classType")
    @Expose
    val classType: String? = null,
    @SerializedName("context")
    @Expose
    val context: String? = null
)

@Keep
data class RxClassGraphResponse(
    @SerializedName("classGraphList")
    @Expose
    val classGraphList: RxClassGraphList? = null
)

@Keep
data class RxClassGraphList(
    @SerializedName("classGraph")
    @Expose
    val classGraph: List<RxClassGraph> = emptyList()
)

@Keep
data class RxClassGraph(
    @SerializedName("classId")
    @Expose
    val classId: String? = null,
    @SerializedName("className")
    @Expose
    val className: String? = null,
    @SerializedName("classType")
    @Expose
    val classType: String? = null,
    @SerializedName("source")
    @Expose
    val source: String? = null,
    @SerializedName("rela")
    @Expose
    val rela: String? = null,
    @SerializedName("relatedClassId")
    @Expose
    val relatedClassId: String? = null,
    @SerializedName("relatedClassName")
    @Expose
    val relatedClassName: String? = null,
    @SerializedName("relatedClassType")
    @Expose
    val relatedClassType: String? = null
)

@Keep
data class RxClassTreeResponse(
    @SerializedName("classTreeList")
    @Expose
    val classTreeList: RxClassTreeList? = null
)

@Keep
data class RxClassTreeList(
    @SerializedName("classTree")
    @Expose
    val classTree: List<RxClassTree> = emptyList()
)

@Keep
data class RxClassTree(
    @SerializedName("classId")
    @Expose
    val classId: String? = null,
    @SerializedName("className")
    @Expose
    val className: String? = null,
    @SerializedName("classType")
    @Expose
    val classType: String? = null,
    @SerializedName("childClasses")
    @Expose
    val childClasses: List<RxClassChildClass> = emptyList()
)

@Keep
data class RxClassChildClass(
    @SerializedName("classId")
    @Expose
    val classId: String? = null,
    @SerializedName("className")
    @Expose
    val className: String? = null,
    @SerializedName("classType")
    @Expose
    val classType: String? = null
)

@Keep
data class RxClassRelaSourceVersionResponse(
    @SerializedName("relaSourceVersionList")
    @Expose
    val relaSourceVersionList: RxClassRelaSourceVersionList? = null
)

@Keep
data class RxClassRelaSourceVersionList(
    @SerializedName("relaSourceVersion")
    @Expose
    val relaSourceVersion: List<RxClassRelaSourceVersion> = emptyList()
)

@Keep
data class RxClassRelaSourceVersion(
    @SerializedName("source")
    @Expose
    val source: String? = null,
    @SerializedName("version")
    @Expose
    val version: String? = null
)

@Keep
data class RxClassRelasResponse(
    @SerializedName("relaList")
    @Expose
    val relaList: RxClassRelaList? = null
)

@Keep
data class RxClassRelaList(
    @SerializedName("rela")
    @Expose
    val rela: List<RxClassRela> = emptyList()
)

@Keep
data class RxClassRela(
    @SerializedName("rela")
    @Expose
    val rela: String? = null,
    @SerializedName("relaLabel")
    @Expose
    val relaLabel: String? = null,
    @SerializedName("relaDirection")
    @Expose
    val relaDirection: String? = null,
    @SerializedName("relatedClassId")
    @Expose
    val relatedClassId: String? = null,
    @SerializedName("relatedClassName")
    @Expose
    val relatedClassName: String? = null,
    @SerializedName("relatedClassType")
    @Expose
    val relatedClassType: String? = null
)

@Keep
data class RxClassSimilarityResponse(
    @SerializedName("similarityInformationList")
    @Expose
    val similarityInformationList: RxClassSimilarityInformationList? = null
)

@Keep
data class RxClassSimilarityInformationList(
    @SerializedName("similarityInformation")
    @Expose
    val similarityInformation: List<RxClassSimilarityInformation> = emptyList()
)

@Keep
data class RxClassSimilarityInformation(
    @SerializedName("classId")
    @Expose
    val classId: String? = null,
    @SerializedName("className")
    @Expose
    val className: String? = null,
    @SerializedName("classType")
    @Expose
    val classType: String? = null,
    @SerializedName("similarityScore")
    @Expose
    val similarityScore: String? = null,
    @SerializedName("similarityMethod")
    @Expose
    val similarityMethod: String? = null
)

@Keep
data class RxClassSourcesResponse(
    @SerializedName("sourcesOfDrugClassRelationsList")
    @Expose
    val sourcesOfDrugClassRelationsList: RxClassSourcesOfDrugClassRelationsList? = null
)

@Keep
data class RxClassSourcesOfDrugClassRelationsList(
    @SerializedName("source")
    @Expose
    val source: List<String> = emptyList()
)

@Keep
data class RxClassSpellingSuggestionsResponse(
    @SerializedName("suggestionGroup")
    @Expose
    val suggestionGroup: RxClassSuggestionGroup? = null
)

@Keep
data class RxClassSuggestionGroup(
    @SerializedName("suggestion")
    @Expose
    val suggestion: List<String> = emptyList()
)

@Keep
data class RxClassTypesResponse(
    @SerializedName("classTypeList")
    @Expose
    val classTypeList: RxClassTypeList? = null
)

@Keep
data class RxClassTypeList(
    @SerializedName("classType")
    @Expose
    val classType: List<String> = emptyList()
)

@Keep
data class RxClassByRxcuiResponse(
    @SerializedName("rxclassMinConceptList")
    @Expose
    val rxclassMinConceptList: RxClassByRxcuiMinConceptList? = null
)

@Keep
data class RxClassByRxcuiMinConceptList(
    @SerializedName("rxclassMinConcept")
    @Expose
    val rxclassMinConcept: List<RxClassByRxcuiMinConcept> = emptyList()
)

@Keep
data class RxClassByRxcuiMinConcept(
    @SerializedName("classId")
    @Expose
    val classId: String? = null,
    @SerializedName("className")
    @Expose
    val className: String? = null,
    @SerializedName("classType")
    @Expose
    val classType: String? = null,
    @SerializedName("rxcui")
    @Expose
    val rxcui: String? = null
) 