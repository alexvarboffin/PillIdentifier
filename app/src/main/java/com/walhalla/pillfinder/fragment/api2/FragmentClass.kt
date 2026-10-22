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
import com.walhalla.lib.datamodel.common.response.Response8
import com.walhalla.lib.datamodel.rxclass.response.RxClassResponse
import com.walhalla.pillfinder.MyApp
import com.walhalla.pillfinder.adapter.ComplexPresenter
import com.walhalla.pillfinder.adapter.ComplexRecyclerViewAdapter
import com.walhalla.pillfinder.adapter.obj.HeaderObject
import com.walhalla.pillfinder.adapter.obj.ClickableClassItem
import com.walhalla.pillfinder.adapter.obj.VieModel
import com.walhalla.pillfinder.adapter.obj.ClassCardItem
import com.walhalla.pillfinder.adapter.obj.ClassMemberItem
import com.walhalla.pillfinder.databinding.CategoryListFragmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import com.walhalla.pillfinder.activity.AnalogsActivity


class FragmentClass : Fragment(), ComplexPresenter, OnRefreshListener {
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

    override fun onResume() {
        super.onResume()
        mBinding!!.swiperefresh.setOnRefreshListener(this)
        loadData()
        if (mBinding!!.swiperefresh.isRefreshing) {
            mBinding!!.swiperefresh.isRefreshing = false
        }
    }

    private fun loadData() {
        if (rxcui.isNullOrBlank()) return
        MyApp.rxClass.getClassByRxNormDrugId(rxcui!!).enqueue(object : Callback<Response8?> {
            override fun onResponse(call: Call<Response8?>, response: Response<Response8?>) {
                val body = response.body()
                val data = ArrayList<VieModel>()
                val classGroup = body?.rxclassDrugInfoList?.rxclassDrugInfo ?: emptyList()
                if (classGroup.isNotEmpty()) {
                    data.add(HeaderObject("Классы препарата"))
                    // Группируем по classId/className/classType
                    val grouped = classGroup.groupBy {
                        val c = it.rxclassMinConceptItem
                        Triple(c?.classId ?: "", c?.className ?: "", c?.classType ?: "")
                    }
                    for ((key, members) in grouped) {
                        val (classId, className, classType) = key
                        val memberItems = members.map { info ->
                            ClassMemberItem(
                                minConceptName = info.minConcept?.name ?: "",
                                minConceptTty = info.minConcept?.tty ?: "",
                                rela = info.rela ?: "",
                                relaSource = info.relaSource ?: ""
                            )
                        }
                        data.add(ClassCardItem(classId, className, classType, memberItems))
                    }
                } else {
                    data.add(HeaderObject("Нет данных о классах"))
                }
                val activity: Activity? = getActivity()
                if (activity != null && isAdded) {
                    updateData(data)
                }
            }
            override fun onFailure(call: Call<Response8?>, t: Throwable) {
                updateData(listOf(HeaderObject(t.message ?: t.localizedMessage ?: "Error")))
            }
        })
    }

    fun updateData(data: List<VieModel>) {
        val obj: MutableList<VieModel> = ArrayList()
        obj.addAll(data)
        mAdapter!!.onRestoreInstanceState(obj)
    }

    override fun onItemClicked(v: View, position: Int) {}
    override fun onItemClicked(itemId: Int, category: VieModel) {}
    override fun onItemClicked(v: View, obj: VieModel) {
        when (obj) {
            is ClickableClassItem -> {
                // Старый вариант, если где-то остался
            }
            is ClassCardItem -> {
                // Клик по карточке класса — открыть новую активити с аналогами
                AnalogsActivity.start(requireContext(), obj.classId, obj.classType, obj.className)
            }
            is ClassMemberItem -> {
                if (v.id == com.walhalla.pillfinder.R.id.tvMemberRela) {
                    // Клик по типу связи
                    Toast.makeText(requireContext(), "Тип связи: ${obj.rela}", Toast.LENGTH_SHORT).show()
                    // TODO: здесь можно показать диалог-справку по rela
                } else {
                    // Клик по препарату — показать подробности
                    Toast.makeText(requireContext(), "Препарат: ${obj.minConceptName} [${obj.minConceptTty}]", Toast.LENGTH_SHORT).show()
                    // TODO: здесь можно открыть экран с подробностями по препарату
                }
            }
        }
    }
    override fun onRefresh() {
        if (mBinding!!.swiperefresh.isRefreshing) {
            mBinding!!.swiperefresh.isRefreshing = false
        }
    }

    companion object {
        const val KEY_INDEX: String = "key_index_class"
        const val KEY_RXNORMID: String = "key_rxnormId_class"
        @JvmStatic
        fun newInstance(index: Int, rxnormId: String?): FragmentClass {
            val bundle = Bundle()
            bundle.putInt(KEY_INDEX, index)
            bundle.putString(KEY_RXNORMID, rxnormId)
            val fragment = FragmentClass()
            fragment.arguments = bundle
            return fragment
        }
    }
} 