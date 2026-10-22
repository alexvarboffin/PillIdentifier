package com.walhalla.pillfinder.adapter.emptyView

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.sizeDp
import com.walhalla.pillfinder.R

class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var primaryText: TextView =
        view.findViewById(R.id.nothingPrimary)
    var secondaryText: TextView =
        view.findViewById(R.id.nothingSecondary)

    val ic: IconicsDrawable = IconicsDrawable(view.context, FontAwesome.Icon.faw_ticket_alt).apply {
        sizeDp = (48)
        colorList = ColorStateList.valueOf(Color.DKGRAY)
    }

    fun bind(emptyViewObj: EmptyViewObj, position: Int) {
        primaryText.text = emptyViewObj.primary
        secondaryText.text = emptyViewObj.secondary
        primaryText.setCompoundDrawablesWithIntrinsicBounds(
            null,
            ic, null, null
        )
    }
}