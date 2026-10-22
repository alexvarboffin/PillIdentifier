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
import com.walhalla.lib.datamodel.common.response.Response5
import com.walhalla.pillfinder.MyApp.Companion.rxTerms
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.adapter.ComplexPresenter
import com.walhalla.pillfinder.adapter.ComplexRecyclerViewAdapter
import com.walhalla.pillfinder.adapter.emptyView.EmptyViewObj
import com.walhalla.pillfinder.adapter.obj.NameValue1_2
import com.walhalla.pillfinder.adapter.obj.NameValue2_1
import com.walhalla.pillfinder.adapter.obj.VieModel
import com.walhalla.pillfinder.databinding.CategoryListFragmentBinding
import com.walhalla.ui.DLog.d
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment5 : Fragment(), Callback<Response5>, ComplexPresenter, OnRefreshListener {
    protected var rxcui: String? = null
    private var index = 0

    private var mBinding: CategoryListFragmentBinding? = null


    private var mAdapter: ComplexRecyclerViewAdapter? = null
    private var query: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = CategoryListFragmentBinding.inflate(inflater)
        if (mAdapter == null) {
            mAdapter =
                ComplexRecyclerViewAdapter(requireContext() /*, presenter*/) //new AlbumsAdapter(getContext());//
            mAdapter!!.setChildItemClickListener(this)
        }
        return mBinding!!.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(getContext(), 1)
        mBinding!!.recyclerView.setLayoutManager(layoutManager)
        //mBinding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, Helpers.dpToPx(getContext(), 2), true));
        mBinding!!.recyclerView.setItemAnimator(DefaultItemAnimator())
        //        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBinding!!.recyclerView.setAdapter(mAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            index = requireArguments().getInt(KEY_INDEX, 0)
            rxcui = requireArguments().getString(KEY_RXNORMID, null)
            query = requireArguments().getString(KEY_QUERY, null)
        }
    }

    fun loadData() {
        val allinfo: Call<Response5> = rxTerms.getAllRxTermInfo(rxcui!! /*, RxnormRepository.RX_NAV_CALLER*/)
        allinfo.enqueue(this)
    }


    fun updateData(data: MutableList<VieModel?>) {
        val obj: MutableList<VieModel> = _root_ide_package_.java.util.ArrayList<VieModel>(data)
        mAdapter!!.onRestoreInstanceState(obj)
    }


    override fun onItemClicked(v: View, position: Int) {
    }

    override fun onItemClicked(v: View, obj: VieModel) {
    }

    override fun onItemClicked(itemId: Int, category: VieModel) {
    }


    override fun onResume() {
        super.onResume()
        d("resume")
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

    //*** Fragment not attached to a context
    override fun onResponse(call: Call<Response5?>, response: Response<Response5?>) {
        val body = response.body()
        val data = ArrayList<VieModel?>()
        val activity: Activity? = getActivity()
        if (activity != null && isAdded) {
            if (body != null) {
                val prop = body.rxtermsProperties
                if (prop != null) {
                    data.add(
                        NameValue1_2(
                            resources.getString(R.string.rx_5_brandName),
                            prop.brandName?:""
                        )
                    )
                    data.add(
                        NameValue1_2(
                            resources.getString(R.string.rx_5_displayName),
                            prop.displayName?:""
                        )
                    )
                    data.add(
                        NameValue1_2(
                            resources.getString(R.string.rx_5_synonym),
                            prop.synonym?:""
                        )
                    )
                    data.add(
                        NameValue2_1(
                            resources.getString(R.string.rx_5_fullName),
                            prop.fullName?:""
                        )
                    )
                    data.add(
                        NameValue1_2(
                            resources.getString(R.string.rx_5_fullGenericName),
                            prop.fullGenericName?:""
                        )
                    )
                    data.add(
                        NameValue1_2(
                            resources.getString(R.string.rx_5_strength),
                            prop.strength?:""
                        )
                    )
                    data.add(
                        NameValue1_2(
                            resources.getString(R.string.rx_5_rxtermsDoseForm),
                            prop.rxtermsDoseForm?:""
                        )
                    )
                    data.add(
                        NameValue1_2(
                            resources.getString(R.string.rx_5_route),
                            prop.route?:""
                        )
                    )
                    data.add(
                        NameValue1_2(
                            resources.getString(R.string.rx_5_termType),
                            prop.termType?:""
                        )
                    )
                    data.add(
                        NameValue1_2(
                            resources.getString(R.string.rx_5_rxcui),
                            prop.rxcui?:""
                        )
                    )
                    data.add(
                        NameValue1_2(
                            resources.getString(R.string.rx_5_genericRxcui),
                            prop.genericRxcui?:""
                        )
                    )
                    data.add(
                        NameValue2_1(
                            resources.getString(R.string.rx_5_rxnormDoseForm),
                            prop.rxnormDoseForm?:""
                        )
                    )
                    data.add(
                        NameValue1_2(
                            resources.getString(R.string.rx_5_suppress),
                            prop.suppress?:""
                        )
                    )
                    updateData(data)
                } else {
                    //Вернулся null данных нет
                    //DLog.d("<@@@>EEEEEEEEEEEEE" + body.toString());

                    data.add(
                        EmptyViewObj(
                            "No data found for given Rxcui ($rxcui)", query!!
                        )
                    )
                    updateData(data)
                }

                //Response{protocol=h2, code=200, message=, url=https://rxnav.nlm.nih.gov/REST/RxTerms/rxcui/317482/allinfo.json?caller=RxNav} █
                //DLog.d("<@@@>EEEEEEEEEEEEE" + prop.toString());
            } else {
                d("<@@@>EEEEEEEEEEEEE")
            }
        }
    }

    override fun onFailure(call: Call<Response5?>, t: Throwable) {
        val activity: Activity? = getActivity()
        if (activity != null && isAdded) {
            //DLog.d("<@@@>WWWWWWWWWWWWWWWWWWWWW");
            //Toast.makeText(getContext(), "@@@", Toast.LENGTH_SHORT).show();
        }
    }


    companion object {
        const val KEY_INDEX: String = "key_index_rxnormId"
        const val KEY_RXNORMID: String = "key_rxnormId"
        private const val KEY_QUERY = "key_query"

        @JvmStatic
        fun newInstance(index: Int, rxnormId: String?, query: String?): Fragment5 {
            val bundle = Bundle()
            bundle.putInt(KEY_INDEX, index)
            bundle.putString(KEY_RXNORMID, rxnormId)
            bundle.putString(KEY_QUERY, query)
            val fragment = Fragment5()
            fragment.setArguments(bundle)
            return fragment
        }
    }
}
