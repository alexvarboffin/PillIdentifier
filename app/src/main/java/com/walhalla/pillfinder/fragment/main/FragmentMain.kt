package com.walhalla.pillfinder.fragment.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.firebase.firestore.MainPresenterNew
import com.google.gson.Gson
import com.walhalla.domen.PillRequest
import com.walhalla.domen.rest.QueryConstants.DEFAULT_ANY
import com.walhalla.factory.DialogFactory
import com.walhalla.factory.DialogType
import com.walhalla.pillfinder.BuildConfig
import com.walhalla.pillfinder.MpcField
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.activity.IPresenter
import com.walhalla.pillfinder.activity.IPresenter.PresenterCallback
import com.walhalla.pillfinder.activity.MainActivity
import com.walhalla.pillfinder.databinding.NlmrximageListBinding
import com.walhalla.pillfinder.dialog.D_Color
import com.walhalla.pillfinder.dialog.D_Scoring
import com.walhalla.pillfinder.dialog.D_Shape
import com.walhalla.pillfinder.dialog.D_Size
import com.walhalla.pillfinder.dialog.inflate.D_Imprint
import com.walhalla.pillfinder.ui.adapter.PaginationAdapter
import com.walhalla.pillfinder.ui.adapter.PaginationAdapter.ChildItemClickListener
import com.walhalla.pillfinder.ui.adapter.scroll.PaginationScrollListener
import com.walhalla.pillfinder.ui.helper.NavigatorView
import com.walhalla.pillfinder.ui.helper.NotificationCallback
import com.walhalla.ui.DLog.d
import com.walhalla.ui.DLog.getAppVersion
import com.walhalla.ui.DLog.handleException
import com.walhalla.ui.DLog.nonNull
import gov.nih.nlm.Colors
import gov.nih.nlm.model.APIError
import gov.nih.nlm.model.NlmRxImage
import java.util.Locale


