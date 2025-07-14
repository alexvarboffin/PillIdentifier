package com.walhalla.lib.datamodel.common.response

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.walhalla.lib.datamodel.pkg_base.PropConcept

// --- Общие классы для всех API ---

@Keep
data class IdGroup(
    @SerializedName("rxnormId")
    @Expose
    val rxnormId: List<String>? = emptyList()
)

@Keep
data class Response0(
    @SerializedName("idGroup")
    @Expose
    val idGroup: IdGroup? = null
)

@Keep
data class Response1(
    @SerializedName("propConceptGroup")
    @Expose
    val propConceptGroup: PropConceptGroup? = null
)

@Keep
data class PropConceptGroup(
    @SerializedName("propConcept")
    @Expose
    val propConcept: List<PropConcept> = emptyList()
)



@Keep
data class Response2(
    @SerializedName("relatedGroup")
    @Expose
    val relatedGroup: RelatedGroup? = null
)

@Keep
data class RelatedGroup(
    @SerializedName("conceptGroup")
    @Expose
    val conceptGroup: List<RelatedConceptGroup> = emptyList()
)

@Keep
data class RelatedConceptGroup(
    @SerializedName("tty")
    @Expose
    val tty: String? = null,
    @SerializedName("conceptProperties")
    @Expose
    val conceptProperties: List<RelatedConceptProperty> = emptyList()
)

