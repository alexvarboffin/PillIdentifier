package com.walhalla.pillfinder.adapter.viewHolder

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.walhalla.pillfinder.R

class SimpleTextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun setText(text1: String?, position: Int) {
        if (position % 2 > 0) {
            this.text1.setBackgroundColor(Color.WHITE)
        }
        this.text1.text = text1
    }

    val text1: TextView =
        itemView.findViewById(R.id.textView1)
}
