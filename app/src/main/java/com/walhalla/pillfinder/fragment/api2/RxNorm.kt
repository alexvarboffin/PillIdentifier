package com.walhalla.pillfinder.fragment.api2

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import com.google.gson.JsonObject
import com.walhalla.Util
import com.walhalla.lib.RxnormRepository
import com.walhalla.lib.datamodel.common.response.Response0
import com.walhalla.lib.datamodel.common.response.Response1
import com.walhalla.lib.service.RxnormApi
import com.walhalla.lib.service.RxnormRepositoryCallback
import com.walhalla.pillfinder.MyApp
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.adapter.DynamicModifyViewPagerAdapter
import com.walhalla.pillfinder.fragment.api2.Fragment5.Companion.newInstance
import com.walhalla.pillfinder.fragment.main.BaseFragment
import com.walhalla.ui.DLog.d
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

import javax.net.ssl.SSLHandshakeException

class RxNorm : BaseFragment(), RxnormRepositoryCallback {
    private var repo: RxnormRepository? = null

    private var mPagerAdapter: DynamicModifyViewPagerAdapter? = null

    //private final ArrayList<JsonObject> data = new ArrayList<>();
    var data: MutableMap<String?, JsonObject?>? = HashMap<String?, JsonObject?>()
    var titles: MutableList<String?> = ArrayList<String?>()
    private var api: RxnormApi? = null
    private var handler0: Handler? = null

    private var query0: String? = null

    //rxcui or ingredient
    private var rxcui: String? = null
    private var ingredient: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rxnorm, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            rxcui = requireArguments().getString(KEY_RXNORMID, null)
            ingredient = requireArguments().getString(KEY_INGREDIENT, null)
        }

        api = MyApp.rxnorm
        handler0 = Handler(Looper.getMainLooper())

        //        for (int i1 = 0; i1 < 12; i1++) {
