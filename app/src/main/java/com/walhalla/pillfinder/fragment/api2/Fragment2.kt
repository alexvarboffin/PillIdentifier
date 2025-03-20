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
import com.walhalla.lib.datamodel.pkg2.Response2
import com.walhalla.lib.datamodel.pkg4.Response4
import com.walhalla.lib.datamodel.pkg_base.PropConcept
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

class Fragment2 : Fragment(), Callback<Response2?>, ComplexPresenter,
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
    ): View? {
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
            index = arguments!!.getInt(KEY_INDEX, 0)
            opCode = arguments!!.getString(KEY_RXNORMID, null)
        }
    }

    fun loadData() {
        //Call<Response2> call03 = api.allrelatedextension(opCode, RxnormRepository.RX_NAV_CALLER);
        //call03.enqueue(this);


        //Attributes
        //Codes
        //Names
        //Sources
        //ALL


        val call4 = api!!.allProperties(opCode, RxnormRepository.RX_NAV_CALLER, "ALL")
        call4.enqueue(object : Callback<Response4?> {
            override fun onResponse(call: Call<Response4?>, response: Response<Response4?>) {
                val body = response.body()

                val fUll = ArrayList<VieModel>()
                val formater: MutableMap<String, MutableList<PropConcept>> = HashMap()

                if (body != null) {
                    val aa = body.propConceptGroup
                    if (null != aa) {
                        val mm = aa.propConcept

                        for (concept in mm) {
                            var raw = formater[concept.propCategory]
                            if (raw == null) {
                                raw = ArrayList()
                            }
                            raw.add(concept)
                            formater[concept.propCategory] = raw
                        }
                        for ((key, value) in formater) {
                            fUll.add(HeaderObject(key))
                            for (concept in value) {
                                fUll.add(NameValue2_1(concept.propName, concept.propValue))
                            }
                        }
                    }
                }
                val activity: Activity? = activity
                if (activity != null && isAdded) {
                    updateData(fUll)
                }
            }

            override fun onFailure(call: Call<Response4?>, t: Throwable) {
            }
        })
    }


    //    GENERAL_CARDINALITY	SINGLE
    //    PRESCRIBABLE	Y
    //    RXNAV_HUMAN_DRUG	US
    //    RXNAV_VET_DRUG	US
    //    TTY	IN
    //https://rxnav.nlm.nih.gov/REST/rxcui/1186300/allProperties?caller=RxNav&prop=Attributes
    override fun onResponse(call: Call<Response2?>, response: Response<Response2?>) {
        val response2 = response.body()
        val data = ArrayList<VieModel>()
        val activity: Activity? = activity
        if (activity != null && isAdded) {
            if (response2 != null) {
                val group = response2.allRelatedGroup
                val aa = group.conceptGroup
                //--> group.rxcui
                for (conceptGroup in aa) {
                    val prop = conceptGroup.conceptProperties

                    //String aaa = conceptGroup.tty;
                    for (property in prop) {
                        data.add(
                            NameValue2_1(
                                getString(R.string.general_cardinality),
                                property.genCard
                            )
                        )
                        data.add(NameValue2_1(getString(R.string.prescribable), property.pres))
                        data.add(
                            NameValue2_1(
                                getString(R.string.rxnav_human_drug),
                                property.humandrug
                            )
                        )
                        data.add(
                            NameValue2_1(
                                getString(R.string.rxnav_vet_drug),
                                property.inferedhuman
                            )
                        )
                        data.add(NameValue2_1(getString(R.string.tty0), property.tty))
                    }
                }
            }

            updateData(data)
        }
    }

    fun updateData(data: List<VieModel>) {
        val obj: MutableList<VieModel> = ArrayList()
        obj.addAll(data)
        mAdapter!!.onRestoreInstanceState(obj)
    }

    override fun onFailure(call: Call<Response2?>, t: Throwable) {
    }

    override fun onItemClicked(v: View, position: Int) {
    }


    override fun onItemClicked(itemId: Int, category: VieModel) {}

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
        fun newInstance(index: Int, rxnormId: String?): Fragment2 {
            val bundle = Bundle()
            bundle.putInt(KEY_INDEX, index)
            bundle.putString(KEY_RXNORMID, rxnormId)
            val fragment = Fragment2()
            fragment.arguments = bundle
            return fragment
        }
    }
}
