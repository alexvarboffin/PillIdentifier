package com.walhalla.pillfinder.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.walhalla.pillfinder.BuildConfig
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.databinding.RowItemNlmrximagesBinding
import com.walhalla.ui.DLog.getAppVersion
import gov.nih.nlm.model.APIError
import gov.nih.nlm.model.ModelObject
import gov.nih.nlm.model.NlmRxImage
import okhttp3.PicassoHelper
import okhttp3.PicassoHelper.Companion.getInstance

/**
 * Created by combo on 25.03.2020.
 */
class PaginationAdapter @JvmOverloads constructor(
    private val context: Context, // The items to display in your RecyclerView
    private val items: MutableList<Any> = ArrayList()
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var isLoaderVisible = false

    interface ChildItemClickListener {
        fun onClick(o: NlmRxImage)
    }

    private val picasso: PicassoHelper?

    private var childItemClickListener: ChildItemClickListener? = null

    /**
     * Constructor
     *
     * @param context
     */
    init {
        this.picasso = getInstance(context)
    }

    fun setChildItemClickListener(listener: ChildItemClickListener?) {
        childItemClickListener = listener
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return if (this.items == null) 0 else items.size
    }

    fun add(mc: Any) {
        items!!.add(mc)
        notifyItemInserted(items.size - 1)
    }


    fun addAll(data: List<Any>) {
//        DLog.d("ADDED: " + data);
//        for (Object mc : data) {
//            add(mc);
//        }

        items!!.addAll(data)
        this.notifyDataSetChanged()
    }

    fun addItems(data: List<Any>) {
        items!!.addAll(data)
        notifyDataSetChanged()
    }

    fun remove(city: Any) {
//        DLog.d("REMOVED: " + city);

        val position = items!!.indexOf(city)
        if (position > -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
//        DLog.d("CLEAR: ");
        isLoaderVisible = false
        while (itemCount > 0) {
            remove(getItem(0))
        }
    }

    val isEmpty: Boolean
        get() = itemCount == 0


    fun addLoadingFooter() {
        isLoaderVisible = true
        //add(new Object());
        items!!.add("")
        notifyItemInserted(items.size - 1)
    }

    fun removeLoading() {
//        DLog.d("REMOVE_LOADING: ");
        isLoaderVisible = false
        val position = items!!.size - 1
        if (position > 0) {
            val item = getItem(position)
            if (item != null) {
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    fun getItem(position: Int): Any {
        return items!![position]
    }

    //Returns the view type of the item at position for the purposes of view recycling.
    override fun getItemViewType(position: Int): Int {
        if (isLoaderVisible && position == items!!.size - 1) {
            return VIEW_TYPE_LOADING
        } else {
            if (items!![position] is NlmRxImage) {
                return TYPE_NLMRXIMAGES
            } else if (items[position] is APIError) {
                return TYPE_INFORMATION
            } else if (items[position] is ModelObject) {
                return TYPE_POSTMODEL
            }
            return VIEW_TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(viewGroup.context)

        when (viewType) {
            TYPE_NLMRXIMAGES -> {
                val v1 = RowItemNlmrximagesBinding.inflate(inflater, viewGroup, false)
                viewHolder = NlmRxImagesViewHolder(v1, childItemClickListener)
            }

            TYPE_POSTMODEL -> {
                val v2 = inflater.inflate(R.layout.fragment_information, viewGroup, false)
                viewHolder = ViewHolder2(v2)
            }

            TYPE_INFORMATION -> {
                val v3 = inflater.inflate(R.layout.fragment_information, viewGroup, false)
                viewHolder = ViewHolder2(v3)
            }

            VIEW_TYPE_LOADING -> viewHolder =
                ProgressHolder(inflater.inflate(R.layout.item_loading, viewGroup, false))

            else -> {
                val v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false)
                viewHolder = RecyclerViewSimpleTextViewHolder(v)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_NLMRXIMAGES -> {
                val vh1 = viewHolder as NlmRxImagesViewHolder
                val obj = items!![position] as NlmRxImage
                vh1.bind(context, picasso, obj)
            }

            TYPE_INFORMATION -> {
                val vh2 = viewHolder as ViewHolder2
                configureViewHolder2(vh2)
            }

            VIEW_TYPE_LOADING -> {}
            else -> {
                val holder = viewHolder as RecyclerViewSimpleTextViewHolder
                val n = items!![position].toString()
                holder.bind(n)
            }
        }
    }

    private fun configureViewHolder2(vh2: ViewHolder2) {
        if (BuildConfig.DEBUG) {
            vh2.title.text = getAppVersion(context)
        }
    }

    class RecyclerViewSimpleTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val label: TextView =
            itemView.findViewById(android.R.id.text1)

        fun bind(n: String?) {
            label.text = n
        }
    }

    class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView =
            itemView.findViewById(R.id.tv_title)
    }

    class ProgressHolder(itemView: View) //        @Override
    //        protected void clear() {
    //        }
        : RecyclerView.ViewHolder(itemView)

    companion object {
        const val TYPE_NLMRXIMAGES: Int = 100
        const val TYPE_INFORMATION: Int = 101
        const val TYPE_POSTMODEL: Int = 111
        const val VIEW_TYPE_LOADING: Int = 123
    }
}