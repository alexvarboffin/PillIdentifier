package com.walhalla.pillfinder.adapter.obj.ingredient

import android.graphics.Color
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.walhalla.pillfinder.R

class IngredientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val text1: TextView =
        itemView.findViewById(R.id.textView1)
    private val text2: TextView =
        itemView.findViewById(R.id.textView2)
    val ll: RelativeLayout = itemView.findViewById(R.id.ll)
    val btn: View = itemView.findViewById(R.id.search_btn)

    fun setIngredient(text1: IngredientString, position: Int) {
        if (position % 2 > 0) {
            ll.setBackgroundColor(Color.WHITE)
        }
        this.text1.text = "" + text1.id + ": "
        text2.text = "" + text1.title
    }
}
