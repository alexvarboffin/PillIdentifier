package com.walhalla.health

import android.graphics.PorterDuff
import android.widget.ImageView

object M {
    @JvmStatic
    fun setThemeColor(primaryColor: Int, imageView: ImageView) {
        val tmp = imageView.drawable
        tmp?.setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN)
    }
}
