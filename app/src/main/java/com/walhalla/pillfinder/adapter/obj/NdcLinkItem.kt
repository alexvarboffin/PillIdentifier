package com.walhalla.pillfinder.adapter.obj

/**
 * NDC row whose value is rendered as a NEW clickable chip (layout item_ndc_link, style NewClickableChip).
 * Tap -> ComplexPresenter.onItemClicked(v, item) -> NdcPillCardDialog (getNDCProperties).
 * @param allStatuses true for historical/obsolete NDCs (ndcstatus=ALL), false = default (active).
 */
class NdcLinkItem(name: String, val ndc: String, val allStatuses: Boolean) : NValue(name, ndc), VieModel