package com.walhalla.pillfinder.fragment.api2

import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.walhalla.lib.service.RxNormLib
import com.walhalla.pillfinder.R
import gov.nih.nlm.rxnorm.models.NdcProperty
import gov.nih.nlm.rxnorm.models.NdcPropertyListResponse
import kotlinx.coroutines.Job

/**
 * Pill card for one NDC, built from RxNorm getNDCProperties (/ndcproperties.json).
 * Opened from the NEW clickable NDC chip in FragmentNdc / FragmentNdcHistory.
 * States: loading -> content | empty | error (+ retry).
 */
class NdcPillCardDialog : DialogFragment() {

    private var ndc: String = ""
    private var allStatuses: Boolean = false
    private var job: Job? = null

    private var progress: ProgressBar? = null
    private var message: TextView? = null
    private var retry: Button? = null
    private var content: LinearLayout? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val args = requireArguments()
        ndc = args.getString(ARG_NDC).orEmpty()
        allStatuses = args.getBoolean(ARG_ALL_STATUSES, false)

        val view = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_ndc_pill_card, null, false)
        progress = view.findViewById(R.id.ndcCardProgress)
        message = view.findViewById(R.id.ndcCardMessage)
        retry = view.findViewById(R.id.ndcCardRetry)
        content = view.findViewById(R.id.ndcCardContent)
        retry?.setOnClickListener { load() }

        load()

        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.ndc_card_title, ndc))
            .setView(view)
            .setPositiveButton(android.R.string.ok, null)
            .create()
    }

    override fun onDestroy() {
        job?.cancel()
        job = null
        super.onDestroy()
    }

    private fun load() {
        showState(loading = true, text = null, canRetry = false)
        job?.cancel()
        job = RxNormLib.ndcProperties(ndc, allStatuses, object : RxNormLib.NdcPropertiesCallback {
            override fun onResult(body: NdcPropertyListResponse?) {
                if (!isAdded) return
                val records = body?.ndcPropertyList?.ndcProperty.orEmpty()
                if (records.isEmpty()) {
                    // non-2xx is also delivered as null body (RxNormLib.fetch contract)
                    showState(loading = false, text = getString(R.string.ndc_card_empty), canRetry = true)
                } else {
                    showState(loading = false, text = null, canRetry = false)
                    render(records)
                }
            }

            override fun onError(t: Throwable) {
                if (!isAdded) return
                val reason = t.message ?: t.localizedMessage ?: t.javaClass.simpleName
                showState(loading = false, text = getString(R.string.ndc_card_error, reason), canRetry = true)
            }
        })
    }

    private fun showState(loading: Boolean, text: String?, canRetry: Boolean) {
        progress?.visibility = if (loading) View.VISIBLE else View.GONE
        message?.visibility = if (text != null) View.VISIBLE else View.GONE
        message?.text = text
        retry?.visibility = if (canRetry) View.VISIBLE else View.GONE
        if (loading || text != null) content?.removeAllViews()
    }

    private fun render(records: List<NdcProperty>) {
        val root = content ?: return
        root.removeAllViews()
        records.forEachIndexed { index, record ->
            if (records.size > 1) {
                addSection(root, getString(R.string.ndc_card_record, index + 1, records.size, record.source.orDash()))
            }
            renderRecord(root, record, showSource = records.size == 1)
        }
    }

    private fun renderRecord(root: LinearLayout, p: NdcProperty, showSource: Boolean) {
        val props = p.propertyConceptList?.propertyConcept.orEmpty()
            .filter { !it.propName.isNullOrBlank() && !it.propValue.isNullOrBlank() }
        val used = HashSet<String>()
        fun values(name: String): List<String> {
            used += name
            return props.filter { it.propName == name }.mapNotNull { it.propValue?.trim() }.distinct()
        }
        fun value(name: String): String? = values(name).takeIf { it.isNotEmpty() }?.joinToString(", ")

        // Core physical description: always shown, "not specified" when missing.
        addRow(root, R.string.ndc_card_imprint, value("IMPRINT_CODE"), always = true)
        val colorText = value("COLORTEXT")
        val colorCode = value("COLOR")
        addRow(root, R.string.ndc_card_colour, colorText ?: codeLabel(colorCode), always = true)
        val shapeText = value("SHAPETEXT")
        val shapeCode = value("SHAPE")
        addRow(root, R.string.ndc_card_shape, shapeText ?: codeLabel(shapeCode), always = true)
        addRow(root, R.string.ndc_card_size, value("SIZE"), always = true)
        addRow(root, R.string.ndc_card_score, value("SCORE"), always = false)
        addRow(root, R.string.ndc_card_labeler, value("LABELER"), always = true)
        val packaging = p.packagingList?.packaging.orEmpty().map { it.trim() }.filter { it.isNotEmpty() }
        addRow(root, R.string.ndc_card_packaging, packaging.takeIf { it.isNotEmpty() }?.joinToString("\n"), always = true)

        // Regulatory / label info: only when present.
        addRow(root, R.string.ndc_card_marketing_status, value("MARKETING_STATUS"), always = false)
        val category = value("MARKETING_CATEGORY")
        val application = APPLICATION_PROPS.mapNotNull { value(it) }.joinToString(", ").ifEmpty { null }
        addRow(root, R.string.ndc_card_marketing_category,
            listOfNotNull(category, application).joinToString(" · ").ifEmpty { null }, always = false)
        val from = formatDate(value("MARKETING_EFFECTIVE_TIME_LOW"))
        val to = formatDate(value("MARKETING_EFFECTIVE_TIME_HIGH"))
        addRow(root, R.string.ndc_card_marketed,
            if (from != null || to != null) "${from ?: "?"} – ${to ?: "?"}" else null, always = false)
        addRow(root, R.string.ndc_card_label_type, value("LABEL_TYPE"), always = false)
        addRow(root, R.string.ndc_card_spl_set_id,
            p.splSetIdItem?.takeIf { it.isNotBlank() } ?: value("DM_SPL_ID"), always = false)
        addRow(root, R.string.ndc_card_ndc10, p.ndc10?.takeIf { it.isNotBlank() }, always = false)
        addRow(root, R.string.ndc_card_ndc9, p.ndc9?.takeIf { it.isNotBlank() }, always = false)
        addRow(root, R.string.ndc_card_rxcui, p.rxcui?.takeIf { it.isNotBlank() }, always = false)
        if (showSource) addRow(root, R.string.ndc_card_source, p.source?.takeIf { it.isNotBlank() }, always = false)

        // Anything else the API returned (future-proof: unknown propNames are not lost).
        val rest = props.filter { it.propName !in used }
        if (rest.isNotEmpty()) {
            addSection(root, getString(R.string.ndc_card_other))
            rest.groupBy { it.propName!! }.forEach { (name, list) ->
                addRow(root, name, list.mapNotNull { it.propValue }.distinct().joinToString(", "))
            }
        }
    }

    private fun addSection(root: LinearLayout, title: String) {
        val tv = TextView(requireContext())
        tv.text = title
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        tv.textSize = 15f
        tv.setPadding(0, dp(12), 0, dp(4))
        root.addView(tv)
    }

    private fun addRow(root: LinearLayout, labelRes: Int, value: String?, always: Boolean) {
        if (value.isNullOrBlank() && !always) return
        addRow(root, getString(labelRes), value)
    }

    private fun addRow(root: LinearLayout, label: String, value: String?) {
        val ctx = requireContext()
        val row = LinearLayout(ctx)
        row.orientation = LinearLayout.HORIZONTAL
        row.setPadding(0, dp(4), 0, dp(4))

        val l = TextView(ctx)
        l.text = label
        l.setTypeface(l.typeface, Typeface.BOLD)
        l.setTextColor(0xFF000000.toInt())
        l.textSize = 14f
        row.addView(l, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))

        val v = TextView(ctx)
        if (value.isNullOrBlank()) {
            v.text = getString(R.string.ndc_card_missing)
            v.setTextColor(ContextCompat.getColor(ctx, R.color.ndc_card_missing))
        } else {
            v.text = value
            v.setTextColor(0xFF000000.toInt())
            v.setTextIsSelectable(true) // long-press to copy (e.g. SPL set id)
        }
        v.textSize = 14f
        row.addView(v, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2f))
        root.addView(row)
    }

    private fun dp(v: Int): Int = (v * resources.displayMetrics.density).toInt()

    private fun String?.orDash(): String = if (this.isNullOrBlank()) "—" else this

    companion object {
        private const val ARG_NDC = "arg_ndc"
        private const val ARG_ALL_STATUSES = "arg_all_statuses"
        private const val TAG = "NdcPillCardDialog"

        private val APPLICATION_PROPS = listOf("NDA", "ANDA", "BLA", "NADA", "ANADA", "NDA_AUTHORIZED_GENERIC")

        /**
         * FDA SPL (NCI thesaurus) codes seen in live rifampin responses (23.10.2026), used only as a fallback
         * when COLORTEXT / SHAPETEXT is absent: C48336 = CAPSULE (SHAPETEXT), C48326 = RED, C48331 = ORANGE (COLORTEXT).
         */
        private val KNOWN_CODES = mapOf(
            "C48336" to "CAPSULE",
            "C48326" to "RED",
            "C48331" to "ORANGE"
        )

        private fun codeLabel(code: String?): String? {
            if (code.isNullOrBlank()) return null
            return code.split(';', ',').map { it.trim() }.filter { it.isNotEmpty() }.distinct()
                .joinToString(", ") { c -> KNOWN_CODES[c]?.let { "$it ($c)" } ?: c }
        }

        /** 20080420 -> 20.04.2008; anything else is returned as-is. */
        private fun formatDate(raw: String?): String? {
            if (raw.isNullOrBlank()) return null
            val s = raw.trim()
            return if (s.length == 8 && s.all { it.isDigit() }) "${s.substring(6, 8)}.${s.substring(4, 6)}.${s.substring(0, 4)}" else s
        }

        @JvmStatic
        fun newInstance(ndc: String, allStatuses: Boolean): NdcPillCardDialog {
            val f = NdcPillCardDialog()
            f.arguments = Bundle().apply {
                putString(ARG_NDC, ndc)
                putBoolean(ARG_ALL_STATUSES, allStatuses)
            }
            return f
        }

        @JvmStatic
        fun show(fm: FragmentManager, ndc: String, allStatuses: Boolean) {
            if (fm.isStateSaved || fm.findFragmentByTag(TAG) != null) return
            newInstance(ndc, allStatuses).show(fm, TAG)
        }
    }
}