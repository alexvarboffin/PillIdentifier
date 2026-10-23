package com.walhalla.pillfinder.fragment.api2

import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.walhalla.lib.service.RxNormLib
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.adapter.viewHolder.NewClickableStyle
import gov.nih.nlm.rxnorm.models.ApproximateGroupResponse
import gov.nih.nlm.rxnorm.models.Candidate
import gov.nih.nlm.rxnorm.models.IdGroupResponse
import kotlinx.coroutines.Job
import java.io.IOException
import java.util.Locale

/**
 * Roadmap item #3: "Did you mean / Closest matches" fallback for Drug Search ([RxNorm]).
 *
 * Shown only when findRxcui (rxcui.json, search=2) returned no rxcui. Calls getApproximateMatch
 * (approximateTerm.json, maxEntries=[RxNormLib.APPROX_MAX_ENTRIES], option=1 = active concepts),
 * groups the returned atoms by rxcui (the API returns one row per atom, so the same rxcui repeats),
 * keeps the best rank/score per rxcui and shows up to [MAX_MATCHES] matches as NewClickableChip items.
 *
 * Names: the candidate's own name is used when it comes from the RXNORM source; otherwise (VANDF/USP
 * aliases or licensed sources like GS/MMSL where the API omits the name) the canonical name is resolved
 * with getRxNormName (rxcui/{id}.json), one cheap call per distinct rxcui, while a placeholder is shown.
 *
 * Tapping a match calls [onPick] with the rxcui; the host opens it exactly like a successful search.
 */
