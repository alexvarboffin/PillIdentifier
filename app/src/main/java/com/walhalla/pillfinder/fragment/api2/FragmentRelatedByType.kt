package com.walhalla.pillfinder.fragment.api2

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
//import com.walhalla.lib.datamodel.rxnorm.response.RxNormRelatedResponse
//import com.walhalla.lib.datamodel.rxnorm.response.RxNormTermTypesResponse
//import com.walhalla.lib.datamodel.rxnorm.response.RxNormRelaTypesResponse
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

class FragmentRelatedByType : Fragment(), ComplexPresenter, OnRefreshListener {
    private var rxcui: String? = null
    private var index = 0
    private var mBinding: CategoryListFragmentBinding? = null
    private var mAdapter: ComplexRecyclerViewAdapter? = null

    private var typeSpinner: Spinner? = null
    private var valueSpinner: Spinner? = null
    private var currentType: String = "TTY"
    private var ttyList: List<String> = emptyList()
    private var relaList: List<String> = emptyList()

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
        // Добавляем два Spinner'а в header layout (или можно в xml, если потребуется)
        val root = mBinding!!.root as ViewGroup
        val header = inflater.inflate(R.layout.related_by_type_header, root, false)
        typeSpinner = header.findViewById(R.id.type_spinner)
        valueSpinner = header.findViewById(R.id.value_spinner)
        root.addView(header, 0)
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

    override fun onResume() {
        super.onResume()
        mBinding!!.swiperefresh.setOnRefreshListener(this)
        setupTypeSpinner()
        if (mBinding!!.swiperefresh.isRefreshing) {
            mBinding!!.swiperefresh.isRefreshing = false
        }
    }

    private fun setupTypeSpinner() {
        // Два варианта: TTY и RELA
        val types = listOf("TTY", "RELA")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpinner?.adapter = adapter
        typeSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                currentType = types[position]
                loadTypeValues()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        // По умолчанию TTY
        typeSpinner?.setSelection(0)
    }

    private fun loadTypeValues() {
        if (currentType == "TTY") {
            // Получаем список TTY (was: MyApp.rxnorm.getTermTypes(), Retrofit)
            lifecycleScope.launch {
                RxNormLib.fetch { getTermTypes() }.onSuccess { body ->
                    val list = body?.termTypeList?.termType ?: emptyList()
                    ttyList = list
                    setupValueSpinner(list)
                }.onFailure {
                    setupValueSpinner(emptyList())
                }
            }
        } else {
            // Получаем список RELA (was: MyApp.rxnorm.getRelaTypes(), Retrofit)
            // NB: relatypes.json returns relationTypeList.relationType; old DTO read relaTypeList.relaType (always empty)
            lifecycleScope.launch {
                RxNormLib.fetch { getRelaTypes() }.onSuccess { body ->
                    val list = body?.relationTypeList?.relationType ?: emptyList()
                    relaList = list
                    setupValueSpinner(list)
                }.onFailure {
                    setupValueSpinner(emptyList())
                }
            }
        }
    }

//    old Retrofit loadTypeValues():
//    private fun loadTypeValues() {
//        if (currentType == "TTY") {
//            // Получаем список TTY
//            MyApp.rxnorm.getTermTypes().enqueue(object : Callback<RxNormTermTypesResponse?> {
//                override fun onResponse(call: Call<RxNormTermTypesResponse?>, response: Response<RxNormTermTypesResponse?>) {
//                    val list = response.body()?.termTypeList?.termType ?: emptyList()
//                    ttyList = list
//                    setupValueSpinner(list)
//                }
//                override fun onFailure(call: Call<RxNormTermTypesResponse?>, t: Throwable) {
//                    setupValueSpinner(emptyList())
//                }
//            })
//        } else {
//            // Получаем список RELA
//            MyApp.rxnorm.getRelaTypes().enqueue(object : Callback<RxNormRelaTypesResponse?> {
//                override fun onResponse(call: Call<RxNormRelaTypesResponse?>, response: Response<RxNormRelaTypesResponse?>) {
//                    val list = response.body()?.relaTypeList?.relaType ?: emptyList()
//                    relaList = list
//                    setupValueSpinner(list)
//                }
//                override fun onFailure(call: Call<RxNormRelaTypesResponse?>, t: Throwable) {
//                    setupValueSpinner(emptyList())
//                }
//            })
//        }
//    }

