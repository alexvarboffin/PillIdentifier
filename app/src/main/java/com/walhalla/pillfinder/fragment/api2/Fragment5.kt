package com.walhalla.pillfinder.fragment.api2;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.walhalla.lib.RxnormRepository;
;
import com.walhalla.lib.datamodel.pkg5.Response5;
import com.walhalla.lib.datamodel.pkg5.RxtermsProperties;
import com.walhalla.lib.service.RxnormApi;
import com.walhalla.pillfinder.MyApp;
import com.walhalla.pillfinder.R;
import com.walhalla.pillfinder.adapter.ComplexPresenter;
import com.walhalla.pillfinder.adapter.ComplexRecyclerViewAdapter;
import com.walhalla.pillfinder.adapter.emptyView.EmptyViewObj;
import com.walhalla.pillfinder.adapter.obj.NameValue1_2;
import com.walhalla.pillfinder.adapter.obj.NameValue2_1;
import com.walhalla.pillfinder.adapter.obj.SimpleString;
import com.walhalla.pillfinder.adapter.obj.VieModel;
import com.walhalla.pillfinder.adapter.obj.ingredient.IngredientString;
import com.walhalla.pillfinder.databinding.CategoryListFragmentBinding;
import com.walhalla.ui.DLog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment5 extends Fragment
        implements Callback<Response5>,
        ComplexPresenter, SwipeRefreshLayout.OnRefreshListener {


    protected String rxcui;
    private int index;

    public static final String KEY_INDEX = "key_index_rxnormId";
    public static final String KEY_RXNORMID = "key_rxnormId";
    private static final String KEY_QUERY = "key_query";

    private CategoryListFragmentBinding mBinding;

    private RxnormApi api;
    private ComplexRecyclerViewAdapter mAdapter;
    private String query;


    public static Fragment5 newInstance(int index, String rxnormId, String query) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_INDEX, index);
        bundle.putString(KEY_RXNORMID, rxnormId);
        bundle.putString(KEY_QUERY, query);
        Fragment5 fragment = new Fragment5();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = CategoryListFragmentBinding.inflate(inflater);
        if (mAdapter == null) {
            mAdapter = new ComplexRecyclerViewAdapter(getContext()/*, presenter*/);//new AlbumsAdapter(getContext());//
            mAdapter.setChildItemClickListener(this);
        }
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        //mBinding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, Helpers.dpToPx(getContext(), 2), true));
        mBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBinding.recyclerView.setAdapter(mAdapter);
        api = MyApp.rxnorm;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            index = getArguments().getInt(KEY_INDEX, 0);
            rxcui = getArguments().getString(KEY_RXNORMID, null);
            query = getArguments().getString(KEY_QUERY, null);
        }
    }

    public void loadData() {
        Call<Response5> allinfo = api.getAllRxTermInfo(rxcui, RxnormRepository.RX_NAV_CALLER);
        allinfo.enqueue(this);
    }


    public void updateData(List<VieModel> data) {
        List<VieModel> obj = new ArrayList<>(data);
        mAdapter.onRestoreInstanceState(obj);
    }


    @Override
    public void onItemClicked(View v, int position) {

    }

    @Override
    public void onItemClicked(View v, VieModel obj) {

    }

    @Override
    public void onItemClicked(int itemId, VieModel category) {

    }


    @Override
    public void onResume() {
        super.onResume();
        DLog.d("resume");
        mBinding.swiperefresh.setOnRefreshListener(this);
        loadData();
        if (mBinding.swiperefresh.isRefreshing()) {
            mBinding.swiperefresh.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        if (mBinding.swiperefresh.isRefreshing()) {
            mBinding.swiperefresh.setRefreshing(false);
        }
    }

    //*** Fragment not attached to a context

    @Override
    public void onResponse(@NonNull Call<Response5> call, Response<Response5> response) {
        Response5 body = response.body();
        ArrayList<VieModel> data = new ArrayList<>();
        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            if (body != null) {
                RxtermsProperties prop = body.rxtermsProperties;
                if (prop != null) {
                    data.add(new NameValue1_2(getResources().getString(R.string.rx_5_brandName), prop.brandName));
                    data.add(new NameValue1_2(getResources().getString(R.string.rx_5_displayName), prop.displayName));
                    data.add(new NameValue1_2(getResources().getString(R.string.rx_5_synonym), prop.synonym));
                    data.add(new NameValue2_1(getResources().getString(R.string.rx_5_fullName), prop.fullName));
                    data.add(new NameValue1_2(getResources().getString(R.string.rx_5_fullGenericName), prop.fullGenericName));
                    data.add(new NameValue1_2(getResources().getString(R.string.rx_5_strength), prop.strength));
                    data.add(new NameValue1_2(getResources().getString(R.string.rx_5_rxtermsDoseForm), prop.rxtermsDoseForm));
                    data.add(new NameValue1_2(getResources().getString(R.string.rx_5_route), prop.route));
                    data.add(new NameValue1_2(getResources().getString(R.string.rx_5_termType), prop.termType));
                    data.add(new NameValue1_2(getResources().getString(R.string.rx_5_rxcui), prop.rxcui));
                    data.add(new NameValue1_2(getResources().getString(R.string.rx_5_genericRxcui), prop.genericRxcui));
                    data.add(new NameValue2_1(getResources().getString(R.string.rx_5_rxnormDoseForm), prop.rxnormDoseForm));
                    data.add(new NameValue1_2(getResources().getString(R.string.rx_5_suppress), prop.suppress));
                    updateData(data);
                } else {

                    //Вернулся null данных нет
                    //DLog.d("<@@@>EEEEEEEEEEEEE" + body.toString());
                    data.add(new EmptyViewObj(
                            "No data found for given Rxcui (" + rxcui + ")", query));
                    updateData(data);
                }

                //Response{protocol=h2, code=200, message=, url=https://rxnav.nlm.nih.gov/REST/RxTerms/rxcui/317482/allinfo.json?caller=RxNav} █
                //DLog.d("<@@@>EEEEEEEEEEEEE" + prop.toString());
            } else {
                DLog.d("<@@@>EEEEEEEEEEEEE");
            }


        }
    }

    @Override
    public void onFailure(@NonNull Call<Response5> call, @NonNull Throwable t) {
        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            //DLog.d("<@@@>WWWWWWWWWWWWWWWWWWWWW");
            //Toast.makeText(getContext(), "@@@", Toast.LENGTH_SHORT).show();
        }
    }


}
