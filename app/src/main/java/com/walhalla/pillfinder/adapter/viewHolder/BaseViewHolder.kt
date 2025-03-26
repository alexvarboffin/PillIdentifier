package com.walhalla.pillfinder.adapter.viewHolder

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.walhalla.pillfinder.adapter.ComplexPresenter

abstract class BaseViewHolder<R>(view: View, protected var view_state_number: Int,
    val presenter: ComplexPresenter
) :
    RecyclerView.ViewHolder(view) {
    private fun inflater(viewGroup: ViewGroup): View {
        return itemView
    }

    abstract fun bind(model: R)
}
