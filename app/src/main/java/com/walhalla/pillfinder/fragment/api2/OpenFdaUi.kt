package com.walhalla.pillfinder.fragment.api2

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.adapter.viewHolder.NewClickableStyle

/** openFDA source marking helpers (colour @color/openfda_source). */
object OpenFdaUi {
    /** NEW clickable chip (style NewClickableChip: blue stroke + underline) + leading amber openFDA dot. */
    @JvmStatic
    fun markClickableChip(chip: TextView) {
        NewClickableStyle.apply(chip)
        val dot = ContextCompat.getDrawable(chip.context, R.drawable.ic_openfda_dot)
        chip.setCompoundDrawablesRelativeWithIntrinsicBounds(dot, null, null, null)
        chip.compoundDrawablePadding = (6 * chip.resources.displayMetrics.density).toInt()
    }
}
