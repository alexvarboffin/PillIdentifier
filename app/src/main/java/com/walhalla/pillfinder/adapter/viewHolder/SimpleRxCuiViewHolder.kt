package com.walhalla.pillfinder.adapter.viewHolder

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.adapter.ComplexPresenter
import com.walhalla.pillfinder.adapter.obj.RxCuiObjString

class SimpleRxCuiViewHolder(view: View, private val presenter: ComplexPresenter) :
    RecyclerView.ViewHolder(view) {
    private val layout: ViewGroup = itemView.findViewById(R.id.lLayout1)
    private val btn: View = itemView.findViewById(R.id.search_btn)

    fun bind(`object`: RxCuiObjString, position: Int) {
        if (position % 2 > 0) {
            layout.setBackgroundColor(Color.WHITE)
        }
        text1.text = "" + `object`.rxcui
        btn.setOnClickListener { v: View? ->
            presenter.onItemClicked(
                v!!, `object`
            )
        }
    }


    private val text1: TextView = itemView.findViewById(R.id.textView1)
}
