package com.walhalla.pillfinder.adapter.viewHolder

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.walhalla.pillfinder.R
import android.widget.ImageView
import android.widget.PopupMenu
import com.walhalla.pillfinder.MyApp
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface OnMoreActionListener {
    fun onMoreAction(rxcui: String, action: MoreAction)
}

enum class MoreAction {
    SHOW_CLASSES,
    SHOW_RXTERMS,
    SHOW_ANALOGS
}

class SimpleTextViewHolder(view: View, private val moreActionListener: OnMoreActionListener?) : RecyclerView.ViewHolder(view) {
    private val moreBtn: ImageView = itemView.findViewById(R.id.more_options)

    fun setText(text1: String?, position: Int) {
        if (position % 2 > 0) {
            this.text1.setBackgroundColor(Color.WHITE)
        }
        this.text1.text = text1
        moreActionListener?.let {
            moreBtn.visibility = View.VISIBLE
            moreBtn.setOnClickListener { v ->
                showMoreMenu(v, text1, it)
            }
        }
    }

    private fun showMoreMenu(anchor: View, rxcui: String?, moreActionListener: OnMoreActionListener) {
        val popup = PopupMenu(itemView.context, anchor)
        popup.menu.add("Показать классы препарата")
        popup.menu.add("Показать RxTerms")
        popup.menu.add("Показать аналоги")
        popup.setOnMenuItemClickListener { item ->
            when (item.title) {
                "Показать классы препарата" -> {
                    moreActionListener.onMoreAction(rxcui ?: "", MoreAction.SHOW_CLASSES)
                    true
                }
                "Показать RxTerms" -> {
                    moreActionListener.onMoreAction(rxcui ?: "", MoreAction.SHOW_RXTERMS)
                    true
                }
                "Показать аналоги" -> {
                    moreActionListener.onMoreAction(rxcui ?: "", MoreAction.SHOW_ANALOGS)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    val text1: TextView =
        itemView.findViewById(R.id.textView1)
}