//            data.put("" + i1, new JsonObject());
//            titles.add("" + i1);
//        }
        if (!TextUtils.isEmpty(ingredient)) {
            val autoTextView =
                requireActivity().findViewById<AutoCompleteTextView?>(R.id.auto_text_view)
            if (autoTextView != null) {
                autoTextView.setText(ingredient)
                searchIngredient(ingredient)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = view.findViewById<View>(R.id.fab)

        if (!TextUtils.isEmpty(rxcui)) {
            fab.visibility = View.GONE
        } else {
            fab.setOnClickListener(View.OnClickListener { v: View? ->
                val t = requireActivity().findViewById<AutoCompleteTextView>(R.id.auto_text_view)
                val query = t.text.toString()
                searchIngredient(query)
            })
        }
        val tabLayout = requireActivity().findViewById<TabLayout?>(R.id.tabs)
        if (null != tabLayout) {
            tabLayout.visibility = View.VISIBLE
            tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    //DLog.d("" + tab.getText() + " ");
                    invalidateFragmentMenus(tab.position) //api v2
                    if (activity != null) {
                        Util.hideKeyboardFrom(
                            requireActivity(),  //getActivity().findViewById(R.id.et_user_input)
                            requireActivity().window.decorView
                        )
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }

        val viewPager = view.findViewById<ViewPager2>(R.id.viewpager)
        setupViewPager(viewPager)

        if (null != tabLayout) {
            TabLayoutMediator(
                tabLayout, viewPager,
                TabConfigurationStrategy { tab: TabLayout.Tab?, position: Int ->
                    tab!!.setText(
                        titles[position]
                    )
                }).attach()
            viewPager.setOffscreenPageLimit(
                if (tabLayout.tabCount > 0) tabLayout.tabCount else ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
            )
        }
    }

    private fun searchIngredient(query: String?) {
        this.query0 = query
        repo = RxnormRepository(api, this)
        if (!isValidQuery(query)) {
            mainView.mSnackbar("Please enter Query")
            return
        }
        //repo.globalRequest(query);
        mainView.showProgressBar()
        repo!!.globalRequest(query)
    }

    private fun isValidQuery(query: String?): Boolean {
        return query != null && query.length > 0
    }

    override fun successResponse(response: Response0) {
        if (mainView != null) {
            mainView.hideProgressBar()
        }

        if (response != null) {
            val idGroup = response.idGroup
            val rxnormId: List<String>? = idGroup?.rxnormId
            if (rxnormId.isNullOrEmpty()) {
                handler0!!.post(Runnable {
                    if (mainView != null) {
                        mainView.mSnackbar("There is no result for '$query0' (as String)")
                    }
                    mPagerAdapter!!.updateEmployeeListItems("There is no result for '$query0' (as String)")
                })
            } else {
                val pd = rxnormId[0]
                makeInformationGui(pd)
            }
        }
    }

    private fun makeInformationGui(pd: String?) {
        //DLog.d("@@@@@@@@@@" + pd);
        val buffer: MutableList<Fragment?> = ArrayList<Fragment?>()
        buffer.add(newInstance(1, pd, query0))
        buffer.add(FragmentRelated.newInstance(1, pd))
        buffer.add(FragmentNdc.newInstance(1, pd))
        buffer.add(FragmentNdcHistory.newInstance(1, pd))
        buffer.add(FragmentProperties.newInstance(1, pd))
        buffer.add(FragmentInteraction.newInstance(1, pd))
        buffer.add(Fragment7.newInstance(1, pd))
        buffer.add(Fragment2.newInstance(1, pd))

        titles.add("[INFO]") //titles.add("[" + pd + "]");
        titles.add("Related")
        titles.add("NDC")
        titles.add("NDC History")
        titles.add("Properties")
        titles.add("Interaction")
        titles.add("Status")
        titles.add("RxNorm Properties")
        mPagerAdapter!!.updateEmployeeListItems(buffer)
    }


    override fun successResponse(response1: Response1) {
    }

    override fun successResponse(opCode: Int, body: JsonObject) {
    }


    override fun handleThrowable(throwable: Throwable) {
        if (mainView != null) {
            mainView.hideProgressBar()
            //JsonSyntaxException
            //no_parameters_reply
            val err: String?
            if (throwable is ConnectException) {
                err = getString(R.string.err_connection)
            } else if (throwable is SSLHandshakeException) {
                err = throwable.localizedMessage
            } else if (throwable is UnknownHostException) {
                err = getString(R.string.err_connection)
            } else if (throwable is IOException) {
                d("@" + throwable.message)
                err = getString(R.string.err_connection)
            } else {
                err = throwable.localizedMessage //getString(R.string.err_refine_query);
            }
            mainView.mSnackbar(err)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_clear) {
            val textView = requireActivity().findViewById<AutoCompleteTextView>(R.id.auto_text_view)
            textView.setText("")
            titles.clear()
            mPagerAdapter!!.updateEmployeeListItems(ArrayList<Fragment?>())
            return true
        }
        //        else if (item.getItemId() == R.id.action_help) {
//            return true;
//        }
        return super.onOptionsItemSelected(item)
    }

    private fun invalidateFragmentMenus(position: Int) {
        for (i in 0..<mPagerAdapter!!.itemCount) {
            mPagerAdapter!!.getItem(i).setHasOptionsMenu(i == position)
        }
        if (activity != null) {
            requireActivity().invalidateOptionsMenu() //or respectively its support method.
        }
    }

    private fun setupViewPager(viewPager: ViewPager2) {
        mPagerAdapter = DynamicModifyViewPagerAdapter(this)
        viewPager.setAdapter(mPagerAdapter)
    }

    override fun onResume() {
        super.onResume()
        d("<resume>: $rxcui")

        val aa = activity as AppCompatActivity?
        if (aa != null) {
            aa.supportActionBar!!.setTitle(R.string.drugsearch)
            aa.supportActionBar!!.subtitle = null //DLog.getAppVersion(getContext())
            aa.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            aa.supportActionBar!!.setDisplayShowHomeEnabled(false)
        }

        if (!TextUtils.isEmpty(rxcui)) {
            makeInformationGui(rxcui)
        } else if ( /*!onSaveInstanceCalled*/data == null || data!!.isEmpty()) {
            d("LOAD NEW DATA")
            emptyView()
            loadData(0)
        } else {
            val activity: Activity? = getActivity()
            if (activity != null && isAdded) {
                updateData()
            } //restore data

            //mScreenCategoryListPresenter.setData(data);
        }
    }

    private fun loadData(i: Int) {
    }

    private fun emptyView() {
    }


    private fun updateData() {
        d("!!!" + data!!.size)

        //titles = new ArrayList<>();
        val buffer: MutableList<Fragment?> = ArrayList<Fragment?>()


        //        buffer.add(new CalculatorFragment());
//        titles.add(getString(R.string.tab_calculator));

//        for (Tab tab : data) {
//            titles.add("xxx"/*tab.getName()*/);
//        }


        //Fragment t1 = new Fragment();

        //this.titles.add(getString(R.string.tab_title_0));
        for (i in 0..0) {
            val aa = data!!["" + i]
            val t1 = CategoryListFragment.newInstance(
                1,
                ArrayList(listOf<JsonObject>(aa))
            )
            buffer.add(t1)
        }
        mPagerAdapter!!.updateEmployeeListItems(ArrayList<Fragment?>())
    }

    override fun successResponse(message: String) {
        d("@@@$message")
    }

    companion object {
        const val KEY_RXNORMID: String = "key_rx_norm_Id"
        const val KEY_INGREDIENT: String = "key_Ingredien"

        private const val NOT_ONE_CATEGORY = true

        @JvmStatic
        fun newInstance(rxnormId: String?): Fragment {
            val a: Fragment = RxNorm()
            val bundle = Bundle()
            bundle.putString(KEY_RXNORMID, rxnormId)
            a.setArguments(bundle)
            return a
        }


        //!!!! without KEY_RXNORMID
        fun newInstance(): Fragment {
            return RxNorm()
        }

        @JvmStatic
        fun newIngredientInstance(ingredient: String?): Fragment {
            val a: Fragment = RxNorm()
            val bundle = Bundle()
            bundle.putString(KEY_INGREDIENT, ingredient)
            a.setArguments(bundle)
            return a
        }
    }
}