class FragmentMain : BaseFragment(), PresenterCallback {
    private var places: HashMap<String, String>? = HashMap()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_FILTER, places)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (savedInstanceState != null) {
            places = savedInstanceState.getSerializable(KEY_FILTER) as HashMap<String, String>?
        }
        if (arguments != null) {
            places = requireArguments().getSerializable(KEY_FILTER) as HashMap<String, String>?
        }


        presenter = MainPresenterNew(this)

        //Recycler
        numberOfColumns = resources.getInteger(R.integer.column_count)


        if (mViewAdapter == null) {
            mViewAdapter = PaginationAdapter(requireContext())
        }

        if (savedInstanceState != null) {
            d("Restore result")
            return
        }
        d("ON_CREATE")
    }


    @SuppressLint("NonConstantResourceId")
    private val tabListener = View.OnClickListener { v: View ->
        val fm =
            parentFragmentManager //getFragmentManager
        var oldFragment: Fragment? = null
        if (fm != null) {
            oldFragment = fm.findFragmentByTag(KEY_CURRENT_DIALOG)
        }

        if (oldFragment != null) {
            fm.beginTransaction().remove(oldFragment).commit()
        }

        var rr: DialogType? = null

        val id = v.id
        if (id == R.id.btn_color) {
            rr = DialogType.COLOR
        } else if (id == R.id.btn_shape) {
            rr = DialogType.SHAPE
        } else if (id == R.id.btn_scoring) {
            rr = DialogType.SCORE
        } else if (id == R.id.btn_size) {
            rr = DialogType.SIZE
        } else if (id == R.id.btn_imprint) {
            rr = DialogType.IMPRINT
        }
        var try_attach_this: DialogFragment? = null
        if (rr != null) {
            try_attach_this = DialogFactory.getDialog(this, rr)
        }
        if (try_attach_this != null) {
            val dialog = try_attach_this.dialog
            if (dialog != null && dialog.isShowing) {
                return@OnClickListener
            }

            if (!try_attach_this.isAdded) {
                if (fm != null) {
                    try_attach_this.show(fm, KEY_CURRENT_DIALOG)
                }
            }
        }
    }
    private var mOptionsMenu: Menu? = null
    private var presenter: IPresenter? = null

    fun fffff7(fragment: Fragment) {
        val a = fragment.activity ?: return
        val view1 = a.findViewById<View>(R.id.top_menu)
        if (view1 != null) {
            view1.visibility = View.VISIBLE
            a.findViewById<View>(R.id.btn_imprint).setOnClickListener(tabListener)
            a.findViewById<View>(R.id.btn_size).setOnClickListener(tabListener)
            a.findViewById<View>(R.id.btn_color).setOnClickListener(tabListener)
            a.findViewById<View>(R.id.btn_shape).setOnClickListener(tabListener)
            a.findViewById<View>(R.id.btn_scoring).setOnClickListener(tabListener)

            //            a.findViewById(R.id.btn_search).setOnClickListener(cl);
//                ((androidx.appcompat.widget.Toolbar) a.findViewById(R.id.toolbar))
//                        .setNavigationIcon(R.mipmap.ic_launcher_round);
            (a.findViewById<View>(R.id.toolbar) as Toolbar).navigationIcon =
                null
        }
    }

    override fun handleThrowable(throwable: Throwable) {
        if (callback != null) {
            callback!!.handleThrowable(throwable)
        }
        setState(State.EXCEPTION)
    }

    override fun hideProgressBar() {
        if (mainView != null) {
            mainView.hideProgressBar()
        }
    }

    override fun showProgressBar() {
        val activity = activity
        if (activity != null && activity is MainActivity) {
            activity.showProgressBar()
        }
    }

    override fun hideRefreshLayoutProgress() {
        mBinding!!.swipeRefreshLayout.isRefreshing = false
    }

    fun replyStatus(var0: SpannableStringBuilder) {
        if (callback != null) {
            callback!!.replyStatus(var0)
        }
    }

    fun mSnackbar(var0: Int) {
        if (callback != null) {
            callback!!.mSnackbar(var0)
        }
    }

    enum class State {
        RESET,
        INIT,
        RESTORE,
        EXCEPTION
    }

    //        //mMap.put(FIELD_COLOR, null);
    //        mMap.put(FIELD_SHAPE, null);
    //        mMap.put("sizeT", null);
    //        mMap.put(FIELD_SCORE, null);
    //        mMap.put("symbol", null);
    //        mMap.put(FIELD_SIZE, "0");
    //        mMap.put(RequestMap.FIELD_IMPRINT, null);
    //        mMap.put("imprintColor", null);
    //        mMap.put("ndc", null);
    //        mMap.put("matchPackSize", null);
    //        mMap.put("matchRelabeled", null);
    //        mMap.put("rxcui", null);
    //        mMap.put("id", null);
    //        mMap.put("setId", null);
    //        mMap.put("rootId", null);
    //        mMap.put("name", null);
    //        mMap.put("inactive", null);
    //        mMap.put("parse", null);
    //        mMap.put(FIELD_PAGE, "0");
    //private List<Object> objectList = new ArrayList<>();
    //""""""private static final int PAGE_START = -9999;
    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    var isLoading: Boolean = false

    // If current page is the last page (Pagination will stop after this page load)
    var isLastPage: Boolean = false

    //private String mTitle;
    private var mVersion: String? = null

    //@Recycler
    var mViewAdapter: PaginationAdapter? = null
    private var layoutManager: GridLayoutManager? = null
    private var numberOfColumns = 0

    private val sizeLookup: GridLayoutManager.SpanSizeLookup =
        object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                //define span size for this position
                //for example, if you have 2 column per row, you can implement something like that:
