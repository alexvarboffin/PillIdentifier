package com.walhalla.pillfinder.fragment.api2

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.walhalla.pillfinder.adapter.obj.VieModel

class CategoryListFragment : SwipeFragment<VieModel?>() {
    protected var opCode: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            val tmp = requireArguments().getSerializable(KEY_TAB)
            if (tmp != null) {
                adapterSavedState = tmp as List<VieModel?>
            }
            opCode = requireArguments().getInt(KEY_INDEX, 0)
        }
    }


    override fun onResume() {
        super.onResume()
        makeGui()
    }

    override fun setData() {
    }

    override fun actionFilter() {
    }

    private fun makeGui() {
        val gson = GsonBuilder().setPrettyPrinting().create()


        //JsonObject body = adapterSavedState.get(0);
        when (opCode) {
            0 -> {}
            1 -> {}
            2 -> {}
            3 -> {}
            4 -> {}
            5 -> {}
            6 -> {}
            7 -> {}
            8 -> {}
            9 -> {}
            10 -> {}
            11 -> {}
        }

        val activity: Activity? = activity
        if (activity != null && isAdded) {
            updateData(adapterSavedState)
        }
    }

    override fun updateData(data: List<VieModel>) {
        super.updateData(data)
    }

    override fun onItemClicked(v: View, position: Int) {
    }

    override fun onItemClicked(v: View, obj: VieModel) {
    }

    override fun onItemClicked(itemId: Int, category: VieModel) {
    }


    companion object {
        const val KEY_INDEX: String = "key_index"
        const val KEY_TAB: String = "key_tab"

        fun newInstance(index: Int, list: ArrayList<JsonObject?>?): CategoryListFragment {
            val bundle = Bundle()
            bundle.putInt(KEY_INDEX, index)
            bundle.putSerializable(KEY_TAB, list)
            val fragment = CategoryListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
