package com.walhalla.health

import android.graphics.Color
import android.graphics.drawable.GradientDrawable

object Constant {
    @JvmStatic
    fun getShapeDrawable(isStroke: Boolean, primaryColor: Int): GradientDrawable {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadii = floatArrayOf(8f, 8f, 8f, 8f, 8f, 8f, 8f, 8f)
        shape.setColor(primaryColor)

        if (isStroke) {
            shape.setStroke(3, primaryColor)
            shape.setColor(Color.WHITE)
        }

        return shape
    }
}