//                                if(position == youRule) {
//                                    return 2; //item will take 2 column (full row size)
//                                } else {
//                                    return 1; //you will have 2 rolumn per row
//                                }

                val viewType = mViewAdapter!!.getItemViewType(position)
                //DLog.d("viewType->" + viewType + " pos-->" + position + " " + numberOfColumns);
                if (viewType == PaginationAdapter.TYPE_INFORMATION) {
                    return numberOfColumns
                } else if (viewType == PaginationAdapter.TYPE_NLMRXIMAGES) {
                    return 1
                } else if (viewType == PaginationAdapter.VIEW_TYPE_LOADING) {
                    return numberOfColumns //Full width
                }
                return numberOfColumns
            }
        }

    private var callback: FragmentCallback? = null
    var mBinding: NlmrximageListBinding? = null

    private val onnRefresh = OnRefreshListener { //??? CURRENT_PAGE_NUMBER = PAGE_START;
        mBinding!!.pageNavigation.CURRENT_PAGE_NUMBER = NavigatorView.PAGE_START_INDEX
        isLastPage = false
        //mViewAdapter.clear();
        //loadNextPage(CURRENT_PAGE_NUMBER);
        presenter!!.loadNextPageRequest(
            mBinding!!.pageNavigation.CURRENT_PAGE_NUMBER,
            IPresenter.QEvent.REPLACE
        )
    }

    /*
    Recycler
     */
    private val setChildItemClickListener = object : ChildItemClickListener {

        override fun onClick(o: NlmRxImage) {
            //        if (position < objectList.size()) {
            //            Object obj = objectList.get(position);
            //
            //            if (obj instanceof NlmRxImage) {
            if (callback != null) {
                callback!!.showMoreInfo(Gson().toJson(o))
            }


            //            }
            //        }
        }
    }
    private val SB_D = "x"


    private var tmp = -1
    private var customAlphabet: Array<String?>? = null

    fun mNavigationViewFix(totalPages: Int, current: Int) {
        if (totalPages != tmp || customAlphabet == null) {
            val format = formatter(totalPages)

            customAlphabet = arrayOfNulls(totalPages)
            for (i in 0 until totalPages) {
                val newIndex = i + 1
                customAlphabet!![i] = String.format(Locale.getDefault(), format, newIndex)
            }
            //mBinding.alphSectionIndex.setVisibility(View.GONE);
            mBinding!!.alphSectionIndex.setAlphabet(customAlphabet)
            mBinding!!.alphSectionIndex.onSectionIndexClickListener { view1: View?, position: Int, character: String ->
                //page = position
                //title = character
                try {
                    val newIndex = position + 1
                    mBinding!!.pageNavigation.CURRENT_PAGE_NUMBER = newIndex
                    presenter!!.loadNextPageRequest(newIndex, IPresenter.QEvent.ADD)
                } catch (e: Exception) {
                    d("onViewCreated: " + e.localizedMessage)
                }
                if (BuildConfig.DEBUG) {
                    val sp = SpannableStringBuilder("@$position $character")
                    sp.setSpan(
                        BackgroundColorSpan(Color.RED),
                        0,
                        sp.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    sp.setSpan(
                        ForegroundColorSpan(Color.WHITE),
                        0,
                        sp.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    sp.setSpan(UnderlineSpan(), 0, sp.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                    val tst = Toast.makeText(context, sp, Toast.LENGTH_SHORT)
                    if (tst.view != null) {
                        tst.view!!.background = resources.getDrawable(R.drawable.pretty_gradient)
                        tst.view!!.setPadding(50, 50, 50, 50)
                    }
                    tst.show()
                }
            }
            tmp = totalPages
        }
        val format = formatter(totalPages)
        val mmm = String.format(Locale.getDefault(), format, current)
        mBinding!!.alphSectionIndex.setLetterToBold(mmm)
    }


    private fun formatter(totalPages: Int): String {
        return (if ((totalPages > 100)) "%1$03d" else (if ((totalPages > 10)) "%1$02d" else "%1\$d")
                )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (mBinding == null) {
            mBinding = NlmrximageListBinding.inflate(inflater, container, false)
            d("ON_CREATE_VIEW")
            mVersion = getAppVersion(requireActivity())
            fffff()
        }
        return mBinding!!.root
    }


    private fun fffff() {
        //setHasOptionsMenu(true);


//        mRecyclerview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//                private void enableDisableSwipeRefresh(boolean enable) {
//                    if (mSwipeRefreshLayout != null) {
//                        mSwipeRefreshLayout.setEnabled(enable);
//                    }
//                }
//            }
//        });


        setRecyclerView(mBinding!!.recyclerView)

        //Set recycleView
        mBinding!!.pageNavigation.setOnClickListener(cl)
        mViewAdapter!!.setChildItemClickListener(setChildItemClickListener)
        //        if (!Utils.isConnected(getContext())) {
//            callback.mSnackbar(R.string.err_connection);
//            homeScreenView();
//        } else {
//            //loadNextPage(navigator.getCURRENT_PAGE_NUMBER());
        mBinding!!.swipeRefreshLayout.setOnRefreshListener(onnRefresh)


        //        }
        if (places != null && !places!!.isEmpty()) {
            setState(State.RESTORE)
        } else {
            setState(State.INIT)
        }
    }

    fun setState(state: State) {
        //DLog.d("@@@@@@@@@@@@@@ SET_STATE: " + state.name());
        when (state) {
            State.INIT -> {
                PillRequest.INSTANCE.mapInit()
                this.homeScreenView()
                if (callback != null) {
                    callback!!.replyStatus(SpannableString(""))
                    callback!!.setMainTitle(
                        getString(R.string.app_name),
                        SpannableStringBuilder(mVersion)
                    )
                }
            }

            State.RESTORE -> {
                PillRequest.INSTANCE.mapInit()
                PillRequest.INSTANCE.put(places!!)
                updateStatus()
                newRequest()
            }

            State.RESET -> {
                PillRequest.INSTANCE.reset()
                this.homeScreenView()
                this.updateStatus()
                if (callback != null) {
                    callback!!.replyStatus(SpannableString(""))
                    //mTitle = mVersion;//"(x-x-x-x-x)";
                    callback!!.setMainTitle(
                        getString(R.string.app_name),
                        SpannableStringBuilder(mVersion)
                    )
                }
                mBinding!!.alphSectionIndex.visibility = View.GONE
            }

            State.EXCEPTION -> if (mainView != null) {
                mainView.hideProgressBar()
            }

            else -> {}
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun setRecyclerView(rr: RecyclerView) {
        layoutManager = GridLayoutManager(context, numberOfColumns)


        if (RESIZE_ENABLED) {
            layoutManager!!.spanSizeLookup = sizeLookup
        }
        rr.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                if (END_SCROLL_ENABLED) {
                    this@FragmentMain.isLoading = true
                    //Increment page index to load the next one
                    //CURRENT_PAGE_NUMBER++;
                    //loadNextPage(CURRENT_PAGE_NUMBER);
                    nextPage()
                }
            }

            override fun getTotalPageCount(): Int {
                //return TOTAL_PAGES;
                return mBinding!!.pageNavigation.totaL_PAGES
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
        rr.layoutManager = layoutManager
        rr.adapter = mViewAdapter
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentCallback) {
            callback = context
        } else {
            throw RuntimeException("$context must implement Callback")
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    private fun homeScreenView() {
        var home = false
        try {
            if (mBinding!!.recyclerView.adapter != null && mBinding!!.recyclerView.adapter!!
                    .itemCount > 0
            ) {
                val viewType = mBinding!!.recyclerView.adapter!!.getItemViewType(0)
                if (PaginationAdapter.TYPE_INFORMATION == viewType) {
                    d("HOME_SCREEN: $viewType")
                    home = true
                }
            }
            if (!home) {
                d("SET_HOME_SCREEN: ")
                val objectList: MutableList<Any> = ArrayList()

                mBinding!!.pageNavigation.reset()

                objectList.add(APIError())
                mViewAdapter!!.clear()
                mViewAdapter!!.addAll(objectList)

                //Remove menu
            }
        } catch (w: Exception) {
            handleException(w)
        }
    }

    override fun onResume() {
        super.onResume()
        //        if (BuildConfig.DEBUG) {
//            ActionBar sab = ((AppCompatActivity) getActivity()).getSupportActionBar();
//            sab.setDisplayHomeAsUpEnabled(true);
//            sab.setDisplayHomeAsUpEnabled(true);
//            Toolbar tb = ((AppCompatActivity) getActivity()).findViewById(R.id.toolbar);
//            tb.setNavigationOnClickListener(v -> {
//                //??? CURRENT_PAGE_NUMBER = PAGE_START;
//                mBinding.pageNavigation.setCURRENT_PAGE_NUMBER(mBinding.pageNavigation.PAGE_START);
//                isLastPage = false;
//                //mViewAdapter.clear();
//                //loadNextPage(CURRENT_PAGE_NUMBER);
//                loadNextPage(mBinding.pageNavigation.getCURRENT_PAGE_NUMBER());
//            });
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_SHAPE_OPTION -> if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val value = data.getStringExtra(D_Shape.KEY_INPUT_OUTPUT_DATA)
                    //Toast.makeText(getContext(), "" + value, Toast.LENGTH_LONG).show();
                    //String s = String.format(getString(R.string.shape_selected),
                    // QueryConstants.shapes[position]);
                    //callback.mSnackbar(s);
                    if (DEFAULT_ANY == value) {
                        PillRequest.INSTANCE.remove(MpcField.SHAPE.value)
                        updateStatus()
                    } else {
                        PillRequest.INSTANCE.put(MpcField.SHAPE.value, value!!)
                        updateStatus()
                        newRequest()
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // After Cancel code.
            }

            REQUEST_COLOR_OPTION -> if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val color = data.getStringExtra(D_Color.KEY_INPUT_OUTPUT_COLOR)
                    if (color != null && !color.isEmpty()) {
                        PillRequest.INSTANCE.put(
                            MpcField.COLOR.value,
                            color
                        ) //Reset color if string empty or set
                        updateStatus()
                        newRequest()
                    } else {
                        PillRequest.INSTANCE.remove(MpcField.COLOR.value)
                        updateStatus()
                    }
                }
            }

            REQUEST_IMPRINT_OPTION -> if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val imprint = data.getStringExtra(D_Imprint.KEY_INPUT_OUTPUT_DATA)
                    if (imprint != null && imprint.length > 0) {
                        PillRequest.INSTANCE.put(MpcField.IMPRINT.value, imprint)
                        updateStatus()
                        newRequest()
                    } else {
                        PillRequest.INSTANCE.remove(MpcField.IMPRINT.value)
                        updateStatus()
                    }
                }
            }

            REQUEST_SCORING_OPTION -> if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val score = data.getStringExtra(D_Scoring.KEY_INPUT_OUTPUT_DATA)
                    if (score != null && score == DEFAULT_ANY) {
                        PillRequest.INSTANCE.remove(MpcField.SCORE)
                        updateStatus()
                    } else {
                        PillRequest.INSTANCE.put(MpcField.SCORE.value, score!!)
                        updateStatus()
                        newRequest()
                    }
                }
            }

            REQUEST_SIZE_OPTION -> if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val value = data.getIntExtra(D_Size.KEY_INPUT_OUTPUT_DATA, -1)
                    if (value == 0) {
                        PillRequest.INSTANCE.remove(MpcField.SIZE)
                        updateStatus()
                    } else {
                        PillRequest.INSTANCE.put(MpcField.SIZE.value, value.toString())
                        updateStatus()
                        newRequest()
                    }
                }
            }
        }
    }


    private fun updateStatus() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("(");
