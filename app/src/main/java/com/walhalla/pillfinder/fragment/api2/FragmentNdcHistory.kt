package com.walhalla.pillfinder.fragment.api2

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
//import com.walhalla.lib.datamodel.rxnorm.response.RxNormHistoricalNDCsResponse
import gov.nih.nlm.rxnorm.models.NdcHistoryDepth
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.walhalla.lib.service.RxNormLib
import com.walhalla.pillfinder.MyApp
import com.walhalla.pillfinder.adapter.ComplexPresenter
import com.walhalla.pillfinder.adapter.ComplexRecyclerViewAdapter
import com.walhalla.pillfinder.adapter.obj.HeaderObject
import com.walhalla.pillfinder.adapter.obj.NdcLinkItem
import com.walhalla.pillfinder.adapter.obj.NameValue1_2
import com.walhalla.pillfinder.adapter.obj.VieModel
import com.walhalla.pillfinder.databinding.CategoryListFragmentBinding
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response

class FragmentNdcHistory : Fragment(), ComplexPresenter, OnRefreshListener {
    private var rxcui: String? = null
    private var index = 0
    private var mBinding: CategoryListFragmentBinding? = null
    private var mAdapter: ComplexRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = CategoryListFragmentBinding.inflate(inflater)
        if (mAdapter == null) {
            mAdapter = ComplexRecyclerViewAdapter(requireContext())
            mAdapter!!.setChildItemClickListener(this)
        }
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, 1)
        mBinding!!.recyclerView.layoutManager = layoutManager
        mBinding!!.recyclerView.itemAnimator = DefaultItemAnimator()
        mBinding!!.recyclerView.adapter = mAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            index = requireArguments().getInt(KEY_INDEX, 0)
            rxcui = requireArguments().getString(KEY_RXNORMID, null)
        }
    }

    fun loadData() {
        // was: MyApp.rxnorm.allhistoricalndcs(rxcui!!, null, 1) (Retrofit)
        lifecycleScope.launch {
            RxNormLib.fetch { getAllHistoricalNDCs(rxcui!!, NdcHistoryDepth._1) }.onSuccess { body ->
                val data = ArrayList<VieModel>()
                val times = body?.historicalNdcConcept?.historicalNdcTime
                if (!times.isNullOrEmpty()) {
                    data.add(HeaderObject("Historical NDC List"))
                    for (time in times) {
                        for (ndcTime in time.ndcTime.orEmpty()) {
                            for (ndc in ndcTime.ndc.orEmpty()) {
                                val label = "NDC: $ndc (${ndcTime.startDate} - ${ndcTime.endDate})"
                                // was: data.add(NameValue1_2(label, ndc)) - plain text, its text2 click was a no-op here
                                // historical NDCs may be obsolete -> ndcstatus=ALL
                                data.add(NdcLinkItem(label, ndc, allStatuses = true)) // NEW clickable chip -> pill card
                            }
                        }
                    }
                }
                val activity: Activity? = getActivity()
                if (activity != null && isAdded) {
                    updateData(data)
                }
            }.onFailure { t ->
                updateData(listOf(HeaderObject(t.message ?: t.localizedMessage ?: "Error")))
            }
        }
    }

//    old Retrofit loadData():
//    fun loadData() {
//        val call = MyApp.rxnorm.allhistoricalndcs(rxcui!!, null, 1)
//        call.enqueue(object : Callback<RxNormHistoricalNDCsResponse?> {
//            override fun onResponse(
//                call: Call<RxNormHistoricalNDCsResponse?>,
//                response: Response<RxNormHistoricalNDCsResponse?>
//            ) {
//                val body = response.body()
//                val data = ArrayList<VieModel>()
//                val times = body?.historicalNdcConcept?.historicalNdcTime
//                if (!times.isNullOrEmpty()) {
//                    data.add(HeaderObject("Historical NDC List"))
//                    for (time in times) {
//                        for (ndcTime in time.ndcTime) {
//                            for (ndc in ndcTime.ndc) {
//                                val label = "NDC: $ndc (${ndcTime.startDate} - ${ndcTime.endDate})"
//                                data.add(NameValue1_2(label, ndc))
//                            }
//                        }
//                    }
//                }
//                val activity: Activity? = getActivity()
//                if (activity != null && isAdded) {
//                    updateData(data)
//                }
//            }
//            override fun onFailure(call: Call<RxNormHistoricalNDCsResponse?>, t: Throwable) {
//                updateData(listOf(HeaderObject(t.message ?: t.localizedMessage ?: "Error")))
//            }
//        })
//    }

    fun updateData(data: List<VieModel>) {
        val obj: MutableList<VieModel> = ArrayList()
        obj.addAll(data)
        mAdapter!!.onRestoreInstanceState(obj)
    }

    override fun onItemClicked(v: View, position: Int) {}
    override fun onItemClicked(itemId: Int, category: VieModel) {}
    // was: override fun onItemClicked(v: View, obj: VieModel) {}
    override fun onItemClicked(v: View, obj: VieModel) {
        // NEW: NDC chip -> pill card (getNDCProperties, ndcstatus=ALL). Other items keep the old no-op behaviour.
        if (obj is NdcLinkItem && isAdded) {
            NdcPillCardDialog.show(childFragmentManager, obj.ndc, obj.allStatuses)
        }
    }

    override fun onResume() {
        super.onResume()
        mBinding!!.swiperefresh.setOnRefreshListener(this)
        loadData()
        if (mBinding!!.swiperefresh.isRefreshing) {
            mBinding!!.swiperefresh.isRefreshing = false
        }
    }

    override fun onRefresh() {
        if (mBinding!!.swiperefresh.isRefreshing) {
            mBinding!!.swiperefresh.isRefreshing = false
        }
    }

    companion object {
        const val KEY_INDEX: String = "key_index_ndc_history"
        const val KEY_RXNORMID: String = "key_rxnormId_ndc_history"
        @JvmStatic
        fun newInstance(index: Int, rxnormId: String?): FragmentNdcHistory {
            val bundle = Bundle()
            bundle.putInt(KEY_INDEX, index)
            bundle.putString(KEY_RXNORMID, rxnormId)
            val fragment = FragmentNdcHistory()
            fragment.arguments = bundle
            return fragment
        }
    }
} 