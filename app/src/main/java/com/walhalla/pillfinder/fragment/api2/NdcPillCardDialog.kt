package com.walhalla.pillfinder.fragment.api2

import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.KeyEvent
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
import com.walhalla.pillfinder.adapter.viewHolder.NewClickableStyle
import gov.nih.nlm.rxnorm.models.NdcInfo
import gov.nih.nlm.rxnorm.models.NdcInfoListResponse
import gov.nih.nlm.rxnorm.models.NdcProperty
import gov.nih.nlm.rxnorm.models.NdcPropertyListResponse
import gov.nih.nlm.rxnorm.models.NdcRelation
import gov.nih.nlm.rxnorm.models.NdcStatus
import gov.nih.nlm.rxnorm.models.NdcStatusResponse
import kotlinx.coroutines.Job

/**
 * Pill card for one NDC (roadmap #1 + #2). Three independent sections, loaded in parallel,
 * each with its own loading / error (+retry) / empty state:
 *  1. Pill properties  - getNDCProperties (/ndcproperties.json)
 *  2. NDC status       - getNDCStatus     (/ndcstatus.json)
 *  3. Other packages   - findRelatedNDCs  (/relatedndc.json, relation=drug, active only)
 *
 * Navigation: tapping a related NDC chip REPLACES the content of this same dialog (no dialog stacking);
 * the previous NDC is pushed on an in-dialog back stack (max [MAX_BACK_STACK]); "Back to NDC ..." chip
 * or the system Back key pops it. Closing the dialog drops the stack.
 */
class NdcPillCardDialog : DialogFragment() {

    /** One opened NDC. allStatuses=true -> getNDCProperties with ndcstatus=ALL (obsolete/historical NDCs). */
    private data class Entry(val ndc: String, val allStatuses: Boolean)

    private lateinit var current: Entry
    private val backStack = ArrayList<Entry>()

    private var propertiesJob: Job? = null
    private var statusJob: Job? = null
    private var relatedJob: Job? = null

    // section 1 (views from the layout, unchanged ids from roadmap #1)
    private var progress: ProgressBar? = null
    private var message: TextView? = null
    private var retry: Button? = null
    private var content: LinearLayout? = null
    private var back: TextView? = null

    // sections 2 and 3 (built in code)
    private var statusSection: SectionViews? = null
    private var relatedSection: SectionViews? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val args = requireArguments()
        current = Entry(args.getString(ARG_NDC).orEmpty(), args.getBoolean(ARG_ALL_STATUSES, false))
        if (savedInstanceState != null) {
            savedInstanceState.getString(STATE_NDC)?.let {
                current = Entry(it, savedInstanceState.getBoolean(STATE_ALL, false))
            }
            val ndcs = savedInstanceState.getStringArrayList(STATE_STACK_NDC).orEmpty()
            val flags = savedInstanceState.getBooleanArray(STATE_STACK_ALL) ?: BooleanArray(0)
            backStack.clear()
            ndcs.forEachIndexed { i, n -> backStack.add(Entry(n, flags.getOrElse(i) { false })) }
        }

