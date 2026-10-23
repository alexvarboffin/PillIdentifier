package com.walhalla.pillfinder.adapter.viewHolder

import android.graphics.Paint
import android.widget.TextView

/**
 * Marks a TextView as a NEW clickable object (pairs with @style/NewClickableChip):
 * the style gives accent colour + chip background, this adds the underline (not expressible in a style).
 */
object NewClickableStyle {
    @JvmStatic
    fun apply(textView: TextView) {
        textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }
}