package com.walhalla.pillfinder.fragment.api2;

import android.app.Activity;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.JsonObject;
import com.walhalla.Util;
import com.walhalla.lib.RxnormRepository;
import com.walhalla.lib.datamodel.pkg0.IdGroup;
import com.walhalla.lib.datamodel.pkg0.Response0;
import com.walhalla.lib.datamodel.pkg1.Response1;
import com.walhalla.lib.service.RxnormApi;
import com.walhalla.lib.service.RxnormRepositoryCallback;
import com.walhalla.pillfinder.MyApp;
import com.walhalla.pillfinder.R;
import com.walhalla.pillfinder.adapter.DynamicModifyViewPagerAdapter;

import com.walhalla.pillfinder.fragment.main.BaseFragment;
import com.walhalla.ui.DLog;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RxNorm extends BaseFragment
        implements RxnormRepositoryCallback {

    public static final String KEY_RXNORMID = "key_rx_norm_Id";
    public static final String KEY_INGREDIENT = "key_Ingredien";

    private static final boolean NOT_ONE_CATEGORY = true;

    private RxnormRepository repo;

    private DynamicModifyViewPagerAdapter mPagerAdapter;

    //private final ArrayList<JsonObject> data = new ArrayList<>();
    Map<String, JsonObject> data = new HashMap<String, JsonObject>();
    List<String> titles = new ArrayList<>();
    private RxnormApi api;
    private Handler handler0;

    private String query0;

    //rxcui or ingredient
    private String rxcui;
    private String ingredient;


    public static Fragment newInstance(String rxnormId) {
        Fragment a = new RxNorm();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_RXNORMID, rxnormId);
        a.setArguments(bundle);
        return a;
    }


    //!!!! without KEY_RXNORMID

    public static Fragment newInstance() {
        return new RxNorm();
    }

    public static Fragment newIngredientInstance(String ingredient) {
        Fragment a = new RxNorm();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_INGREDIENT, ingredient);
        a.setArguments(bundle);
        return a;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rxnorm, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            rxcui = getArguments().getString(KEY_RXNORMID, null);
            ingredient = getArguments().getString(KEY_INGREDIENT, null);
        }

        api = MyApp.rxnorm;
        handler0 = new Handler(Looper.getMainLooper());

