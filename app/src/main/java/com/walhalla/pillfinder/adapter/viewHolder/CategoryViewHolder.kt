package com.walhalla.pillfinder.adapter.viewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.adapter.ComplexPresenter

class CategoryViewHolder(view: View, blocking_flag: Int, presenter: ComplexPresenter?) :
    RecyclerView.ViewHolder(view) {
    private val textView: TextView =
        view.findViewById(R.id.textView)

    fun bind(tmp: JsonObject) {
        textView.text = "" + tmp.toString()
    }
}
