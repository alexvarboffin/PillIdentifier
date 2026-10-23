package com.walhalla.pillfinder.fragment.api2

/**
 * Classes tab (FragmentClass): splits RxClass getClassByRxcui rows into
 *  - the MED-RT contraindication section: relaSource == "MEDRT" and rela starts with "ci_"
 *    (ci_with, ci_chemclass, ci_moa, ci_pe, ...), grouped rela -> class -> rows;
 *  - the regular rows, grouped class -> rows exactly as before.
 * Every input row lands in exactly one group (partition), so no row is lost or duplicated.
 * A class that has both ci_* and other rows (e.g. alprazolam / Benzodiazepines D001569) gets a card
 * in the contraindication section with its ci_* rows AND a regular card with its other rows.
 * Pure Kotlin (no Android imports) so the logic can be checked off-device against live JSON.
 * UI-only: same single getClassByRxcui response, no new endpoint.
 */
object ContraindicationSplitter {
    const val SOURCE_MEDRT = "MEDRT"

    /** Display order of the known MED-RT contraindication relations; any other ci_* follow alphabetically. */
    val KNOWN_ORDER: List<String> = listOf("ci_with", "ci_chemclass", "ci_moa", "ci_pe")

    data class ClassKey(val classId: String, val className: String, val classType: String)

    /** One getClassByRxcui row (rxclassDrugInfo), flattened to what the Classes tab shows. */
    data class Row(
        val classId: String,
        val className: String,
        val classType: String,
        val minConceptName: String,
        val minConceptTty: String,
        val rela: String,
        val relaSource: String
    )

    class Split<T>(
        /** rela -> (class -> rows); relas in display order, classes and rows in API order */
        val contra: Map<String, Map<ClassKey, List<T>>>,
        /** class -> rows for all non-contraindication rows; API order (same grouping as before) */
        val regular: Map<ClassKey, List<T>>
    )

    fun isContraindication(relaSource: String?, rela: String?): Boolean =
        SOURCE_MEDRT.equals(relaSource, ignoreCase = true) && (rela ?: "").startsWith("ci_")

    private fun relaRank(rela: String): Int {
        val i = KNOWN_ORDER.indexOf(rela)
        return if (i < 0) Int.MAX_VALUE else i
    }

    fun <T> split(
        rows: List<T>,
        key: (T) -> ClassKey,
        relaSource: (T) -> String?,
        rela: (T) -> String?
    ): Split<T> {
        val (ci, rest) = rows.partition { isContraindication(relaSource(it), rela(it)) }
        val byRela = ci.groupBy { rela(it) ?: "" } // LinkedHashMap, API order
        val relaOrder = byRela.keys.sortedWith(compareBy<String>({ relaRank(it) }, { it }))
        val contra = LinkedHashMap<String, Map<ClassKey, List<T>>>()
        for (r in relaOrder) contra[r] = byRela.getValue(r).groupBy(key)
        return Split(contra, rest.groupBy(key))
    }

    /** Convenience overload for flattened rows (used by FragmentClass). */
    fun split(rows: List<Row>): Split<Row> =
        split(rows, { ClassKey(it.classId, it.className, it.classType) }, { it.relaSource }, { it.rela })
}
