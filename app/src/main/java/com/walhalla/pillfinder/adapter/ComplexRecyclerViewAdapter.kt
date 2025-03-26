package com.walhalla.pillfinder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.adapter.emptyView.EmptyViewHolder
import com.walhalla.pillfinder.adapter.emptyView.EmptyViewObj
import com.walhalla.pillfinder.adapter.mpc.MpcObj
import com.walhalla.pillfinder.adapter.obj.HeaderObject
import com.walhalla.pillfinder.adapter.obj.NameValue1_2
import com.walhalla.pillfinder.adapter.obj.NameValue2_1
import com.walhalla.pillfinder.adapter.obj.RxCuiObjString
import com.walhalla.pillfinder.adapter.obj.SimpleString
import com.walhalla.pillfinder.adapter.obj.VieModel
import com.walhalla.pillfinder.adapter.obj.ingredient.IngredientString
import com.walhalla.pillfinder.adapter.obj.ingredient.IngredientViewHolder
import com.walhalla.pillfinder.adapter.viewHolder.CategoryViewHolder
import com.walhalla.pillfinder.adapter.viewHolder.HeaderViewHolder
import com.walhalla.pillfinder.adapter.viewHolder.MpcViewHolder
import com.walhalla.pillfinder.adapter.viewHolder.Simple2TextViewHolder
import com.walhalla.pillfinder.adapter.viewHolder.SimpleRxCuiViewHolder
import com.walhalla.pillfinder.adapter.viewHolder.SimpleTextViewHolder

class ComplexRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val TYPE_RxCuiObjString = 334

    private val EMPTY_VIEW_DEFAULT = 777
    private val EMPTY_VIEW_FROM_OBJECT = 778


    private val TYPE_ERROR = 0
    private val TYPE_COUPON_ITEM = 178
    private val context: Context

    private var presenter: ComplexPresenter? = null

    var blocking_flag: Int = 0


    fun setChildItemClickListener(listener: ComplexPresenter?) {
        presenter = listener
    }


    //    private final ComplexPresenter presenter;
    constructor(context: Context /*ComplexPresenter presenter,*/) {
//        this.presenter = presenter;
        this.context = context
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    constructor( /*ComplexPresenter presenter,*/
                 context: Context, items: List<VieModel>
    ) {
//        this.presenter = presenter;
        this.context = context
        this.datalist = items
    }


    fun onRestoreInstanceState(restoredData: List<VieModel>) {
        this.datalist = restoredData
        this.notifyDataSetChanged()
    }

    interface Callback {
        fun onItemClicked(v: View, position: Int)

        fun onItemClicked(v: View, obj: VieModel)

        fun onItemClicked(itemId: Int, category: VieModel)
    }


    // The items to display in your RecyclerView
    private var datalist: List<VieModel> = ArrayList()


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return if (datalist.size > 0) datalist.size else 1
    }

    //Returns the view type of the item at position for the purposes of view recycling.
    override fun getItemViewType(position: Int): Int {
        if (datalist.isEmpty()) {
            return EMPTY_VIEW_DEFAULT
        } else if (datalist[position] is EmptyViewObj) {
            return EMPTY_VIEW_FROM_OBJECT
        } else if (datalist[position] is SimpleString) {
            return TYPE_SIMPLE_STRING_
        } else if (datalist[position] is IngredientString) {
            return TYPE_INGREDIENT_
        } else if (datalist[position] is NameValue1_2) {
            return TYPE_VALUE_1_2
        } else if (datalist[position] is NameValue2_1) {
            return TYPE_VALUE_2_1
        } else if (datalist[position] is HeaderObject) {
            return TYPE_HEADER
        } else if (datalist[position] is RxCuiObjString) {
            return TYPE_RxCuiObjString
        } else if (datalist[position] is MpcObj) {
            return TYPE_MPC_SHAPE
        }
        return -1
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val view: View

        when (viewType) {
            TYPE_COUPON_ITEM -> {
                view = getContentResource(viewType, viewGroup)
                viewHolder = CategoryViewHolder(view, blocking_flag, presenter)
            }

            EMPTY_VIEW_DEFAULT, EMPTY_VIEW_FROM_OBJECT -> {
                val v2 = getContentResource(R.layout.nothing_yet, viewGroup)
                viewHolder = EmptyViewHolder(v2)
            }

            TYPE_SIMPLE_STRING_ -> {
                val v20 = getContentResource(R.layout.item_string, viewGroup)
                viewHolder = SimpleTextViewHolder(v20)
            }

            TYPE_INGREDIENT_ -> {
                val contentResource = getContentResource(R.layout.item_rx_ingredient, viewGroup)
                viewHolder = IngredientViewHolder(contentResource)
            }

            TYPE_VALUE_1_2 -> {
                val v201 = getContentResource(R.layout.item_name_value_1_2, viewGroup)
                viewHolder = Simple2TextViewHolder(v201)
            }

            TYPE_VALUE_2_1 -> {
                val v20z1 = getContentResource(R.layout.item_name_value_2_1, viewGroup)
                viewHolder = Simple2TextViewHolder(v20z1)
            }

            TYPE_HEADER -> {
                val v20a1 = getContentResource(R.layout.item_header, viewGroup)
                viewHolder = HeaderViewHolder(v20a1)
            }

            TYPE_MPC_SHAPE -> {
                val view1 = getContentResource(R.layout.item_mpc, viewGroup)
                viewHolder = MpcViewHolder(view1, presenter!!)
            }

            TYPE_RxCuiObjString -> {
                val view2 = getContentResource(R.layout.item_rxcui, viewGroup)
                viewHolder = SimpleRxCuiViewHolder(view2, presenter!!)
            }

            else -> {
                view = getContentResource(android.R.layout.simple_list_item_1, viewGroup)
                viewHolder = RecyclerViewSimpleTextViewHolder(view)
            }
        }

        return viewHolder
    }

    private fun getContentResource(@LayoutRes res_id: Int, viewGroup: ViewGroup): View {
        val inflater = LayoutInflater.from(viewGroup.context)
        return inflater.inflate(res_id, viewGroup, false)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_SIMPLE_STRING_ -> {
                val tmp9 = datalist[position] as SimpleString
                val rr = (viewHolder as SimpleTextViewHolder)
                rr.setText(tmp9.title, position)
                rr.text1.setOnClickListener { v: View ->
                    if (presenter != null) {
                        presenter!!.onItemClicked(v, tmp9)
                    }
                }
            }

            TYPE_INGREDIENT_ -> {
                val ingredientString = datalist[position] as IngredientString
                val h = (viewHolder as IngredientViewHolder)
                h.setIngredient(ingredientString, position)
                h.ll.setOnClickListener { v: View? ->
                    if (presenter != null) {
                        //presenter.c(ingredientString);
                    }
                }
                h.btn.setOnClickListener { v: View ->
                    if (presenter != null) {
                        presenter!!.onItemClicked(v, ingredientString)
                    }
                }
            }

            TYPE_VALUE_1_2 -> {
                val tmp99 = datalist[position] as NameValue1_2
                val holder = (viewHolder as Simple2TextViewHolder)
                holder.setText(tmp99, position)
                holder.text2.setOnClickListener { v: View ->
                    if (presenter != null) {
                        presenter!!.onItemClicked(v, tmp99)
                    }
                }
            }

            TYPE_VALUE_2_1 -> {
                val tmp9s9 = datalist[position] as NameValue2_1
                val holder1 = (viewHolder as Simple2TextViewHolder)
                holder1.setText(tmp9s9, position)
                holder1.text2.setOnClickListener { v: View ->
                    if (presenter != null) {
                        presenter!!.onItemClicked(v, tmp9s9)
                    }
                }
            }

            TYPE_HEADER -> {
                val tmp9z9: Any = datalist[position]
                (viewHolder as HeaderViewHolder).bind(tmp9z9 as HeaderObject)
            }

            TYPE_MPC_SHAPE -> {
                val o: Any = datalist[position]
                (viewHolder as MpcViewHolder).bind(o as MpcObj, position)
            }

            TYPE_RxCuiObjString -> {
                val kk: Any = datalist[position]
                (viewHolder as SimpleRxCuiViewHolder).bind(kk as RxCuiObjString, position)
            }

            EMPTY_VIEW_FROM_OBJECT -> {
                val emptyViewObj = datalist[position] as EmptyViewObj
                (viewHolder as EmptyViewHolder).bind(emptyViewObj, position)
            }

            EMPTY_VIEW_DEFAULT -> {
                val emptyView = viewHolder as EmptyViewHolder
                emptyView.bind(EmptyViewObj("No data yet", "You're doing good !"), position)
            }

            else -> {
                val vh = viewHolder as RecyclerViewSimpleTextViewHolder
                configureDefaultViewHolder(vh, datalist[position])
            }
        }
    }


    private fun configureDefaultViewHolder(vh: RecyclerViewSimpleTextViewHolder, o: Any?) {
        val text = if ((o == null)) "null" else o.toString()
        vh.label.text = text
    }


    /*
    private void configureViewHolder2(ViewHolder2 vh2, int positon) {
        //vh2.getImageView().setImageResource(R.drawable.sample_golden_gate);
        APIError error = (APIError) items.get(positon);
        if (error != null) {
            vh2.error_msg.setText(error.getErrorMsg());
        }
    }
*/
    //    private void configureBillingViewHolder(BillingViewHolder holder, int position) {
    //        Billing billing = (Billing) items.get(position);
    //        if (billing != null) {
    //            tvFirstName.setText(billing.getFirstName());
    //
    //            tvLastName.setText(billing.getLastName());
    //
    //            tvCompany.setText(billing.getCompany());
    //
    //            tvAddress1.setText(billing.getAddress1());
    //
    //            tvAddress2.setText(billing.getAddress2());
    //
    //            tvCity.setText(billing.getCity());
    //
    //            tvState.setText(billing.getState());
    //
    //            tvPostcode.setText(billing.getPostcode());
    //
    //            tvCountry.setText(billing.getCountry());
    //
    //            tvEmail.setText(billing.getEmail());
    //
    //            tvPhone.setText(billing.getPhone());
    //
    //            edit.setOnClickListener(v -> getPresenter().onItemClicked(v, billing));
    //        }
    //    }
    internal class RecyclerViewSimpleTextViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var label: TextView =
            itemView.findViewById(android.R.id.text1)
    }

    companion object {
        private const val TYPE_STRING_ = 123
        private const val TYPE_VALUE_1_2 = 124
        private const val TYPE_VALUE_2_1 = 125
        private const val TYPE_HEADER = 126

        private const val TYPE_MPC_SHAPE = 141
        private const val TYPE_SIMPLE_STRING_ = 454
        private const val TYPE_INGREDIENT_ = 455
    }
}