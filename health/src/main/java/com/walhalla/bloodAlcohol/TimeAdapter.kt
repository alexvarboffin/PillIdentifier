package com.walhalla.bloodAlcohol

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.walhalla.health.R

class TimeAdapter internal constructor(context: Context, i: Int, strArr: Array<String>) : ArrayAdapter<String?>(context, i, strArr) {
    private val arr = arrayOf(
        context.resources.getString(R.string.hour),
        context.resources.getString(R.string.minute),
        context.resources.getString(R.string.day)
    )
    var time_img: IntArray = intArrayOf(R.drawable.time, R.drawable.time, R.drawable.time)

    override fun getDropDownView(i: Int, view: View, viewGroup: ViewGroup): View {
        return getCustomView(i, viewGroup)
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        return getCustomView(i, viewGroup)
    }

    fun getCustomView(i: Int, viewGroup: ViewGroup?): View {
        val inflate =
            LayoutInflater.from(context).inflate(R.layout.spinner_down_blue, viewGroup, false)
        (inflate.findViewById<View>(R.id.currency) as TextView).text =
            arr[i]
        (inflate.findViewById<View>(R.id.image) as ImageView).setImageResource(
            time_img[i]
        )
        return inflate
    }
}