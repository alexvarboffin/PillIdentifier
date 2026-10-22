package com.walhalla.pillfinder.fragment.api2

import android.content.Context
import android.widget.ArrayAdapter

class SuggestionAdapter(
    context: Context,
    private val items: MutableList<String>
) : ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, items) {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): String? = items[position]

    fun updateData(newItems: List<String>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
} 