        val view = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_ndc_pill_card, null, false)
        progress = view.findViewById(R.id.ndcCardProgress)
        message = view.findViewById(R.id.ndcCardMessage)
        retry = view.findViewById(R.id.ndcCardRetry)
        content = view.findViewById(R.id.ndcCardContent)
        back = view.findViewById(R.id.ndcCardBack)
        back?.let { NewClickableStyle.apply(it) }
        back?.setOnClickListener { goBack() }
        retry?.setOnClickListener { loadProperties() }
        statusSection = SectionViews(view.findViewById(R.id.ndcCardStatusSection), getString(R.string.ndc_card_section_status)) { loadStatus() }
        relatedSection = SectionViews(view.findViewById(R.id.ndcCardRelatedSection), getString(R.string.ndc_card_section_related)) { loadRelated() }

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.ndc_card_title, current.ndc))
            .setView(view)
            .setPositiveButton(android.R.string.ok, null)
            .create()
        // System Back: pop the in-dialog stack first, close only when it is empty.
        dialog.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP && backStack.isNotEmpty()) {
                goBack(); true
            } else keyCode == KeyEvent.KEYCODE_BACK && backStack.isNotEmpty()
        }
        showEntry()
        return dialog
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_NDC, current.ndc)
        outState.putBoolean(STATE_ALL, current.allStatuses)
        outState.putStringArrayList(STATE_STACK_NDC, ArrayList(backStack.map { it.ndc }))
        outState.putBooleanArray(STATE_STACK_ALL, backStack.map { it.allStatuses }.toBooleanArray())
    }

    override fun onDestroy() {
        cancelAll()
        super.onDestroy()
    }

    private fun cancelAll() {
        propertiesJob?.cancel(); propertiesJob = null
        statusJob?.cancel(); statusJob = null
        relatedJob?.cancel(); relatedJob = null
    }

    // ---------------------------------------------------------------- navigation

    private fun openRelated(entry: Entry) {
        if (entry.ndc == current.ndc) return
        backStack.add(current)
        while (backStack.size > MAX_BACK_STACK) backStack.removeAt(0)
        current = entry
        showEntry()
    }

    private fun goBack() {
        if (backStack.isEmpty()) return
        current = backStack.removeAt(backStack.size - 1)
        showEntry()
    }

    private fun showEntry() {
        (dialog as? AlertDialog)?.setTitle(getString(R.string.ndc_card_title, current.ndc))
        val prev = backStack.lastOrNull()
        back?.visibility = if (prev != null) View.VISIBLE else View.GONE
        if (prev != null) back?.text = getString(R.string.ndc_card_back, prev.ndc)
        cancelAll()
        // three independent calls in parallel; failure of one does not affect the others
        loadProperties()
        loadStatus()
        loadRelated()
    }

    // ---------------------------------------------------------------- 1. properties (roadmap #1)

    // was (roadmap #1): private fun load() { ... } - renamed to loadProperties(), uses current.ndc instead of the ndc field
    private fun loadProperties() {
        val entry = current
        showState(loading = true, text = null, canRetry = false)
        propertiesJob?.cancel()
        propertiesJob = RxNormLib.ndcProperties(entry.ndc, entry.allStatuses, object : RxNormLib.NdcPropertiesCallback {
            override fun onResult(body: NdcPropertyListResponse?) {
                if (!isAdded || entry != current) return
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
                if (!isAdded || entry != current) return
                showState(loading = false, text = getString(R.string.ndc_card_error, reason(t)), canRetry = true)
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
            listOfNotNull(category, application).joinToString(" \u00B7 ").ifEmpty { null }, always = false)
        val from = formatDate(value("MARKETING_EFFECTIVE_TIME_LOW"))
        val to = formatDate(value("MARKETING_EFFECTIVE_TIME_HIGH"))
        addRow(root, R.string.ndc_card_marketed,
            if (from != null || to != null) "${from ?: "?"} \u2013 ${to ?: "?"}" else null, always = false)
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

    // ---------------------------------------------------------------- 2. status (roadmap #2)

    private fun loadStatus() {
        val entry = current
        val section = statusSection ?: return
        section.loading()
        statusJob?.cancel()
        statusJob = RxNormLib.ndcStatus(entry.ndc, object : RxNormLib.LibCallback<NdcStatusResponse> {
            override fun onResult(body: NdcStatusResponse?) {
                if (!isAdded || entry != current) return
                val s = body?.ndcStatus
                if (s == null || (s.status.isNullOrBlank() && s.rxcui.isNullOrBlank() && s.ndcHistory.isNullOrEmpty())) {
                    section.message(getString(R.string.ndc_card_status_empty), canRetry = true)
                } else {
                    section.done()
                    renderStatus(section.content, s)
                }
            }

            override fun onError(t: Throwable) {
                if (!isAdded || entry != current) return
                section.message(getString(R.string.ndc_card_error, reason(t)), canRetry = true)
            }
        })
    }

    private fun renderStatus(root: LinearLayout, s: NdcStatus) {
        root.removeAllViews()
        addRow(root, R.string.ndc_card_status, s.status?.takeIf { it.isNotBlank() } ?: "UNKNOWN", always = true)
        addRow(root, R.string.ndc_card_active, s.active, always = false)
        addRow(root, R.string.ndc_card_rxnorm_ndc, s.rxnormNdc, always = false)
        val maps = listOfNotNull(
            s.rxcui?.takeIf { it.isNotBlank() },
            s.conceptName?.takeIf { it.isNotBlank() },
            s.conceptStatus?.takeIf { it.isNotBlank() }?.let { "($it)" }
        ).joinToString(" ").ifEmpty { null }
        addRow(root, R.string.ndc_card_maps_to, maps, always = true)
        addRow(root, R.string.ndc_card_sources, s.sourceList?.sourceName.orEmpty().filter { it.isNotBlank() }
            .joinToString(", ").ifEmpty { null }, always = false)
        addRow(root, R.string.ndc_card_alt_ndc, s.altNdc?.takeIf { it.isNotBlank() && it != "N" }, always = false)
        addRow(root, R.string.ndc_card_comment, s.comment?.takeIf { it.isNotBlank() }, always = false)

        // compact history: "07.2007 - 12.2010  rxcui 201867" (original -> active when remapped)
        val history = s.ndcHistory.orEmpty().map { h ->
            val period = "${formatYearMonth(h.startDate) ?: "?"} \u2013 ${formatYearMonth(h.endDate) ?: "?"}"
            val rx = when {
                h.originalRxcui.isNullOrBlank() -> h.activeRxcui.orEmpty()
                h.activeRxcui.isNullOrBlank() || h.activeRxcui == h.originalRxcui -> h.originalRxcui.orEmpty()
                else -> "${h.originalRxcui} \u2192 ${h.activeRxcui}"
            }
            if (rx.isBlank()) period else "$period  rxcui $rx"
        }
        addRow(root, R.string.ndc_card_history, history.joinToString("\n").ifEmpty { null }, always = false)

        val mapping = s.ndcSourceMapping.orEmpty().map { m ->
            listOfNotNull(m.ndcSource, m.ndcRxcui?.let { "rxcui $it" }, m.ndcActive?.let { "active $it" })
                .joinToString(" \u00B7 ")
        }.filter { it.isNotBlank() }
        addRow(root, R.string.ndc_card_source_mapping, mapping.joinToString("\n").ifEmpty { null }, always = false)
    }

    // ---------------------------------------------------------------- 3. related NDCs (roadmap #2)

    private fun loadRelated() {
        val entry = current
        val section = relatedSection ?: return
        section.setTitle(getString(R.string.ndc_card_section_related))
        section.loading()
        relatedJob?.cancel()
        relatedJob = RxNormLib.relatedNdcs(entry.ndc, RELATED_RELATION, object : RxNormLib.LibCallback<NdcInfoListResponse> {
            override fun onResult(body: NdcInfoListResponse?) {
                if (!isAdded || entry != current) return
                val others = body?.ndcInfoList?.ndcInfo.orEmpty()
                    .filter { !it.ndc11.isNullOrBlank() && it.ndc11 != entry.ndc }
                    .distinctBy { it.ndc11 }
                section.setTitle(getString(R.string.ndc_card_section_related_count, others.size))
                if (others.isEmpty()) {
                    section.message(getString(R.string.ndc_card_related_empty), canRetry = false)
                } else {
                    section.done()
                    renderRelated(section.content, others)
                }
            }

            override fun onError(t: Throwable) {
                if (!isAdded || entry != current) return
                section.message(getString(R.string.ndc_card_error, reason(t)), canRetry = true)
            }
        })
    }

    private fun renderRelated(root: LinearLayout, items: List<NdcInfo>) {
        root.removeAllViews()
        val ctx = requireContext()
        for (info in items) {
            val ndc = info.ndc11 ?: continue
            val row = LinearLayout(ctx)
            row.orientation = LinearLayout.VERTICAL
            row.setPadding(0, dp(3), 0, dp(3))

            // NEW clickable: same style as the NDC chips in the NDC / NDC History tabs
            val chip = TextView(ctx, null, 0, R.style.NewClickableChip)
            chip.text = "$ndc  \u203A"
            NewClickableStyle.apply(chip)
            val allStatuses = !info.status.equals("ACTIVE", ignoreCase = true)
            chip.setOnClickListener { openRelated(Entry(ndc, allStatuses)) }
            row.addView(chip, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT))

            val sub = TextView(ctx)
            sub.text = listOfNotNull(
                info.status, info.tty, info.rxcui?.let { "rxcui $it" }, info.conceptName
            ).filter { it.isNotBlank() }.joinToString(" \u00B7 ")
            sub.textSize = 12f
            sub.setTextColor(ContextCompat.getColor(ctx, R.color.ndc_card_missing))
            row.addView(sub)
            root.addView(row)
        }
    }

    // ---------------------------------------------------------------- shared UI helpers

    /** Header + progress + message + retry + content for one independent section. */
    private inner class SectionViews(container: LinearLayout, title: String, private val onRetry: () -> Unit) {
        private val header = TextView(container.context)
        private val bar = ProgressBar(container.context)
        private val msg = TextView(container.context)
        private val retryButton = Button(container.context)
        val content = LinearLayout(container.context)

        init {
            container.removeAllViews()
            header.text = title
            header.setTypeface(header.typeface, Typeface.BOLD)
            header.setTextColor(ContextCompat.getColor(container.context, R.color.colorPrimary))
            header.textSize = 16f
            header.setPadding(0, dp(16), 0, dp(4))
            container.addView(header)
            container.addView(bar, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { gravity = android.view.Gravity.CENTER_HORIZONTAL })
            msg.textSize = 15f
            msg.setTextColor(0xFF000000.toInt())
            msg.setPadding(0, dp(4), 0, dp(4))
            container.addView(msg)
            retryButton.setText(R.string.ndc_card_retry)
            retryButton.setOnClickListener { onRetry() }
            container.addView(retryButton, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT))
            content.orientation = LinearLayout.VERTICAL
            container.addView(content)
            loading()
        }

        fun setTitle(title: String) { header.text = title }

        fun loading() {
            bar.visibility = View.VISIBLE
            msg.visibility = View.GONE
            retryButton.visibility = View.GONE
            content.removeAllViews()
        }

        fun message(text: String, canRetry: Boolean) {
            bar.visibility = View.GONE
            msg.visibility = View.VISIBLE
            msg.text = text
            retryButton.visibility = if (canRetry) View.VISIBLE else View.GONE
            content.removeAllViews()
        }

        fun done() {
            bar.visibility = View.GONE
            msg.visibility = View.GONE
            retryButton.visibility = View.GONE
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

    private fun String?.orDash(): String = if (this.isNullOrBlank()) "\u2014" else this

    companion object {
        private const val ARG_NDC = "arg_ndc"
        private const val ARG_ALL_STATUSES = "arg_all_statuses"
        private const val STATE_NDC = "state_ndc"
        private const val STATE_ALL = "state_all"
        private const val STATE_STACK_NDC = "state_stack_ndc"
        private const val STATE_STACK_ALL = "state_stack_all"
        private const val TAG = "NdcPillCardDialog"
        private const val MAX_BACK_STACK = 20

        /**
         * relation=drug: all active NDCs of the same clinical drug (ingredient + strength + dose form), incl. branded
         * packages. Live 23.10.2026: 00527139301 -> product 2 / concept 15 / drug 16 (drug also has Rifadin SBD
         * 52959046110); obsolete 00068051016 -> product 0 / concept 1 / drug 16 active alternatives.
         */
        private val RELATED_RELATION = NdcRelation.drug

        private val APPLICATION_PROPS = listOf("NDA", "ANDA", "BLA", "NADA", "ANADA", "NDA_AUTHORIZED_GENERIC")

        /**
         * FDA SPL (NCI thesaurus) codes seen in live rifampin responses (23.10.2026), used only as a fallback
         * when COLORTEXT / SHAPETEXT is absent: C48336 = CAPSULE (SHAPETEXT), C48326 = RED, C48331 = ORANGE (COLORTEXT).
         */
        //private val KNOWN_CODES = mapOf(
        //    "C48336" to "CAPSULE",
        //    "C48326" to "RED",
        //    "C48331" to "ORANGE"
        //)

        /**
         * Full FDA SPL value sets from the NCI Thesaurus (checked via NCI EVS REST API, 23.10.2026):
         * subset C54454 "SPL Shape Terminology" and subset C54453 "SPL Color Terminology".
         * Labels are the FDA preferred terms. Unknown codes are still shown raw by [codeLabel].
         */
        private val SPL_SHAPE_CODES = mapOf(
            "C48335" to "BULLET",
            "C48336" to "CAPSULE",
            "C48337" to "CLOVER",
            "C48338" to "DIAMOND",
            "C48339" to "DOUBLE CIRCLE",
            "C48340" to "FREEFORM",
            "C48341" to "GEAR",
            "C48342" to "HEPTAGON (7 sided)",
            "C48343" to "HEXAGON (6 sided)",
            "C48344" to "OCTAGON (8 sided)",
            "C48345" to "OVAL",
            "C48346" to "PENTAGON (5 sided)",
            "C48347" to "RECTANGLE",
            "C48348" to "ROUND",
            "C48349" to "SEMI-CIRCLE",
            "C48350" to "SQUARE",
            "C48351" to "TEAR",
            "C48352" to "TRAPEZOID",
            "C48353" to "TRIANGLE"
        )

        private val SPL_COLOR_CODES = mapOf(
            "C48323" to "BLACK",
            "C48324" to "GRAY",
            "C48325" to "WHITE",
            "C48326" to "RED",
            "C48327" to "PURPLE",
            "C48328" to "PINK",
            "C48329" to "GREEN",
            "C48330" to "YELLOW",
            "C48331" to "ORANGE",
            "C48332" to "BROWN",
            "C48333" to "BLUE",
            "C48334" to "TURQUOISE"
        )

        private val KNOWN_CODES = SPL_SHAPE_CODES + SPL_COLOR_CODES

        private fun reason(t: Throwable): String = t.message ?: t.localizedMessage ?: t.javaClass.simpleName

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

        /** 200807 -> 07.2008; anything else is returned as-is. */
        private fun formatYearMonth(raw: String?): String? {
            if (raw.isNullOrBlank()) return null
            val s = raw.trim()
            return if (s.length == 6 && s.all { it.isDigit() }) "${s.substring(4, 6)}.${s.substring(0, 4)}" else s
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