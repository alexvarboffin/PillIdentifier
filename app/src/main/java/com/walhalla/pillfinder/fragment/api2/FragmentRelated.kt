package com.walhalla.pillfinder.fragment.api2

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
//import com.walhalla.lib.datamodel.rxnorm.response.RxNormAllRelatedResponse
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.walhalla.lib.service.RxNormLib
import com.walhalla.pillfinder.MyApp
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.adapter.ComplexPresenter
import com.walhalla.pillfinder.adapter.ComplexRecyclerViewAdapter
import com.walhalla.pillfinder.adapter.obj.ConceptLinkItem
import com.walhalla.pillfinder.adapter.obj.HeaderObject
import com.walhalla.pillfinder.adapter.obj.NameValue1_2
import com.walhalla.pillfinder.adapter.obj.VieModel
import com.walhalla.pillfinder.databinding.CategoryListFragmentBinding
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response

class FragmentRelated : Fragment(), ComplexPresenter, OnRefreshListener {
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

    /**
     * Roadmap #5. PRIMARY call = getAllRelatedExtension: live-checked (23.10.2026) to return exactly the same
     * groups / rows as getAllRelatedInfo (198201: 15/15, 141928: 15/15, 202363: 40/40) plus per-concept
     * pres (prescribable), psn (prescribable name), vetdrug/humandrug, rxnavstr, genCard, minConceptGroup -
     * so one request gives both the rows and the extras.
     * FALLBACK = getAllRelatedInfo (the previous implementation, [loadDataRelatedInfo]) when the extension call
     * throws (offline / timeout / parse error) or returns no body / no allRelatedGroup (non-2xx), so the tab never goes empty.
     */
    fun loadData() {
        val id = rxcui ?: return
        // roadmap #12: "Prescribable only" switch ON -> /Prescribe/rxcui/{id}/allrelated.json; OFF -> unchanged below
        if (PrescribeMode.isEnabled(requireContext())) {
            loadDataPrescribe(id)
            return
        }
        lifecycleScope.launch {
            val ext = RxNormLib.fetch { getAllRelatedExtension(id) }
            val groups = ext.getOrNull()?.allRelatedGroup?.conceptGroup
            if (groups == null) {
                println("getAllRelatedExtension($id) unavailable: " +
                        (ext.exceptionOrNull()?.message ?: "empty body") + " -> fallback getAllRelatedInfo")
                loadDataRelatedInfo()
                return@launch
            }
            val data = ArrayList<VieModel>()
            for (group in groups) {
                val props = group.conceptProperties
                if (props.isNullOrEmpty()) continue
                val tty = group.tty
                if (!tty.isNullOrBlank()) {
                    data.add(HeaderObject(tty))
                }
                for (prop in props) {
                    data.add(
                        conceptRow(
                            prop.name ?: "", prop.rxcui ?: "",
                            psn = prop.psn,
                            prescribable = prop.pres == "Y",
                            veterinary = prop.vetdrug == "US"
                        )
                    )
                }
            }
            if (getActivity() != null && isAdded) {
                updateData(data)
            }
        }
    }

    /**
     * Roadmap #12 (Prescribe mode): prescribeGetAllRelatedInfo. Same rows / navigation as the standard tab, but only
     * concepts of the RxNorm Current Prescribable Content. Live 23.10.2026: 198201 15 -> 15, 141928 15 -> 15,
     * 202363 40 -> 40 (all prescribable), rifampin IN 9384 55 -> 27 (SCD 10 -> 3), Adequan 1085959 12 -> 0.
     * Prescribe has no allrelatedextension twin, so there are no pres / psn / vet extras (every row is prescribable anyway).
     */
    private fun loadDataPrescribe(id: String) {
        lifecycleScope.launch {
            RxNormLib.fetch { prescribeGetAllRelatedInfo(id) }.onSuccess { body ->
                val data = ArrayList<VieModel>()
                data.add(HeaderObject(getString(R.string.prescribe_mode_note)))
                var rows = 0
                for (group in body?.allRelatedGroup?.conceptGroup.orEmpty()) {
                    val props = group.conceptProperties
                    if (props.isNullOrEmpty()) continue
                    val tty = group.tty
                    if (!tty.isNullOrBlank()) data.add(HeaderObject(tty))
                    for (prop in props) {
                        data.add(conceptRow(prop.name ?: "", prop.rxcui ?: ""))
                        rows++
                    }
                }
                if (rows == 0) data.add(HeaderObject(getString(R.string.prescribe_mode_empty)))
                if (getActivity() != null && isAdded) updateData(data)
            }.onFailure { t ->
                if (getActivity() != null && isAdded) updateData(listOf(HeaderObject(t.message ?: t.localizedMessage ?: "")))
            }
        }
    }