//        String[] arr = new String[5];

//        arr[0] = PillRequest.INSTANCE.get(FIELD_IMPRINT);
//        arr[1] = PillRequest.INSTANCE.get(FIELD_SIZE);
//        arr[2] = PillRequest.INSTANCE.get(FIELD_COLOR);
//        arr[3] = PillRequest.INSTANCE.get(FIELD_SHAPE);
//        arr[4] = PillRequest.INSTANCE.get(FIELD_SCORE);

//        for (int i = 0; i < arr.length; i++) {
//            if (arr[i] == null) {
//                sb.append(SB_D);
//            } else {
//                sb.append(arr[i]);
//            }
//            if (i != arr.length - 1) {
//                sb.append("-");
//            }
//        }

        var settingsChanged = false


        val sb = SpannableStringBuilder()
        sb.append("(")
        var tmp0 = PillRequest.INSTANCE.get(MpcField.IMPRINT)
        if (tmp0 == null) {
            sb.append(SB_D)
        } else {
            sb.append(tmp0)
            settingsChanged = true
        }
        sb.append("-")

        tmp0 = PillRequest.INSTANCE.get(MpcField.SIZE)
        if (tmp0 == null) {
            sb.append(SB_D)
        } else {
            sb.append(tmp0)
            settingsChanged = true
        }
        sb.append("-")
        tmp0 = PillRequest.INSTANCE.get(MpcField.COLOR)
        if (tmp0 == null) {
            sb.append("[-]")
        } else {
            val colors = tmp0.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in colors.indices) {
                try {
                    val valueOf =
                        Colors.valueOf(colors[i].trim { it <= ' ' }.uppercase(Locale.getDefault()))
                    val spannable: Spannable = SpannableStringBuilder(valueOf.name)
                    spannable.setSpan(
                        BackgroundColorSpan(resources.getColor(valueOf.value)),
                        0,
                        spannable.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    sb.append(spannable)
                    if (i < colors.size - 1) {
                        sb.append(" ")
                    }
                } catch (e: IllegalArgumentException) {
                    d(e.message!!)
                }
            }
            settingsChanged = true
        }
        sb.append("-")
        tmp0 = PillRequest.INSTANCE.get(MpcField.SHAPE)

        if (tmp0 == null) {
            sb.append(SB_D)
        } else {
            sb.append(tmp0)
            settingsChanged = true
        }
        sb.append("-")
        tmp0 = PillRequest.INSTANCE.get(MpcField.SCORE)

        if (tmp0 == null) {
            sb.append(SB_D)
        } else {
            sb.append(tmp0)
            settingsChanged = true
        }

        sb.append(")")
        if (nonNull(mOptionsMenu)) {
            val mm = mOptionsMenu!!.findItem(R.id.action_clear)
            if (nonNull(mm)) {
                mm.setVisible((settingsChanged))
                onPrepareOptionsMenu(mOptionsMenu!!)
            }
        }

        //not attached to a context
        //getString getActivity().getString
        if (callback != null && isAdded) {
            callback!!.setMainTitle(requireActivity().getString(R.string.app_name), sb)
        }
        d("STATUS_UPDATED: $settingsChanged")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_clear) {
            callback!!.mSnackbar(R.string.settings_reset)
            PillRequest.INSTANCE.reset() //Remove user settings
            setState(State.RESET)

            /**
             *
             *
             *
             */
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        //        MenuItem item = menu.add(0, R.id.action_clear, 100, R.string.action_clear);
//        item.setIcon(R.drawable.ic_clear);
//        item.setVisible(false);
//        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        mOptionsMenu = menu
        fffff7(this)
        if (NOT_FIRST_LAUNCH) {
            updateStatus()
        }
        NOT_FIRST_LAUNCH = true

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        presenter!!.onDestroy()
        callback!!.replyStatus(SpannableString(""))
        super.onDestroy()
    }

    /*
    Callback interface
     */
    interface FragmentCallback : NotificationCallback {
        fun replaceFragment(fragment: Fragment)


        //void request(int i);
        fun showMoreInfo(s: String)

        fun replyStatus(s: Spanned)

        fun handleThrowable(throwable: Throwable)
    }


    @SuppressLint("NonConstantResourceId")
    private val cl = View.OnClickListener { v: View ->
        val id = v.id
        if (id == R.id.next_page_btn) {
            nextPage()
        } else if (id == R.id.prev_page_btn) {
            presenter!!.loadNextPageRequest(
                mBinding!!.pageNavigation.prevPage(),
                IPresenter.QEvent.ADD
            )
        } else if (id == R.id.btn_search) {
            newRequest()
        }
    }

    private fun newRequest() {
        presenter!!.loadNextPageRequest(0, IPresenter.QEvent.REPLACE) //true
    }

    private fun nextPage() {
        presenter!!.loadNextPageRequest(
            mBinding!!.pageNavigation.nextPage(),
            IPresenter.QEvent.ADD
        ) //false
    }

    companion object {
        private const val KEY_FILTER = "key_filter"

        @JvmStatic
        fun newInstance(): Fragment {
            return FragmentMain()
        }

        fun newInstance(places: HashMap<String, String>): Fragment {
            val main = FragmentMain()
            val bundle = Bundle()
            bundle.putSerializable(KEY_FILTER, places)
            main.arguments = bundle
            return main
        }

        private var NOT_FIRST_LAUNCH = false

        //Only for debug
        private const val END_SCROLL_ENABLED =  /*(BuildConfig.DEBUG) ? false : */true


        //    // total no. of pages to load. Initial load is page 0, after which 2 more pages will load.
        //    private int TOTAL_PAGES = 0;
        //
        //    // indicates the current page which Pagination is fetching.
        //    private int CURRENT_PAGE_NUMBER = PAGE_START;
        private const val RESIZE_ENABLED = true


        const val REQUEST_SHAPE_OPTION: Int = 101
        const val REQUEST_COLOR_OPTION: Int = 102
        const val REQUEST_IMPRINT_OPTION: Int = 103
        const val REQUEST_SCORING_OPTION: Int = 104
        const val REQUEST_SIZE_OPTION: Int = 105

        private const val KEY_CURRENT_DIALOG = "key_current_dlg"
    }
}