//        for (int i1 = 0; i1 < 12; i1++) {
//            data.put("" + i1, new JsonObject());
//            titles.add("" + i1);
//        }

        if (!TextUtils.isEmpty(ingredient)) {
            AutoCompleteTextView autoTextView = getActivity().findViewById(R.id.auto_text_view);
            if (autoTextView != null) {
                autoTextView.setText(ingredient);
                searchIngredient(ingredient);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View fab = view.findViewById(R.id.fab);

        if (!TextUtils.isEmpty(rxcui)) {
            fab.setVisibility(View.GONE);
        } else {
            fab.setOnClickListener(v -> {
                AutoCompleteTextView t = getActivity().findViewById(R.id.auto_text_view);
                String query = t.getText().toString();
                searchIngredient(query);
            });
        }
        TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
        if (null != tabLayout) {
            tabLayout.setVisibility(View.VISIBLE);
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    //DLog.d("" + tab.getText() + " ");
                    invalidateFragmentMenus(tab.getPosition()); //api v2
                    if (getActivity() != null) {
                        Util.hideKeyboardFrom(getActivity(),
                                //getActivity().findViewById(R.id.et_user_input)
                                getActivity().getWindow().getDecorView()
                        );
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

        }

        ViewPager2 viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        if (null != tabLayout) {
            new TabLayoutMediator(tabLayout, viewPager,
                    (tab, position) -> tab.setText(titles.get(position))).attach();
            viewPager.setOffscreenPageLimit(
                    (tabLayout.getTabCount() > 0) ? tabLayout.getTabCount() : ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
            );
        }
    }

    private void searchIngredient(String query) {
        this.query0 = query;
        repo = new RxnormRepository(api, this);
        if (!isValidQuery(query)) {
            mainView.mSnackbar("Please enter Query");
            return;
        }
        //repo.globalRequest(query);
        mainView.showProgressBar();
        repo.globalRequest(query);
    }

    private boolean isValidQuery(String query) {
        return query != null && query.length() > 0;
    }

    @Override
    public void successResponse(Response0 response) {
        if (mainView != null) {
            mainView.hideProgressBar();
        }

        if (response != null) {
            IdGroup idGroup = response.idGroup;
            List<String> rxnormId = idGroup.rxnormId;
            if (rxnormId.isEmpty()) {
                handler0.post(() -> {
                    if (mainView != null) {
                        mainView.mSnackbar("There is no result for '" + query0 + "' (as String)");
                    }
                    mPagerAdapter.updateEmployeeListItems("There is no result for '" + query0 + "' (as String)");
                });
            } else {
                String pd = rxnormId.get(0);
                makeInformationGui(pd);
            }
        }
    }

    private void makeInformationGui(final String pd) {
        //DLog.d("@@@@@@@@@@" + pd);
        List<Fragment> buffer = new ArrayList<>();
        buffer.add(Fragment5.newInstance(1, pd, query0));
        buffer.add(Fragment6.newInstance(1, pd));
        buffer.add(Fragment7.newInstance(1, pd));
        buffer.add(Fragment2.newInstance(1, pd));

        titles.add("[INFO]");//titles.add("[" + pd + "]");
        titles.add("Interaction");
        titles.add("Status");
        titles.add("RxNorm Properties");
        mPagerAdapter.updateEmployeeListItems(buffer);
    }


    @Override
    public void successResponse(Response1 response1) {

    }

    @Override
    public void successResponse(int opCode, JsonObject body) {

    }


    @Override
    public void handleThrowable(Throwable throwable) {
        if (mainView != null) {
            mainView.hideProgressBar();
            //JsonSyntaxException
            //no_parameters_reply
            String err;
            if (throwable instanceof ConnectException) {
                err = getString(R.string.err_connection);
            } else if (throwable instanceof javax.net.ssl.SSLHandshakeException) {
                err = throwable.getLocalizedMessage();
            } else if (throwable instanceof UnknownHostException) {
                err = getString(R.string.err_connection);
            } else if (throwable instanceof java.io.IOException) {
                DLog.d("@" + throwable.getMessage());
                err = getString(R.string.err_connection);
            } else {
                err = throwable.getLocalizedMessage();//getString(R.string.err_refine_query);
            }
            mainView.mSnackbar(err);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_clear) {
            AutoCompleteTextView textView = getActivity().findViewById(R.id.auto_text_view);
            textView.setText("");
            titles.clear();
            mPagerAdapter.updateEmployeeListItems(new ArrayList<>());
            return true;
        }
//        else if (item.getItemId() == R.id.action_help) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    private void invalidateFragmentMenus(int position) {
        for (int i = 0; i < mPagerAdapter.getItemCount(); i++) {
            mPagerAdapter.getItem(i).setHasOptionsMenu(i == position);
        }
        if (getActivity() != null) {
            getActivity().invalidateOptionsMenu(); //or respectively its support method.
        }
    }

    private void setupViewPager(ViewPager2 viewPager) {
        mPagerAdapter = new DynamicModifyViewPagerAdapter(this);
        viewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        DLog.d("<resume>: " + rxcui);

        AppCompatActivity aa = (AppCompatActivity) getActivity();
        if (aa != null) {
            aa.getSupportActionBar().setTitle(R.string.drugsearch);
            aa.getSupportActionBar().setSubtitle(null);//DLog.getAppVersion(getContext())
            aa.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            aa.getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        if (!TextUtils.isEmpty(rxcui)) {
            makeInformationGui(rxcui);
        } else if (/*!onSaveInstanceCalled*/ data == null || data.isEmpty()) {
            DLog.d("LOAD NEW DATA");
            emptyView();
            loadData(0);
        } else {
            Activity activity = getActivity();
            if (activity != null && isAdded()) {
                updateData();
            }//restore data
            //mScreenCategoryListPresenter.setData(data);
        }
    }

    private void loadData(int i) {

    }

    private void emptyView() {

    }


    private void updateData() {
        DLog.d("!!!" + data.size());

        //titles = new ArrayList<>();
        List<Fragment> buffer = new ArrayList<>();

//        buffer.add(new CalculatorFragment());
//        titles.add(getString(R.string.tab_calculator));

//        for (Tab tab : data) {
//            titles.add("xxx"/*tab.getName()*/);
//        }


        //Fragment t1 = new Fragment();

        //this.titles.add(getString(R.string.tab_title_0));
        for (int i = 0; i < 1; i++) {
            JsonObject aa = data.get("" + i);
            CategoryListFragment t1 = CategoryListFragment.newInstance(1, new ArrayList<>(List.of(aa)));
            buffer.add(t1);
        }
        mPagerAdapter.updateEmployeeListItems(new ArrayList<>());
    }

    @Override
    public void successResponse(String message) {
        DLog.d("@@@" + message);
    }

}
