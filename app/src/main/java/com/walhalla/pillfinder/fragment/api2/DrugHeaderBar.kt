package com.walhalla.pillfinder.fragment.api2

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.walhalla.lib.service.RxNormLib
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.adapter.viewHolder.NewClickableStyle
import gov.nih.nlm.rxnorm.models.PropConceptGroupResponse
import kotlinx.coroutines.Job

/**
 * Header row above the Drug Search tabs (layout fragment_rxnorm, id rxHeaderRow):
 *
 *  - roadmap #6: NON-clickable InfoBadge "RxNorm prescribable (US)" from getRxProperty(rxcui, PRESCRIBABLE).
 *    Live (23.10.2026): 198201, 141928, 202363, 9384, 201867 -> propValue "Y"; Adequan 1085959 (veterinary BN),
 *    Adenoscan 215025, remapped 1000004 -> {} (no property). Only "Y" shows the badge; absent / error -> hidden.
 *    Meaning: the concept is in the RxNorm Current Prescribable Content subset (US). Nothing more is claimed
 *    (not an FDA status, not Rx-only vs OTC).
 *  - roadmap #12: NEW clickable switch "Prescribable only" ([PrescribeMode], style NewClickableChip).
 *    A change is persisted and reported to [onPrescribeModeChanged] so the host can rebuild the open tabs.
 */
class DrugHeaderBar(
    root: View,
    private val onPrescribeModeChanged: (enabled: Boolean) -> Unit
) {
    private val badge: TextView = root.findViewById(R.id.headerBadgePrescribable)
    private val toggle: SwitchCompat = root.findViewById(R.id.prescribeToggle)
    private var job: Job? = null
    private var generation = 0

    init {
        val ctx = root.context
        NewClickableStyle.apply(toggle)
        toggle.isChecked = PrescribeMode.isEnabled(ctx) // before the listener: restoring state is not a change
        toggle.setOnCheckedChangeListener { _, checked ->
            PrescribeMode.set(ctx, checked)
            onPrescribeModeChanged(checked)
        }
        badge.visibility = View.GONE
    }

    /** Call every time the tabs are (re)built for [rxcui]. */
    fun onDrugOpened(rxcui: String) {
        cancelJobs()
        val gen = ++generation
        badge.visibility = View.GONE
        job = RxNormLib.rxProperty(rxcui, PROP_PRESCRIBABLE, object : RxNormLib.LibCallback<PropConceptGroupResponse> {
            override fun onResult(body: PropConceptGroupResponse?) {
                if (gen != generation) return
                val value = body?.propConceptGroup?.propConcept.orEmpty()
                    .firstOrNull { it.propName == PROP_PRESCRIBABLE }?.propValue
                badge.visibility = if (value.equals("Y", ignoreCase = true)) View.VISIBLE else View.GONE
            }

            override fun onError(t: Throwable) { /* silent: badge stays hidden */ }
        })
    }

    /** New search / Clear: forget the badge of the previous drug. */
    fun reset() {
        cancelJobs()
        generation++
        badge.visibility = View.GONE
    }

    fun cancelJobs() {
        job?.cancel()
        job = null
    }

    companion object {
        const val PROP_PRESCRIBABLE = "PRESCRIBABLE"
    }
}
