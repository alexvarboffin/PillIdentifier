package com.walhalla.bloodAlcohol

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.walhalla.health.R

class VolumeAdapter internal constructor(context: Context, i: Int, strArr: Array<String>) : ArrayAdapter<String>(context, i, strArr) {

    private val arr = arrayOf(
        context.resources.getString(R.string.ounces),
        context.resources.getString(R.string.ml),
        context.resources.getString(R.string.cup)
    )
    private val vol_img = intArrayOf(R.drawable.volume, R.drawable.volume, R.drawable.volume)

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
            vol_img[i]
        )
        return inflate
    }
}