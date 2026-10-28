package com.walhalla.pillfinder.fragment.api2

import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.walhalla.pillfinder.R

/**
 * loading / friendly error (+retry) / empty overlay for RxClass screens (Classes tab, AnalogsActivity),
 * same idea as FragmentFda.Block. Added over category_list_fragment (RelativeLayout root).
 * Raw exceptions go to Log only, never to the UI.
 */
class RxClassStateView(root: ViewGroup, private val onRetry: () -> Unit) {
    private val ctx = root.context
    private val box = LinearLayout(ctx)
    private val bar = ProgressBar(ctx)
    private val msg = TextView(ctx)
    private val retry = Button(ctx)

    init {
        val d = ctx.resources.displayMetrics.density
        box.orientation = LinearLayout.VERTICAL
        box.gravity = Gravity.CENTER_HORIZONTAL
        box.setPadding((24 * d).toInt(), 0, (24 * d).toInt(), 0)
        box.addView(bar, LinearLayout.LayoutParams(-2, -2))
        msg.textSize = 15f
        msg.gravity = Gravity.CENTER
        // category_list_fragment has a fixed light background (#f1f5f8) in both themes -> fixed dark text
        msg.setTextColor(Color.parseColor("#263238"))
        box.addView(msg, LinearLayout.LayoutParams(-2, -2))
        retry.setText(R.string.rxclass_retry)
        retry.setOnClickListener { onRetry() }
        box.addView(retry, LinearLayout.LayoutParams(-2, -2).apply { topMargin = (8 * d).toInt() })
        val lp: ViewGroup.LayoutParams = if (root is RelativeLayout)
            RelativeLayout.LayoutParams(-1, -2).apply { addRule(RelativeLayout.CENTER_IN_PARENT) }
        else ViewGroup.LayoutParams(-1, -2)
        root.addView(box, lp)
        done()
    }

    fun loading() = show(true, null, false)
    fun empty() = show(false, ctx.getString(R.string.rxclass_empty), false)
    fun error(t: Throwable) {
        Log.w(TAG, "RxClass request failed", t)
        show(false, ctx.getString(friendlyError(t)), true)
    }
    fun done() { box.visibility = View.GONE }

    private fun show(progress: Boolean, text: String?, canRetry: Boolean) {
        box.visibility = View.VISIBLE
        bar.visibility = if (progress) View.VISIBLE else View.GONE
        msg.visibility = if (text != null) View.VISIBLE else View.GONE
        msg.text = text
        retry.visibility = if (canRetry) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "RxClassState"
        fun friendlyError(t: Throwable): Int = when {
            t is java.io.IOException || t.javaClass.name.contains("Timeout") -> R.string.rxclass_err_no_connection
            else -> R.string.rxclass_err_generic
        }
    }
}