class ApproximateMatchPanel(
    private val panel: View,
    private val container: LinearLayout,
    private val onPick: (rxcui: String) -> Unit
) {

    private class Match(
        val rxcui: String,
        val rank: String?,
        val score: String?,
        var name: String,
        val matchedName: String?,
        val matchedSource: String?,
        val needsName: Boolean
    )

    private val jobs = mutableListOf<Job>()
    private var generation = 0

    private val ctx get() = container.context
    private val density get() = ctx.resources.displayMetrics.density
    private fun dp(v: Int) = (v * density).toInt()

    /** Starts the fallback lookup for [term] (replaces whatever the panel showed before). */
    fun search(term: String?) {
        cancelJobs()
        val t = term?.trim().orEmpty()
        if (t.isEmpty()) {
            hide()
            return
        }
        val gen = ++generation
        panel.visibility = View.VISIBLE
        container.removeAllViews()
        addHeader(t)
        container.addView(ProgressBar(ctx).apply { isIndeterminate = true }, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { gravity = Gravity.CENTER_HORIZONTAL; topMargin = dp(12) })
        container.addView(greyText(ctx.getString(R.string.approx_loading)).apply { gravity = Gravity.CENTER_HORIZONTAL })

        jobs += RxNormLib.approximateMatch(t, object : RxNormLib.LibCallback<ApproximateGroupResponse> {
            override fun onResult(body: ApproximateGroupResponse?) {
                if (gen != generation) return
                if (body == null) {
                    showMessage(t, ctx.getString(R.string.approx_error_server), canRetry = true)
                } else {
                    render(t, gen, body.approximateGroup?.candidate.orEmpty())
                }
            }

            override fun onError(t2: Throwable) {
                if (gen != generation) return
                showMessage(t, ctx.getString(R.string.approx_error, reason(t2)), canRetry = true)
            }
        })
    }

    /** Hides the panel and drops any in-flight results. */
    fun hide() {
        cancelJobs()
        generation++
        container.removeAllViews()
        panel.visibility = View.GONE
    }

    fun cancelJobs() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }

    private fun render(term: String, gen: Int, candidates: List<Candidate>) {
        val byRxcui = LinkedHashMap<String, MutableList<Candidate>>() // keeps API order = best rank first
        for (c in candidates) {
            val id = c.rxcui?.takeIf { it.isNotBlank() } ?: continue
            byRxcui.getOrPut(id) { mutableListOf() }.add(c)
        }
        if (byRxcui.isEmpty()) {
            showMessage(term, ctx.getString(R.string.approx_empty), canRetry = false)
            return
        }
        val matches = byRxcui.entries.take(MAX_MATCHES).map { (id, atoms) ->
            val best = atoms.first()
            val rxnormAtom = atoms.firstOrNull { it.source == SOURCE_RXNORM && !it.name.isNullOrBlank() }
            val alias = atoms.firstOrNull { it.source != SOURCE_RXNORM && !it.name.isNullOrBlank() }
            Match(
                rxcui = id,
                rank = best.rank,
                score = best.score,
                name = rxnormAtom?.name ?: alias?.name ?: ctx.getString(R.string.approx_rxcui_name, id),
                matchedName = alias?.name,
                matchedSource = alias?.source,
                needsName = rxnormAtom == null
            )
        }

        container.removeAllViews()
        addHeader(term)
        container.addView(greyText(ctx.getString(R.string.approx_count, matches.size)))
        for (m in matches) addMatch(m, gen)
    }

    private fun addMatch(m: Match, gen: Int) {
        val chip = TextView(ctx, null, 0, R.style.NewClickableChip)
        chip.text = chipText(m.name)
        NewClickableStyle.apply(chip)
        chip.setOnClickListener { onPick(m.rxcui) }
        container.addView(chip, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { topMargin = dp(12) })

        val details = StringBuilder(ctx.getString(R.string.approx_details, m.rxcui, m.rank ?: "?", formatScore(m.score)))
        if (m.needsName && m.matchedName != null) {
            details.append('\n').append(ctx.getString(R.string.approx_matched, m.matchedName, m.matchedSource ?: "?"))
        }
        container.addView(greyText(details.toString()))

        if (m.needsName) {
            jobs += RxNormLib.rxNormName(m.rxcui, object : RxNormLib.LibCallback<IdGroupResponse> {
                override fun onResult(body: IdGroupResponse?) {
                    if (gen != generation) return
                    val name = body?.idGroup?.name
                    if (!name.isNullOrBlank()) {
                        m.name = name
                        chip.text = chipText(name)
                    }
                }

                override fun onError(t: Throwable) { /* keep the placeholder/alias name */ }
            })
        }
    }

    private fun showMessage(term: String, text: String, canRetry: Boolean) {
        container.removeAllViews()
        addHeader(term)
        container.addView(greyText(text))
        if (canRetry) {
            val retry = TextView(ctx, null, 0, R.style.NewClickableChip)
            retry.text = ctx.getString(R.string.approx_retry)
            retry.setOnClickListener { search(term) }
            container.addView(retry, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { topMargin = dp(8) })
        }
    }

    private fun addHeader(term: String) {
        container.addView(TextView(ctx).apply {
            text = ctx.getString(R.string.approx_title, term)
            setTypeface(typeface, Typeface.BOLD)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            setTextColor(ContextCompat.getColor(ctx, R.color.colorPrimary))
        })
        container.addView(TextView(ctx).apply {
            text = ctx.getString(R.string.approx_subtitle)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        })
    }

    private fun greyText(s: String) = TextView(ctx).apply {
        text = s
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
        setTextColor(ContextCompat.getColor(ctx, R.color.ndc_card_missing))
        setPadding(0, dp(4), 0, 0)
    }

    private fun chipText(name: String) = "$name  \u203A"

    companion object {
        /** Distinct rxcuis shown (after grouping atoms by rxcui). */
        const val MAX_MATCHES = 8
        private const val SOURCE_RXNORM = "RXNORM"

        private fun formatScore(score: String?): String =
            score?.toDoubleOrNull()?.let { String.format(Locale.US, "%.2f", it) } ?: (score ?: "?")

        private fun reason(t: Throwable): String = when (t) {
            is IOException -> "no connection"
            else -> t.localizedMessage ?: t.javaClass.simpleName
        }
    }
}