    private fun setupValueSpinner(values: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, values)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        valueSpinner?.adapter = adapter
        valueSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (values.isNotEmpty()) {
                    loadData(values[position])
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun loadData(value: String) {
        if (rxcui.isNullOrBlank()) return
        val id = rxcui!!
        val byType = currentType == "TTY"
        // was: MyApp.rxnorm.getRelatedByType(rxcui, tty) / getRelatedByRelationship(rxcui, rela) (Retrofit)
        lifecycleScope.launch {
            RxNormLib.fetch {
                if (byType) getRelated(id, tty = listOf(value)) else getRelated(id, rela = listOf(value))
            }.onSuccess { body ->
                val data = ArrayList<VieModel>()
                if (body != null) {
                    val relatedGroup = body.relatedGroup
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
                updateData(listOf(HeaderObject(t.message ?: t.localizedMessage ?: "Error")))
            }
        }
    }

//    old Retrofit loadData(value):
//    private fun loadData(value: String) {
//        if (rxcui.isNullOrBlank()) return
//        val call = if (currentType == "TTY") {
//            MyApp.rxnorm.getRelatedByType(rxcui!!, value)
//        } else {
//            MyApp.rxnorm.getRelatedByRelationship(rxcui!!, value)
//        }
//        call.enqueue(object : Callback<RxNormRelatedResponse?> {
//            override fun onResponse(call: Call<RxNormRelatedResponse?>, response: Response<RxNormRelatedResponse?>) {
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
//            override fun onFailure(call: Call<RxNormRelatedResponse?>, t: Throwable) {
//                updateData(listOf(HeaderObject(t.message ?: t.localizedMessage ?: "Error")))
//            }
//        })
//    }

    /**
     * Rows with an rxcui become NEW clickable concept chips when hosted by the drug-search screen
     * (parent implements ConceptNavigator); otherwise (or without rxcui) the old plain NameValue1_2 row.
     */
    private fun conceptRow(name: String, id: String): VieModel =
        if (id.isNotBlank() && parentFragment is ConceptNavigator) ConceptLinkItem(name, id)
        else NameValue1_2(name, id)

    fun updateData(data: List<VieModel>) {
        val obj: MutableList<VieModel> = ArrayList()
        obj.addAll(data)
        mAdapter!!.onRestoreInstanceState(obj)
    }

    override fun onItemClicked(v: View, position: Int) {}
    override fun onItemClicked(itemId: Int, category: VieModel) {}
    //override fun onItemClicked(v: View, obj: VieModel) {} // was: empty, taps on related concepts did nothing
    override fun onItemClicked(v: View, obj: VieModel) {
        // Related by Type navigation: open the tapped concept in the drug-search tabs (shared back history)
        if (obj is ConceptLinkItem) {
            (parentFragment as? ConceptNavigator)?.openConcept(obj.rxcui, obj.conceptName)
        }
    }

    override fun onRefresh() {
        if (mBinding!!.swiperefresh.isRefreshing) {
            mBinding!!.swiperefresh.isRefreshing = false
        }
    }

    companion object {
        const val KEY_INDEX: String = "key_index_related_by_type"
        const val KEY_RXNORMID: String = "key_rxnormId_related_by_type"
        @JvmStatic
        fun newInstance(index: Int, rxnormId: String?): FragmentRelatedByType {
            val bundle = Bundle()
            bundle.putInt(KEY_INDEX, index)
            bundle.putString(KEY_RXNORMID, rxnormId)
            val fragment = FragmentRelatedByType()
            fragment.arguments = bundle
            return fragment
        }
    }
} 