package com.walhalla.pillfinder.adapter.obj

/**
 * Classes tab, MED-RT contraindication section (NOT clickable).
 * title = "Contraindications (MED-RT)", note = source / "reference only" disclaimer.
 */
class ContraSectionHeaderItem(val title: String, val note: String) : VieModel

/**
 * Classes tab, one MED-RT contraindication relation group (NOT clickable).
 * label = human-readable MED-RT relation (e.g. "Contraindication: chemical class"),
 * rela = raw relation (e.g. ci_chemclass) shown small/grey for traceability.
 */
class ContraGroupItem(val label: String, val rela: String) : VieModel
