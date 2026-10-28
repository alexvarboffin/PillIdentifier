package com.walhalla.pillfinder.fragment.api2

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import android.widget.HorizontalScrollView
import com.walhalla.lib.service.OpenFdaLib
import com.walhalla.pillfinder.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Last Drug Search tab "FDA" (openFDA by rxcui). Four independent blocks, each with
 * loading / error (+retry) / empty state: label, recalls, NDC products, top reactions (FAERS, with disclaimer).
 * Loaded lazily on the first onResume (= tab actually shown; ViewPager2 keeps other pages STARTED).
 * Every block carries the openFDA source marking (amber stripe + tint + "openFDA" badge).
 * package_ndc chips = NEW clickable (NewClickableChip + amber dot) -> NdcPillCardDialog.
 */
class FragmentFda : Fragment() {

    private var rxcui: String = ""
    private var loaded = false
    private val jobs = ArrayList<Job>()

    private lateinit var label: Block
    private lateinit var recalls: Block
    private lateinit var ndc: Block
    private lateinit var reactions: Block

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rxcui = arguments?.getString(ARG_RXCUI).orEmpty()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val ctx = requireContext()
        val root = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(12), dp(12), dp(12), dp(96))
        }
        label = Block(root, getString(R.string.fda_block_label)) { loadLabel() }
        recalls = Block(root, getString(R.string.fda_block_recalls)) { loadRecalls() }
        ndc = Block(root, getString(R.string.fda_block_ndc)) { loadNdc() }
        reactions = Block(root, getString(R.string.fda_block_reactions)) { loadReactions() }
        reactions.footer(getString(R.string.fda_reactions_disclaimer))
        loaded = false
        return ScrollView(ctx).apply { addView(root) }
    }

    override fun onResume() {
        super.onResume()
        if (!loaded && rxcui.isNotEmpty()) {
            loaded = true
            loadLabel(); loadRecalls(); loadNdc(); loadReactions()
        }
    }

    override fun onDestroyView() {
        jobs.forEach { it.cancel() }; jobs.clear()
        super.onDestroyView()
    }

    private fun launch(block: suspend () -> Unit) {
        jobs += viewLifecycleOwner.lifecycleScope.launch { block() }
    }

    private fun loadLabel() {
        label.loading()
        launch {
            OpenFdaLib.labelByRxcui(rxcui).fold({ list ->
                val l = list.firstOrNull() ?: return@fold label.empty()
                label.done()
                label.row("Brand", l.openfda?.brandName?.joinToString())
                label.row("Generic", l.openfda?.genericName?.joinToString())
                label.row("Manufacturer", l.openfda?.manufacturerName?.joinToString())
                label.row("Effective", formatDate(l.effectiveTime))
                label.row("Boxed warning", l.boxedWarning?.firstOrNull()?.take(400))
                label.row("Purpose", l.purpose?.firstOrNull()?.take(300))
                label.row("Indications", l.indicationsAndUsage?.firstOrNull()?.take(400))
                label.row("Set ID", l.setId)
            }, { label.error(it) })
        }
    }

    private fun loadRecalls() {
        recalls.loading()
        launch {
            OpenFdaLib.recalls(rxcui).fold({ list ->
                if (list.isEmpty()) return@fold recalls.empty()
                recalls.done()
                list.forEach { r ->
                    recalls.row(
                        "${r.status.orEmpty()} · ${r.classification.orEmpty()} · ${formatDate(r.recallInitiationDate).orEmpty()}",
                        "${r.recallingFirm.orEmpty()}\n${r.reasonForRecall.orEmpty().take(300)}"
                    )
                }
            }, { recalls.error(it) })
        }
    }

    private fun loadNdc() {
        ndc.loading()
        launch {
            OpenFdaLib.ndcProducts(rxcui).fold({ list ->
                if (list.isEmpty()) return@fold ndc.empty()
                ndc.done()
                list.forEach { p ->
                    val ingr = p.activeIngredients.orEmpty().joinToString { "${it.name.orEmpty()} ${it.strength.orEmpty()}" }
                    ndc.row("${p.brandName ?: p.genericName.orEmpty()} · ${p.dosageForm.orEmpty()}",
                        "${p.labelerName.orEmpty()} · product NDC ${p.productNdc.orEmpty()}\n$ingr")
                    val flex = LinearLayout(requireContext()).apply { orientation = LinearLayout.HORIZONTAL }
                    p.packaging.orEmpty().mapNotNull { it.packageNdc }.forEach { pkg ->
                        val chip = TextView(requireContext(), null, 0, R.style.NewClickableChip)
                        chip.text = pkg
                        chip.textSize = 14f
                        OpenFdaUi.markClickableChip(chip)
                        chip.setOnClickListener { NdcPillCardDialog.show(parentFragmentManager, pkg, false) }
                        flex.addView(chip, ViewGroup.MarginLayoutParams(-2, -2).apply { marginEnd = dp(6) })
                    }
                    ndc.content.addView(HorizontalScrollView(requireContext()).apply { addView(flex) })
                }
            }, { ndc.error(it) })
        }
    }

    private fun loadReactions() {
        reactions.loading()
        launch {
            OpenFdaLib.topReactions(rxcui).fold({ list ->
                if (list.isEmpty()) return@fold reactions.empty()
                reactions.done()
                list.forEach { reactions.row(it.term.orEmpty().lowercase(), it.count?.toString()) }
            }, { reactions.error(it) })
        }
    }

    /** One openFDA block: amber stripe/tint card, title + "openFDA" badge, state views, content. */
    private inner class Block(parent: LinearLayout, title: String, private val onRetry: () -> Unit) {
        private val ctx = parent.context
        private val card = LinearLayout(ctx)
        private val bar = ProgressBar(ctx)
        private val msg = TextView(ctx)
        private val retry = Button(ctx)
        val content = LinearLayout(ctx)
        // was: ContextCompat.getColor(ctx, android.R.color.black) - unreadable on the dark night tint
        private val textColor = ContextCompat.getColor(ctx, R.color.openfda_text)

        init {
            card.orientation = LinearLayout.VERTICAL
            card.setBackgroundResource(R.drawable.bg_openfda_block)
            card.setPadding(dp(14), dp(8), dp(10), dp(10))
            parent.addView(card, LinearLayout.LayoutParams(-1, -2).apply { bottomMargin = dp(12) })

            val head = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
            head.addView(TextView(ctx).apply {
                text = title
                setTypeface(typeface, Typeface.BOLD)
                textSize = 15f
                setTextColor(ContextCompat.getColor(ctx, R.color.openfda_source))
            }, LinearLayout.LayoutParams(0, -2, 1f))
            head.addView(TextView(ctx).apply {
                setText(R.string.fda_source_badge)
                textSize = 10f
                setTypeface(typeface, Typeface.BOLD)
                setTextColor(ContextCompat.getColor(ctx, R.color.openfda_source_on))
                setBackgroundResource(R.drawable.bg_openfda_badge)
                setPadding(dp(6), dp(1), dp(6), dp(1))
            })
            card.addView(head)
            card.addView(bar, LinearLayout.LayoutParams(-2, -2).apply { gravity = Gravity.CENTER_HORIZONTAL })
            msg.textSize = 14f
            msg.setTextColor(textColor)
            card.addView(msg)
            retry.setText(R.string.fda_retry)
            retry.setOnClickListener { onRetry() }
            card.addView(retry, LinearLayout.LayoutParams(-2, -2))
            content.orientation = LinearLayout.VERTICAL
            card.addView(content)
            loading()
        }

        fun footer(text: String) {
            card.addView(TextView(ctx).apply {
                this.text = text
                textSize = 11f
                setTypeface(typeface, Typeface.ITALIC)
                setTextColor(ContextCompat.getColor(ctx, R.color.openfda_source))
                setPadding(0, dp(6), 0, 0)
            })
        }

        fun loading() { show(true, null, false) }
        fun empty() { show(false, getString(R.string.fda_empty), false) }
        fun error(t: Throwable) {
            // was: show(false, getString(R.string.fda_error, t.message ?: t.javaClass.simpleName), true)
            android.util.Log.w(TAG, "openFDA request failed (rxcui=$rxcui)", t) // raw exception only to Log
            show(false, getString(friendlyError(t)), true)
        }
        fun done() { show(false, null, false) }

        private fun show(progress: Boolean, text: String?, canRetry: Boolean) {
            bar.visibility = if (progress) View.VISIBLE else View.GONE
            msg.visibility = if (text != null) View.VISIBLE else View.GONE
            msg.text = text
            retry.visibility = if (canRetry) View.VISIBLE else View.GONE
            content.removeAllViews()
        }

        fun row(title: String, value: String?) {
            if (value.isNullOrBlank()) return
            content.addView(TextView(ctx).apply {
                text = title
                setTypeface(typeface, Typeface.BOLD)
                textSize = 13f
                setTextColor(textColor)
                setPadding(0, dp(6), 0, 0)
            })
            content.addView(TextView(ctx).apply {
                text = value
                textSize = 13f
                setTextColor(textColor)
                setTextIsSelectable(true)
            })
        }
    }

    private fun dp(v: Int): Int = (v * resources.displayMetrics.density).toInt()

    companion object {
        private const val ARG_RXCUI = "arg_rxcui"
        private const val TAG = "FragmentFda"

        /** User-facing text for a failed openFDA call (no raw exception text in the UI). */
        @JvmStatic
        fun friendlyError(t: Throwable): Int = when {
            t is OpenFdaLib.OpenFdaHttpException && t.status == 403 -> R.string.fda_err_forbidden
            t is OpenFdaLib.OpenFdaHttpException && t.status == 429 -> R.string.fda_err_rate_limit
            t is OpenFdaLib.OpenFdaHttpException -> R.string.fda_err_generic
            t is java.io.IOException || t.javaClass.name.contains("Timeout") -> R.string.fda_err_no_connection // connect/unknown host/timeouts
            else -> R.string.fda_err_generic
        }

        private fun formatDate(raw: String?): String? {
            val s = raw?.trim() ?: return null
            return if (s.length == 8 && s.all { it.isDigit() }) "${s.substring(6, 8)}.${s.substring(4, 6)}.${s.substring(0, 4)}" else s
        }

        @JvmStatic
        fun newInstance(rxcui: String?): FragmentFda = FragmentFda().apply {
            arguments = Bundle().apply { putString(ARG_RXCUI, rxcui) }
        }
    }
}
