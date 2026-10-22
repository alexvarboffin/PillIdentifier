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
import com.walhalla.lib.RxnormRepository
import com.walhalla.lib.datamodel.common.response.DerivedConcepts
import com.walhalla.lib.datamodel.common.response.Response7
import com.walhalla.lib.service.RxnormApi
import com.walhalla.pillfinder.MyApp
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.adapter.ComplexPresenter
import com.walhalla.pillfinder.adapter.ComplexRecyclerViewAdapter
import com.walhalla.pillfinder.adapter.obj.HeaderObject
import com.walhalla.pillfinder.adapter.obj.NameValue1_2
import com.walhalla.pillfinder.adapter.obj.NameValue2_1
import com.walhalla.pillfinder.adapter.obj.SimpleString
import com.walhalla.pillfinder.adapter.obj.VieModel
import com.walhalla.pillfinder.databinding.CategoryListFragmentBinding
import com.walhalla.ui.plugins.Module_U.shareText

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.isNotEmpty
import kotlin.collections.joinToString

class Fragment7 : Fragment(), Callback<Response7>, ComplexPresenter,
    OnRefreshListener {
    protected var opCode: String = ""
    private var index = 0

    private var mBinding: CategoryListFragmentBinding? = null

    private var api: RxnormApi? = null
    private var mAdapter: ComplexRecyclerViewAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = CategoryListFragmentBinding.inflate(inflater)
        if (mAdapter == null) {
            mAdapter = ComplexRecyclerViewAdapter(requireContext())
            mAdapter!!.setChildItemClickListener(this)
        }
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(
            context, 1
        )
        mBinding!!.recyclerView.layoutManager = layoutManager
        //mBinding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, Helpers.dpToPx(getContext(), 2), true));
        mBinding!!.recyclerView.itemAnimator = DefaultItemAnimator()
        //        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBinding!!.recyclerView.adapter = mAdapter
        api = MyApp.rxnorm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            index = requireArguments().getInt(KEY_INDEX, 0)
            opCode = requireArguments().getString(KEY_RXNORMID, null)
        }
    }

    fun loadData() {
        val call8 = api!!.historystatus(opCode, RxnormRepository.RX_NAV_CALLER)
        call8.enqueue(this)
    }

    override fun onResponse(call: Call<Response7>, response: Response<Response7>) {
        val response7 = response.body()
        val activity: Activity? = activity
        if (activity != null && isAdded) {
            if (response7 != null && context != null) {
                val data = ArrayList<VieModel>()

                val history = response7.rxcuiStatusHistory
                val v1 = history?.attributes?.rxcui ?: ""
                val v2 = history?.attributes?.name ?: ""
                val v3 = history?.attributes?.tty ?: ""
                val v4 = history?.attributes?.isMultipleIngredient ?: ""
                val v5 = history?.attributes?.isBranded ?: ""

                data.add(HeaderObject(getString(R.string.header_properties)))
                insertIfNotNull(data, getString(R.string.rxcui), v1)
                insertIfNotNull(data, getString(R.string.name), v2)
                insertIfNotNull(data, getString(R.string.tty), v3)
                insertIfNotNull(data, getString(R.string.isMultipleIngredient), v4)
                insertIfNotNull(data, getString(R.string.isBranded), v5)

                data.add(HeaderObject(getString(R.string.header_definitional_features)))
                val features = history?.definitionalFeatures

                // ingredientAndStrength
                features?.ingredientAndStrength?.let { ingrList ->
                    for (ingr in ingrList) {
                        insertIfNotNull(data, "baseName", ingr.baseName)
                        insertIfNotNull(data, "activeIngredientName", ingr.activeIngredientName)
                        insertIfNotNull(data, "numeratorValue", ingr.numeratorValue)
                        insertIfNotNull(data, "numeratorUnit", ingr.numeratorUnit)
                        insertIfNotNull(data, "denominatorValue", ingr.denominatorValue)
                        insertIfNotNull(data, "denominatorUnit", ingr.denominatorUnit)
                    }
                }
                // doseFormConcept
                features?.doseFormConcept?.let { dfcList ->
                    for (concept in dfcList) {
                        insertIfNotNull(
                            data,
                            getString(R.string.doseFormName),
                            concept.doseFormName
                        )
                        insertIfNotNull(
                            data,
                            getString(R.string.doseFormRxcui),
                            concept.doseFormRxcui
                        )
                    }
                }
                // doseFormGroupConcept
                features?.doseFormGroupConcept?.let { dfgcList ->
                    for (group in dfgcList) {
                        insertIfNotNull(data, "doseFormGroupName", group.doseFormGroupName)
                        insertIfNotNull(data, "doseFormGroupRxcui", group.doseFormGroupRxcui)
                    }
                }
                // quantityFactor
                features?.quantityFactor?.let { qf ->
                    insertIfNotNull(data, "quantityFactorValue", qf.quantityFactorValue)
                    insertIfNotNull(data, "quantityFactorUnit", qf.quantityFactorUnit)
                }
                // qualitativeDistinction
                features?.qualitativeDistinction?.let { qd ->
                    insertIfNotNull(data, "qualitativeDistinction", qd)
                }

                data.add(HeaderObject(getString(R.string.header_pack_components)))
                val pack = history?.pack
                pack?.packAlias?.let { insertIfNotNull(data, "packAlias", it) }
                pack?.packConcept?.let { pcList ->
                    for (pc in pcList) {
                        insertIfNotNull(data, "packName", pc.packName)
                        insertIfNotNull(data, "packRxcui", pc.packRxcui)
                        insertIfNotNull(data, "packNumber", pc.packNumber)
                    }
                }

                data.add(HeaderObject(getString(R.string.header_metadata)))
                val meta = history?.metaData
                insertIfNotNull(data, "status", meta?.status)
                insertIfNotNull(data, "source", meta?.source)
                insertIfNotNull(data, "releaseStartDate", meta?.releaseStartDate)
                insertIfNotNull(data, "releaseEndDate", meta?.releaseEndDate)
                insertIfNotNull(data, "isCurrent", meta?.isCurrent)
                insertIfNotNull(data, "activeStartDate", meta?.activeStartDate)
                insertIfNotNull(data, "activeEndDate", meta?.activeEndDate)
                insertIfNotNull(data, "remappedDate", meta?.remappedDate)

                data.add(HeaderObject("Derived Concepts"))
                val derived = history?.derivedConcepts
                derived?.ingredientConcept?.let { icList ->
                    for (ic in icList) {
                        insertIfNotNull(data, "ingredientName", ic.ingredientName)
                        insertIfNotNull(data, "ingredientRxcui", ic.ingredientRxcui)
                    }
                }
                derived?.qdFreeConcept?.let { qdList ->
                    for (qd in qdList) {
                        insertIfNotNull(data, "qdFreeName", qd.qdFreeName)
                        insertIfNotNull(data, "qdFreeRxcui", qd.qdFreeRxcui)
                    }
                }
                derived?.quantifiedConcept?.let { qcList ->
                    for (qc in qcList) {
                        insertIfNotNull(data, "quantifiedName", qc.quantifiedName)
                        insertIfNotNull(data, "quantifiedRxcui", qc.quantifiedRxcui)
                        insertIfNotNull(data, "quantifiedTTY", qc.quantifiedTTY)
                        insertIfNotNull(data, "quantifiedActive", qc.quantifiedActive)
                    }
                }
                derived?.remappedConcept?.let { rcList ->
                    for (rc in rcList) {
                        insertIfNotNull(data, "remappedName", rc.remappedName)
                        insertIfNotNull(data, "remappedRxCui", rc.remappedRxCui)
                        insertIfNotNull(data, "remappedTTY", rc.remappedTTY)
                        insertIfNotNull(data, "remappedActive", rc.remappedActive)
                    }
                }
                derived?.scdConcept?.let { scdList ->
                    for (scd in scdList) {
                        insertIfNotNull(data, "scdConceptName", scd.scdConceptName)
                        insertIfNotNull(data, "scdConceptRxcui", scd.scdConceptRxcui)
                    }
                }

                updateData(data)
            }
        }
    }

    fun insertIfNotNull(data: ArrayList<VieModel>, title: String, value: String?) {
        if (!value.isNullOrBlank()) {
            data.add(NameValue1_2(title, value))
        }
    }

    fun updateData(data: List<VieModel>) {
        val obj: MutableList<VieModel> = ArrayList()
        obj.addAll(data)
        mAdapter!!.onRestoreInstanceState(obj)
    }

    override fun onFailure(call: Call<Response7>, t: Throwable) {
    }

    override fun onItemClicked(v: View, position: Int) {
    }

    override fun onItemClicked(v: View, obj: VieModel) {
        if (obj is SimpleString) {
            rrr(obj.title)
        } else if (obj is NameValue1_2) {
            rrr(obj.value)
        } else if (obj is NameValue2_1) {
            rrr(obj.value)
        } else {
            Toast.makeText(
                context,
                "@" + obj.javaClass.simpleName, Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun rrr(m: String) {
        shareText(requireActivity(), "" + m, getString(R.string.app_name))
    }

    override fun onItemClicked(itemId: Int, category: VieModel) {
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
        const val KEY_INDEX: String = "key_index_rxnormId"
        const val KEY_RXNORMID: String = "key_rxnormId"

        @JvmStatic
        fun newInstance(index: Int, rxnormId: String?): Fragment7 {
            val bundle = Bundle()
            bundle.putInt(KEY_INDEX, index)
            bundle.putString(KEY_RXNORMID, rxnormId)
            val fragment = Fragment7()
            fragment.arguments = bundle
            return fragment
        }
    }
}
