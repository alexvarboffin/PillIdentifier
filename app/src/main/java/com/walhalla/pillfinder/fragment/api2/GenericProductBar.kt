package com.walhalla.pillfinder.fragment.api2

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.walhalla.lib.service.RxNormLib
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.adapter.viewHolder.NewClickableStyle
import gov.nih.nlm.rxnorm.models.IdGroupResponse
import gov.nih.nlm.rxnorm.models.MinConceptGroupResponse
import kotlinx.coroutines.Job

/**
 * Roadmap item #4: "Generic: <name> >" chip above the drug tabs of Drug Search ([RxNorm]).
 *
 * getGenericProduct (rxcui/{id}/generic.json) answers for every concept and returns the concept itself
 * for generics (live: SCD 198201 -> 198201, IN 9384 -> 9384) and the generic counterpart for brands
 * (SBD 201867 -> SCD 198201, SBDC 566037 -> SCDC 316641, BN 202433 Tylenol -> IN 161 acetaminophen).
 * So the chip is shown only when the returned rxcui differs from the opened one; no tty check is needed.
 * Errors / offline: the chip simply does not appear.
 *
 * Navigation (shared with Related-tab rows via [navigateTo]): tapping the chip pushes the brand onto a small back stack (max [MAX_BACK_STACK]) and calls
 * [onOpen] with the generic rxcui (host rebuilds the same 10 tabs). A "< Back to <brand>" chip pops it.
 * The brand label comes from getRxNormName (one cheap call per opened drug).
 */
class GenericProductBar(
    private val bar: LinearLayout,
    private val onOpen: (rxcui: String) -> Unit
) {
    private data class Entry(val rxcui: String, val name: String)

    private val backStack = ArrayDeque<Entry>()
    private val jobs = mutableListOf<Job>()
    private var generation = 0

    /** rxcui of the drug currently shown in the tabs (null before the first open / after [reset]). */
    var currentRxcui: String? = null
        private set
    private var currentName: String? = null

    private val ctx get() = bar.context
    private fun dp(v: Int) = (v * ctx.resources.displayMetrics.density).toInt()

    /** Call every time the tabs are (re)built for [rxcui]. */
    fun onDrugOpened(rxcui: String) {
        cancelJobs()
        val gen = ++generation
        if (rxcui != currentRxcui) currentName = null
        currentRxcui = rxcui
        rebuild(null)

        jobs += RxNormLib.genericProduct(rxcui, object : RxNormLib.LibCallback<MinConceptGroupResponse> {
            override fun onResult(body: MinConceptGroupResponse?) {
                if (gen != generation) return
                val generic = body?.minConceptGroup?.minConcept.orEmpty()
                    .firstOrNull { !it.rxcui.isNullOrBlank() && it.rxcui != rxcui }
                    ?: return // generic concept (answers itself) or nothing -> no chip
                rebuild(Entry(generic.rxcui!!, generic.name ?: ctx.getString(R.string.generic_rxcui, generic.rxcui)))
            }

            override fun onError(t: Throwable) { /* silent: chip just does not appear */ }
        })
        if (currentName == null) {
            jobs += RxNormLib.rxNormName(rxcui, object : RxNormLib.LibCallback<IdGroupResponse> {
                override fun onResult(body: IdGroupResponse?) {
                    if (gen != generation) return
                    body?.idGroup?.name?.takeIf { it.isNotBlank() }?.let { currentName = it }
                }

                override fun onError(t: Throwable) {}
            })
        }
    }

    /** New search / clear: forget the brand -> generic history and hide the bar. */
    fun reset() {
        cancelJobs()
        generation++
        backStack.clear()
        currentRxcui = null
        currentName = null
        bar.removeAllViews()
        bar.visibility = View.GONE
    }

    fun cancelJobs() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }

    private fun rebuild(generic: Entry?) {
        bar.removeAllViews()
        backStack.lastOrNull()?.let { prev ->
            addChip(ctx.getString(R.string.generic_back, prev.name)) { goBack() }
        }
        if (generic != null) {
            addChip(ctx.getString(R.string.generic_chip, generic.name)) { openGeneric(generic) }
        }
        bar.visibility = if (bar.childCount > 0) View.VISIBLE else View.GONE
    }

    private fun addChip(text: String, click: () -> Unit) {
        val chip = TextView(ctx, null, 0, R.style.NewClickableChip)
        chip.text = text
        NewClickableStyle.apply(chip)
        chip.setOnClickListener { click() }
        bar.addView(chip, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { if (bar.childCount > 0) topMargin = dp(6) })
    }

    private fun openGeneric(generic: Entry) {
        navigateTo(generic.rxcui, generic.name)
    }
//    was (before the shared history for Related-tab navigation):
//    private fun openGeneric(generic: Entry) {
//        val brand = currentRxcui ?: return
//        backStack.addLast(Entry(brand, currentName ?: ctx.getString(R.string.generic_rxcui, brand)))
//        while (backStack.size > MAX_BACK_STACK) backStack.removeFirst()
//        onOpen(generic.rxcui)
//    }

    /**
     * Shared drug navigation (Generic chip and Related / Related by Type rows): pushes the current drug
     * onto the ONE back history (max [MAX_BACK_STACK]) and opens [rxcui] via [onOpen].
     * [name] (if known) becomes the label of the next "Back to" chip without an extra getRxNormName call.
     */
    fun navigateTo(rxcui: String, name: String?) {
        val from = currentRxcui
        if (from == rxcui) return
        if (from != null) {
            backStack.addLast(Entry(from, currentName ?: ctx.getString(R.string.generic_rxcui, from)))
            while (backStack.size > MAX_BACK_STACK) backStack.removeFirst()
        }
        currentRxcui = rxcui
        currentName = name?.takeIf { it.isNotBlank() }
        onOpen(rxcui)
    }

    private fun goBack() {
        val prev = backStack.removeLastOrNull() ?: return
        currentName = prev.name // known label, onDrugOpened keeps it because the rxcui matches
        currentRxcui = prev.rxcui
        onOpen(prev.rxcui)
    }

    companion object {
        const val MAX_BACK_STACK = 20
    }
}