    // previous primary loader (getAllRelatedInfo), now the FALLBACK for roadmap #5:
    //fun loadData() {
    fun loadDataRelatedInfo() {
        // was: MyApp.rxnorm.getAllRelatedInfo(rxcui!!) (Retrofit)
        lifecycleScope.launch {
            RxNormLib.fetch { getAllRelatedInfo(rxcui!!) }.onSuccess { body ->
                val data = ArrayList<VieModel>()
                if (body != null) {
                    val relatedGroup = body.allRelatedGroup
                    if (relatedGroup != null) {
                        for (group in relatedGroup.conceptGroup.orEmpty()) {
                            val tty = group.tty
                            if (!group.conceptProperties.isNullOrEmpty()) {
                                if (!tty.isNullOrBlank()) {
                                    data.add(HeaderObject(tty))
                                }
                                for (prop in group.conceptProperties.orEmpty()) {
                                    //data.add(NameValue1_2(prop.name ?: "", prop.rxcui ?: ""))
                                    data.add(conceptRow(prop.name ?: "", prop.rxcui ?: ""))
                                }
                            }
                        }
                    }
                }
                val activity: Activity? = getActivity()
                if (activity != null && isAdded) {
                    updateData(data)
                }
            }.onFailure { t ->
                println(t.message ?: t.localizedMessage ?: "")
                updateData(listOf(HeaderObject(t.message ?: t.localizedMessage ?: "")))
            }
        }
    }

//    old Retrofit loadData():
//    fun loadData() {
//        val call = MyApp.rxnorm.getAllRelatedInfo(rxcui!!)
//        call.enqueue(object : Callback<RxNormAllRelatedResponse?> {
//            override fun onResponse(
//                call: Call<RxNormAllRelatedResponse?>,
//                response: Response<RxNormAllRelatedResponse?>
//            ) {
//                val body = response.body()
//                val data = ArrayList<VieModel>()
//                if (body != null) {
//                    val relatedGroup = body.relatedGroup
//                    if (relatedGroup != null) {
//                        for (group in relatedGroup.conceptGroup) {
//                            val tty = group.tty
//                            if (!group.conceptProperties.isNullOrEmpty()) {
//                                if (!tty.isNullOrBlank()) {
//                                    data.add(HeaderObject(tty))
//                                }
//                                for (prop in group.conceptProperties) {
//                                    data.add(NameValue1_2(prop.name ?: "", prop.rxcui ?: ""))
//                                }
//                            }
//                        }
//                    }
//                }
//                val activity: Activity? = getActivity()
//                if (activity != null && isAdded) {
//                    updateData(data)
//                }
//            }

//            override fun onFailure(call: Call<RxNormAllRelatedResponse?>, t: Throwable) {
//                println(t.message ?: t.localizedMessage ?: "")
//                updateData(listOf(HeaderObject(t.message ?: t.localizedMessage ?: "")))
//            }
//        })
//    }

    /**
     * Rows with an rxcui become NEW clickable concept chips when hosted by the drug-search screen
     * (parent implements ConceptNavigator); otherwise (or without rxcui) the old plain NameValue1_2 row.
     */
    //private fun conceptRow(name: String, id: String): VieModel =
    //    if (id.isNotBlank() && parentFragment is ConceptNavigator) ConceptLinkItem(name, id)
    //    else NameValue1_2(name, id)
    private fun conceptRow(
        name: String,
        id: String,
        psn: String? = null,
        prescribable: Boolean = false,
        veterinary: Boolean = false
    ): VieModel =
        if (id.isNotBlank() && parentFragment is ConceptNavigator) ConceptLinkItem(name, id, psn, prescribable, veterinary)
        else NameValue1_2(name, id)

    fun updateData(data: List<VieModel>) {
        val obj: MutableList<VieModel> = ArrayList()
        obj.addAll(data)
        mAdapter!!.onRestoreInstanceState(obj)
    }

    override fun onItemClicked(v: View, position: Int) {}
    override fun onItemClicked(itemId: Int, category: VieModel) {}
//    was (empty handler, taps on related concepts did nothing):
//    override fun onItemClicked(v: View, obj: VieModel) {
//        // Можно добавить обработку клика
//    }
    override fun onItemClicked(v: View, obj: VieModel) {
        // Related tab navigation: open the tapped concept in the drug-search tabs (shared back history)
        if (obj is ConceptLinkItem) {
            (parentFragment as? ConceptNavigator)?.openConcept(obj.rxcui, obj.conceptName)
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
        const val KEY_INDEX: String = "key_index_related"
        const val KEY_RXNORMID: String = "key_rxnormId_related"

        @JvmStatic
        fun newInstance(index: Int, rxnormId: String?): FragmentRelated {
            val bundle = Bundle()
            bundle.putInt(KEY_INDEX, index)
            bundle.putString(KEY_RXNORMID, rxnormId)
            val fragment = FragmentRelated()
            fragment.arguments = bundle
            return fragment
        }
    }
} 