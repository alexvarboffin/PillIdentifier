package com.walhalla.pillfinder.adapter.obj

/**
 * RxNorm concept row (Related / Related by Type tabs) rendered as a NEW clickable chip
 * (layout item_concept_link, style NewClickableChip): chip = concept name, grey line = "RxCUI <id>".
 * Tap -> ComplexPresenter.onItemClicked(v, item) -> ConceptNavigator.openConcept (drug-search tabs).
 *
 * Optional getAllRelatedExtension data (roadmap #5), shown as NON-clickable info only:
 *  psn          - RxNorm prescribable name, shown only when it differs from the name (case-insensitive);
 *  prescribable - pres == "Y" (concept is in the RxNorm Current Prescribable Content) -> "Rx prescribable" badge;
 *  veterinary   - vetdrug == "US" (has a US veterinary SPL) -> "Veterinary" badge.
 */
//class ConceptLinkItem(val conceptName: String, val rxcui: String) :
//    NValue("RxCUI $rxcui", "$conceptName  \u203A"), VieModel
//class ConceptLinkItem(
//    val conceptName: String,
//    val rxcui: String,
//    val psn: String? = null,
//    val prescribable: Boolean = false,
//    val veterinary: Boolean = false
//) : NValue("RxCUI $rxcui", "$conceptName  \u203A"), VieModel
/**
 * AnalogsActivity class members (roadmap 4b):
 *  tty  - appended to the grey line: "RxCUI <id> \u00B7 <TTY>";
 *  info - compact NON-clickable grey line from classMembers nodeAttr (source id / source name / relation).
 */
class ConceptLinkItem(
    val conceptName: String,
    val rxcui: String,
    val psn: String? = null,
    val prescribable: Boolean = false,
    val veterinary: Boolean = false,
    val tty: String? = null,
    val info: String? = null
) : NValue(
    if (tty.isNullOrBlank()) "RxCUI $rxcui" else "RxCUI $rxcui \u00B7 $tty",
    "$conceptName  \u203A"
), VieModel