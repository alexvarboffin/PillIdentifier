package com.walhalla.pillfinder.adapter.emptyView

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.iconics.IconicsDrawable
import com.walhalla.pillfinder.R

class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var primaryText: TextView =
        view.findViewById(R.id.nothingPrimary)
    var secondaryText: TextView =
        view.findViewById(R.id.nothingSecondary)

    fun bind(emptyViewObj: EmptyViewObj, position: Int) {
        primaryText.text = emptyViewObj.primary
        secondaryText.text = emptyViewObj.secondary
        primaryText.setCompoundDrawablesWithIntrinsicBounds(
            null,
            IconicsDrawable(primaryText.context)
                .icon(FontAwesome.Icon.faw_ticket)
                .sizeDp(48).color(Color.DKGRAY), null, null
        )
    }
}