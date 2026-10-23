package com.walhalla.pillfinder.adapter.obj

/**
 * RxNorm concept row (Related / Related by Type tabs) rendered as a NEW clickable chip
 * (layout item_concept_link, style NewClickableChip): chip = concept name, grey line = "RxCUI <id>".
 * Tap -> ComplexPresenter.onItemClicked(v, item) -> ConceptNavigator.openConcept (drug-search tabs).
 */
class ConceptLinkItem(val conceptName: String, val rxcui: String) :
    NValue("RxCUI $rxcui", "$conceptName  \u203A"), VieModel