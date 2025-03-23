package com.walhalla.health.adapters

import android.app.Activity
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.walhalla.health.R
import com.walhalla.health.adapters.LazyAdapter.MyViewHolder
import com.walhalla.health.models.RowItem

class LazyAdapter(private val rowItems: List<RowItem>, private val activity: Activity) : RecyclerView.Adapter<MyViewHolder>() {
    private var anInterface: clickInterface? = null


    interface clickInterface {
        fun onRecItemClick(view: View?, i: Int)
    }

    fun setListeners(listeners: clickInterface?) {
        anInterface = listeners
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.list_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        val rowItem = rowItems[position]
        viewHolder.list_image.setImageResource(rowItem.imageId)
        viewHolder.img_next.setImageResource(rowItem.icon)
        viewHolder.title.setText(rowItem.title)
        viewHolder.description.setText(rowItem.description)
        viewHolder.card.startAnimation(
            AnimationUtils.loadAnimation(
                activity,
                R.anim.card_animation
            )
        )

        val drawable = ContextCompat.getDrawable(activity, R.drawable.home_image_bg)
        if (drawable != null) {
            drawable.colorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(activity, rowItem.color),
                PorterDuff.Mode.SRC_IN
            )
            drawable.mutate()
            viewHolder.image_layout.background = drawable
        }
    }


    override fun getItemCount(): Int {
        return rowItems.size
    }

    inner class MyViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var card: FrameLayout = itemView.findViewById(R.id.card)
        var list_image: ImageView =
            itemView.findViewById(R.id.list_image)
        var img_next: ImageView =
            itemView.findViewById(R.id.img_next)
        var image_layout: RelativeLayout = itemView.findViewById(R.id.image_layout)
        var title: TextView =
            itemView.findViewById(R.id.title)
        var description: TextView =
            itemView.findViewById(R.id.description)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (anInterface != null) {
                anInterface!!.onRecItemClick(v, adapterPosition)
            }
        }
    }
}
