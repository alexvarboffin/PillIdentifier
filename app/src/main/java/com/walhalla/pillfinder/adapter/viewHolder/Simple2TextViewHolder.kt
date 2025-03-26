package com.walhalla.pillfinder.adapter.viewHolder

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.adapter.obj.NValue

class Simple2TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val layout: LinearLayout = itemView.findViewById(R.id.lLayout1)

    fun setText(value: NValue, position: Int) {
        if (position % 2 > 0) {
            layout.setBackgroundColor(Color.WHITE)
        }
        text1.text = value.name
        text2.text = value.value
    }

    private val text1: TextView =
        itemView.findViewById(R.id.textView1)
    val text2: TextView =
        itemView.findViewById(R.id.textView2)
}
