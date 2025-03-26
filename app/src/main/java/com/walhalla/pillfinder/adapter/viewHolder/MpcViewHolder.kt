package com.walhalla.pillfinder.adapter.viewHolder

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.walhalla.pillfinder.MpcField
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.adapter.ComplexPresenter
import com.walhalla.pillfinder.adapter.mpc.MpcObj

class MpcViewHolder(view: View, private val presenter: ComplexPresenter) :
    RecyclerView.ViewHolder(view) {
    private val layout: ViewGroup = itemView.findViewById(R.id.lLayout1)
    private val btn: View = itemView.findViewById(R.id.search_btn)

    fun bind(`object`: MpcObj, position: Int) {
        if (position % 2 > 0) {
            layout.setBackgroundColor(Color.WHITE)
        }
        makeField(`object`.field)
        text2.text = `object`.value
        btn.setOnClickListener { v: View? ->
            presenter.onItemClicked(
                v!!, `object`
            )
        }
    }

    private fun makeField(field: MpcField) {
        val res = if (field == MpcField.IMPRINT) {
            R.string.mpc_imprint
        } else if (field == MpcField.COLOR) {
            R.string.mpc_color
        } else if (field == MpcField.SHAPE) {
            R.string.mpc_shape
        } else if (field == MpcField.SCORE) {
            R.string.mpc_score
        } else if (field == MpcField.SIZE) {
            R.string.mpc_size
        } else if (field == MpcField.IMPRINT_COLOR) {
            R.string.mpc_imprint_color
        } else if (field == MpcField.IMPRINT_TYPE) {
            R.string.mpc_imprint_type
        } else if (field == MpcField.SYMBOL) {
            R.string.mpc_symbol
        } else {
            R.string.app_name
        }
        text1.setText(res)
    }

    private val text1: TextView = itemView.findViewById(R.id.textView1)
    private val text2: TextView = itemView.findViewById(R.id.textView2)
}
