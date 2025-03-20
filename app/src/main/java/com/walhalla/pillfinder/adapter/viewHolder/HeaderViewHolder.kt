package com.walhalla.pillfinder.adapter.viewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.adapter.obj.HeaderObject

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val textView: TextView = view.findViewById(R.id.textView)

    fun bind(tmp: HeaderObject) {
        textView.text = tmp.title
    }
}
