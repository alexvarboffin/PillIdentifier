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
import com.walhalla.lib.datamodel.pkg7.Response7
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

class Fragment7 : Fragment(), Callback<Response7?>, ComplexPresenter,
    OnRefreshListener {
    protected var opCode: String? = null
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
            mAdapter =
                ComplexRecyclerViewAdapter(requireContext() /*, presenter*/) //new AlbumsAdapter(getContext());//
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

    override fun onResponse(call: Call<Response7?>, response: Response<Response7?>) {
        val response7 = response.body()
        val activity: Activity? = activity
        if (activity != null && isAdded) {
            if (response7 != null && context != null) {
                val data = ArrayList<VieModel>()

                val history = response7.rxcuiStatusHistory
                val v1 = history.attributes.rxcui
                val v2 = history.attributes.name
                val v3 = history.attributes.tty
                val v4 = history.attributes.isMultipleIngredient
                val v5 = history.attributes.isBranded

                data.add(HeaderObject(getString(R.string.header_properties)))
                data.add(NameValue1_2(getString(R.string.rxcui), v1))
                data.add(NameValue1_2(getString(R.string.name), v2))
                data.add(NameValue1_2(getString(R.string.tty), v3))
                data.add(NameValue1_2(getString(R.string.isMultipleIngredient), v4))
                data.add(NameValue1_2(getString(R.string.isBranded), v5))

                data.add(HeaderObject(getString(R.string.header_definitional_features)))
                val features = history.definitionalFeatures

                if (features != null) {
                    val aaa = features.doseFormConcept
                    for (concept in aaa) {
                        data.add(
                            NameValue1_2(
                                getString(R.string.doseFormName),
                                concept.doseFormName
                            )
                        )
                        data.add(
                            NameValue1_2(
                                getString(R.string.doseFormRxcui),
                                concept.doseFormRxcui
                            )
                        )
                    }
                }

                data.add(HeaderObject(getString(R.string.header_pack_components)))
                val pack = history.pack //always empty

                data.add(HeaderObject(getString(R.string.header_metadata)))
                val metadata = history.metaData
                if (metadata != null) {
                    data.add(NameValue1_2(getString(R.string.status), metadata.status))
                    data.add(NameValue1_2(getString(R.string.source), metadata.source))
                    data.add(
                        NameValue1_2(
                            getString(R.string.activeStartDate),
                            metadata.activeStartDate
                        )
                    )
                    data.add(
                        NameValue1_2(
                            getString(R.string.activeEndDate),
                            metadata.activeEndDate
                        )
                    )
                    data.add(NameValue1_2(getString(R.string.isCurrent), metadata.isCurrent))
                    data.add(
                        NameValue1_2(
                            getString(R.string.releaseStartDate),
                            metadata.releaseStartDate
                        )
                    )
                    data.add(
                        NameValue1_2(
                            getString(R.string.releaseEndDate),
                            metadata.releaseEndDate
                        )
                    )
                }
                data.add(HeaderObject(getString(R.string.header_derived_concepts)))
                val concepts = history.derivedConcepts
                if (concepts != null) {
                    val aaa = concepts.ingredientConcept
                    val _w = getString(R.string.ingredient)
                    for (concept in aaa) {
                        data.add(NameValue1_2(_w, concept.ingredientName)) //@@@
                    }
                }


                //    04-2005  (opCode + ""
//                    (response7.rxcuiStatusHistory.attributes.);
                updateData(data)
            }
        }
    }

    fun updateData(data: List<VieModel>) {
        val obj: MutableList<VieModel> = ArrayList()
        obj.addAll(data)
        mAdapter!!.onRestoreInstanceState(obj)
    }

    override fun onFailure(call: Call<Response7?>, t: Throwable) {
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
