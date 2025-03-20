package com.walhalla.pillfinder.fragment.api2

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.walhalla.lib.datamodel.pkg6.Response6
import com.walhalla.lib.service.RxnormApi
import com.walhalla.pillfinder.MyApp
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.adapter.ComplexPresenter
import com.walhalla.pillfinder.adapter.ComplexRecyclerViewAdapter
import com.walhalla.pillfinder.adapter.obj.NameValue1_2
import com.walhalla.pillfinder.adapter.obj.NameValue2_1
import com.walhalla.pillfinder.adapter.obj.SimpleString
import com.walhalla.pillfinder.adapter.obj.VieModel
import com.walhalla.pillfinder.databinding.CategoryListFragmentBinding
import com.walhalla.ui.DLog.d
import com.walhalla.ui.plugins.Module_U.shareText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.TreeMap

class Fragment6 : Fragment(), Callback<Response6?>, ComplexPresenter,
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
        mBinding!!.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context, LinearLayoutManager.VERTICAL
            )
        )
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
        //Source: onchigh = EMPTY

        val response6Call = api!!.interaction(opCode, "DrugBank")
        response6Call.enqueue(this)

        //        Call<Response6> call6 = api.rxcui_allinfo(pd, RX_NAV_CALLER);
//        call6.enqueue(new RxnormRepository.Task111(5, callback, this));
    }

    override fun onResponse(call: Call<Response6?>, response: Response<Response6?>) {
        val body = response.body()
        val activity: Activity? = activity
        if (activity != null && isAdded) {
            if (body != null) {
                val data = ArrayList<VieModel>()
                val hash: MutableMap<String, String> = TreeMap()

                val group1 = body.interactionTypeGroup
                for (group in group1) {
                    val interactionTypes = group.interactionType
                    for (interactionType in interactionTypes) {
                        //                    MinConceptItem minConceptItem = interactionType.minConceptItem;
//                    String name0 = minConceptItem.name;
//                    DLog.d("[0]"+name0); //<-- Query string (ex. acetaminophen)

                        val interactionPairs = interactionType.interactionPair

                        val size = interactionPairs.size
                        d("$size interacting drugs for ...")

                        //minConceptItem.name
                        for (interactionPair in interactionPairs) {
                            val interactionConcept1 = interactionPair.interactionConcept


                            //                        Collections.sort(interactionConcept1, new Comparator<InteractionConcept>() {
//                            public int compare(InteractionConcept v1, InteractionConcept v2) {
//                                return v1.n.compareTo(v2.getEmail());
//                            }
//                        });
                            val max = interactionConcept1.size //size 2

                            //                        for (int i = 0; i < max; i++) {
//                            InteractionConcept concept = interactionConcept1.get(i);
//                            MinConceptItem minConceptItem1 = concept.minConceptItem;
//                            SourceConceptItem sourceConceptItem = concept.sourceConceptItem;
//                            //sourceConceptItem.name
//
//                            //DLog.d("[0] "+i+" " + max + "" + minConceptItem1.name+" "+sourceConceptItem.name);
//
//                            hash.put(minConceptItem1.name, interactionPair.severity + "\t" + interactionPair.description);
//                        }

//                        String name = "";
//

                            //Вобщем массив на 2, нужно записывать ячейку 1
                            if (max > 0) {
                                val interactionConcept = interactionConcept1[1]
                                val minConceptItem1 = interactionConcept.minConceptItem
                                //name = minConceptItem1.name;
                                hash[minConceptItem1.name] =
                                    interactionPair.severity + "\t" + interactionPair.description
                            }


                            //                        data.add(name + "\t" + interactionPair.severity + "\t" + interactionPair.description);
                        }
                    }
                }

                for ((key, value) in hash) {
                    //String aaa = entry.getValue();
                    //data.add(name + "\t" + interactionPair.severity + "\t" + interactionPair.description);
                    data.add(SimpleString(key + value))
                }


                //            Collections.sort(vehiclearray, new Comparator<Vehicle>() {
//                public int compare(Vehicle v1, Vehicle v2) {
//                    return v1.getEmail().compareTo(v2.getEmail());
//                }
//            });
                d("<@@@>EEEEEEEEEEEEE$response")
                updateData(data)
            }
        }
    }

    fun updateData(data: List<VieModel>) {
        val obj: MutableList<VieModel> = ArrayList()
        obj.addAll(data)
        mAdapter!!.onRestoreInstanceState(obj)
    }

    override fun onFailure(call: Call<Response6?>, t: Throwable) {
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
        fun newInstance(index: Int, rxnormId: String?): Fragment6 {
            val bundle = Bundle()
            bundle.putInt(KEY_INDEX, index)
            bundle.putString(KEY_RXNORMID, rxnormId)
            val fragment = Fragment6()
            fragment.arguments = bundle
            return fragment
        }
    }
}
