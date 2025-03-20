package com.walhalla.pillfinder.fragment.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.walhalla.pillfinder.Constants;
import gov.nih.nlm.model.NlmRxImage;

import com.walhalla.ui.DLog;

public class FragmentDemo extends BaseFragment implements Constants, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    private int var0 = 0;
    private Button button;

    private static final String SAVED_STATE = "test_bundle";
    private Bundle savedState = null;

    private FragmentMain.FragmentCallback callback;
    //private NlmrximageListBinding mBinding;

    public static Fragment newInstance() {
        return new FragmentDemo();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);


        if (savedInstanceState != null) {
            DLog.d("Restore result");
            return;
        }
        DLog.d("ON_CREATE");
        savedState = new Bundle();
    }

    /**
     *
     * 1
     */
    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //if (mBinding == null) {
        //mBinding = DataBindingUtil.inflate(inflater, R.layout.nlmrximage_list, container, false);
        DLog.d("ON_CREATE_VIEW");

        //}
        if (savedInstanceState != null) {
            DLog.d("Restore result onCreateView");

        }
        if (button == null) {
            button = new Button(getContext());
        }
        button.setText(var0 + "@" + this.hashCode());
        button.setId(87777);
        button.setOnClickListener(this);
        return button;//inflater.inflate(R.layout.nlmrximage_list, container, false);
    }
    /**
     *
     * 2
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            DLog.d("#######33 Restore result");
            return;
        }
        DLog.d("ON_VIEW_CREATED or backstack?");
        jjj(view);
        //view.setOnClickListener(this);
    }

    private void jjj(View view) {
        DLog.d("View: " + view.getClass().getSimpleName());
//        SwipeRefreshLayout nn = view.findViewById(R.id.swipe_refresh_layout);
//        nn.setBackgroundResource(R.drawable.pretty_gradient);
//        nn.setClickable(true);
//        nn.setFocusable(true);
//        nn.setOnClickListener(this);
//        nn.setOnRefreshListener(this);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /* If onDestroyView() is called first, we can use the previously savedState but we can't call saveState() anymore */
        /* If onSaveInstanceState() is called first, we don't have savedState, so we need to call saveState() */
        /* => (?:) operator inevitable! */
        outState.putBundle(SAVED_STATE, (savedState != null) ? savedState : saveState());
    }

    private Bundle saveState() {
        savedState.putString("test", String.valueOf(this.hashCode()));
        return savedState;
    }
    /**
     *
     * 3
     */
    @Override
    public void onResume() {
        super.onResume();

        DLog.d("ON_RESUME: " + this.hashCode() + " " + saveState());//Back from desktop
    }

    @Override
    public void onClick(View v) {
        DLog.d("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2");
        if (callback != null) {
            callback.showMoreInfo(new Gson().toJson(new NlmRxImage()));
            var0++;
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentMain.FragmentCallback) {
            callback = (FragmentMain.FragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onRefresh() {
        DLog.d("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2");
        if (callback != null) {
            callback.showMoreInfo(new Gson().toJson(new NlmRxImage()));
        }
    }
}