@Keep
data class RelatedConceptProperty(
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
data class Response4(
    @SerializedName("propConceptGroup")
    @Expose
    val propConceptGroup: PropConceptGroup? = null
)

@Keep
data class Response5(
    @SerializedName("rxtermsProperties")
    @Expose
    val rxtermsProperties: RxtermsProperties? = null
)

@Keep
data class RxtermsProperties(
    @SerializedName("brandName")
    @Expose
    val brandName: String? = null,
    @SerializedName("displayName")
    @Expose
    val displayName: String? = null,
    @SerializedName("synonym")
    @Expose
    val synonym: String? = null,
    @SerializedName("fullName")
    @Expose
    val fullName: String? = null,
    @SerializedName("fullGenericName")
    @Expose
    val fullGenericName: String? = null,
    @SerializedName("strength")
    @Expose
    val strength: String? = null,
    @SerializedName("rxtermsDoseForm")
    @Expose
    val rxtermsDoseForm: String? = null,
    @SerializedName("route")
    @Expose
    val route: String? = null,
    @SerializedName("termType")
    @Expose
    val termType: String? = null,
    @SerializedName("rxcui")
    @Expose
    val rxcui: String? = null,
    @SerializedName("genericRxcui")
    @Expose
    val genericRxcui: String? = null,
    @SerializedName("rxnormDoseForm")
    @Expose
    val rxnormDoseForm: String? = null,
    @SerializedName("suppress")
    @Expose
    val suppress: String? = null
    // Остальные поля (name, tty, language, umlscui, rxtermsDisplayName, rxtermsSynonym, rxtermsTermType, rxtermsLanguage, rxtermsSuppress, rxtermsUmlsCui) оставлены для обратной совместимости, если они реально приходят в других endpoint'ах
)

@Keep
data class Response7(
    @SerializedName("rxcuiStatusHistory")
    @Expose
    val rxcuiStatusHistory: RxcuiStatusHistory? = null
)

@Keep
data class RxcuiStatusHistory(
    @SerializedName("metaData")
    @Expose
    val metaData: MetaData? = null,
    @SerializedName("attributes")
    @Expose
    val attributes: Attributes? = null,
    @SerializedName("definitionalFeatures")
    @Expose
    val definitionalFeatures: DefinitionalFeatures? = null,
    @SerializedName("pack")
    @Expose
    val pack: Pack? = null,
    @SerializedName("derivedConcepts")
    @Expose
    val derivedConcepts: DerivedConcepts? = null
)

@Keep
data class MetaData(
    @SerializedName("status")
    @Expose
    val status: String? = null,
    @SerializedName("source")
    @Expose
    val source: String? = null,
    @SerializedName("releaseStartDate")
    @Expose
    val releaseStartDate: String? = null,
    @SerializedName("releaseEndDate")
    @Expose
    val releaseEndDate: String? = null,
    @SerializedName("isCurrent")
    @Expose
    val isCurrent: String? = null,
    @SerializedName("activeStartDate")
    @Expose
    val activeStartDate: String? = null,
    @SerializedName("activeEndDate")
    @Expose
    val activeEndDate: String? = null,
    @SerializedName("remappedDate")
    @Expose
    val remappedDate: String? = null
)

@Keep
data class Attributes(
    @SerializedName("rxcui")
    @Expose
    val rxcui: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("tty")
    @Expose
    val tty: String? = null,
    @SerializedName("isMultipleIngredient")
    @Expose
    val isMultipleIngredient: String? = null,
    @SerializedName("isBranded")
    @Expose
    val isBranded: String? = null
)

@Keep
data class DefinitionalFeatures(
    @SerializedName("ingredientAndStrength")
    @Expose
    val ingredientAndStrength: List<IngredientAndStrength>? = null,
    @SerializedName("quantityFactor")
    @Expose
    val quantityFactor: QuantityFactor? = null,
    @SerializedName("qualitativeDistinction")
    @Expose
    val qualitativeDistinction: String? = null,
    @SerializedName("doseFormConcept")
    @Expose
    val doseFormConcept: List<DoseFormConcept>? = null,
    @SerializedName("doseFormGroupConcept")
    @Expose
    val doseFormGroupConcept: List<DoseFormGroupConcept>? = null
)

@Keep
data class IngredientAndStrength(
    @SerializedName("baseRxcui")
    @Expose
    val baseRxcui: String? = null,
    @SerializedName("baseName")
    @Expose
    val baseName: String? = null,
    @SerializedName("bossRxcui")
    @Expose
    val bossRxcui: String? = null,
    @SerializedName("bossName")
    @Expose
    val bossName: String? = null,
    @SerializedName("activeIngredientRxcui")
    @Expose
    val activeIngredientRxcui: String? = null,
    @SerializedName("activeIngredientName")
    @Expose
    val activeIngredientName: String? = null,
    @SerializedName("moietyRxcui")
    @Expose
    val moietyRxcui: String? = null,
    @SerializedName("moietyName")
    @Expose
    val moietyName: String? = null,
    @SerializedName("numeratorValue")
    @Expose
    val numeratorValue: String? = null,
    @SerializedName("numeratorUnit")
    @Expose
    val numeratorUnit: String? = null,
    @SerializedName("denominatorValue")
    @Expose
    val denominatorValue: String? = null,
    @SerializedName("denominatorUnit")
    @Expose
    val denominatorUnit: String? = null
)

@Keep
data class QuantityFactor(
    @SerializedName("quantityFactorValue")
    @Expose
    val quantityFactorValue: String? = null,
    @SerializedName("quantityFactorUnit")
    @Expose
    val quantityFactorUnit: String? = null
)

@Keep
data class DoseFormConcept(
    @SerializedName("doseFormRxcui")
    @Expose
    val doseFormRxcui: String? = null,
    @SerializedName("doseFormName")
    @Expose
    val doseFormName: String? = null
)

@Keep
data class DoseFormGroupConcept(
    @SerializedName("doseFormGroupRxcui")
    @Expose
    val doseFormGroupRxcui: String? = null,
    @SerializedName("doseFormGroupName")
    @Expose
    val doseFormGroupName: String? = null
)

@Keep
data class Pack(
    @SerializedName("packAlias")
    @Expose
    val packAlias: String? = null,
    @SerializedName("packConcept")
    @Expose
    val packConcept: List<PackConcept>? = null
)

@Keep
data class PackConcept(
    @SerializedName("packRxcui")
    @Expose
    val packRxcui: String? = null,
    @SerializedName("packName")
    @Expose
    val packName: String? = null,
    @SerializedName("packNumber")
    @Expose
    val packNumber: String? = null
)

@Keep
data class DerivedConcepts(
    @SerializedName("ingredientConcept")
    @Expose
    val ingredientConcept: List<IngredientConcept>? = null,
    @SerializedName("qdFreeConcept")
    @Expose
    val qdFreeConcept: List<QdFreeConcept>? = null,
    @SerializedName("quantifiedConcept")
    @Expose
    val quantifiedConcept: List<QuantifiedConcept>? = null,
    @SerializedName("remappedConcept")
    @Expose
    val remappedConcept: List<RemappedConcept>? = null,
    @SerializedName("scdConcept")
    @Expose
    val scdConcept: List<ScdConcept>? = null
)

@Keep
data class IngredientConcept(
    @SerializedName("ingredientRxcui")
    @Expose
    val ingredientRxcui: String? = null,
    @SerializedName("ingredientName")
    @Expose
    val ingredientName: String? = null
)

@Keep
data class QdFreeConcept(
    @SerializedName("qdFreeRxcui")
    @Expose
    val qdFreeRxcui: String? = null,
    @SerializedName("qdFreeName")
    @Expose
    val qdFreeName: String? = null
)

@Keep
data class QuantifiedConcept(
    @SerializedName("quantifiedRxcui")
    @Expose
    val quantifiedRxcui: String? = null,
    @SerializedName("quantifiedName")
    @Expose
    val quantifiedName: String? = null,
    @SerializedName("quantifiedTTY")
    @Expose
    val quantifiedTTY: String? = null,
    @SerializedName("quantifiedActive")
    @Expose
    val quantifiedActive: String? = null
)

@Keep
data class RemappedConcept(
    @SerializedName("remappedRxCui")
    @Expose
    val remappedRxCui: String? = null,
    @SerializedName("remappedName")
    @Expose
    val remappedName: String? = null,
    @SerializedName("remappedTTY")
    @Expose
    val remappedTTY: String? = null,
    @SerializedName("remappedActive")
    @Expose
    val remappedActive: String? = null
)

@Keep
data class ScdConcept(
    @SerializedName("scdConceptRxcui")
    @Expose
    val scdConceptRxcui: String? = null,
    @SerializedName("scdConceptName")
    @Expose
    val scdConceptName: String? = null
)

@Keep
data class Response8(
    @SerializedName("rxclassDrugInfoList")
    @Expose
    val rxclassDrugInfoList: RxclassDrugInfoList? = null
)

@Keep
data class RxclassDrugInfoList(
    @SerializedName("rxclassDrugInfo")
    @Expose
    val rxclassDrugInfo: List<RxclassDrugInfo> = emptyList()
)

@Keep
data class RxclassDrugInfo(
    @SerializedName("rxclassMinConceptItem")
    @Expose
    val rxclassMinConceptItem: RxclassMinConceptItem? = null,
    @SerializedName("minConcept")
    @Expose
    val minConcept: MinConcept? = null
)

/**
 * Общий класс для minConceptItem (Interaction API) и minConcept (RxNorm/RxClass API).
 * Пересекающиеся поля: rxcui, name, tty.
 * Используется в Response6 (interaction), Response8 (rxclassDrugInfoList), и других местах, где структура совпадает.
 */
@Keep
data class MinConcept(
    @SerializedName("rxcui")
    @Expose
    val rxcui: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("tty")
    @Expose
    val tty: String? = null
)

// RxclassMinConceptItem оставляем отдельным, так как у него другой набор полей
@Keep
data class RxclassMinConceptItem(
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