package com.walhalla.pillfinder.fragment.api2;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.walhalla.pillfinder.adapter.obj.VieModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryListFragment extends SwipeFragment<VieModel> {

    protected int opCode;

    public static final String KEY_INDEX = "key_index";
    public static final String KEY_TAB = "key_tab";

    public static CategoryListFragment newInstance(int index, ArrayList<JsonObject> list) {
        Bundle bundle = new Bundle();
        bundle.putInt(CategoryListFragment.KEY_INDEX, index);
        bundle.putSerializable(CategoryListFragment.KEY_TAB, list);
        CategoryListFragment fragment = new CategoryListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Serializable tmp = getArguments().getSerializable(KEY_TAB);
            if (tmp != null) {
                adapterSavedState = (List<VieModel>) tmp;
            }
            opCode = getArguments().getInt(KEY_INDEX, 0);
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        makeGui();

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void actionFilter() {

    }

    private void makeGui() {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //JsonObject body = adapterSavedState.get(0);


        switch (opCode) {
            case 0:
//                Response0 response0 = gson.fromJson(body.toString(), Response0.class);
//
//                IdGroup mm = response0.idGroup;
//                List<String> rxnormId = mm.rxnormId;
//                String pd = rxnormId.get(0);
                break;

            case 1:

                break;

            case 2:

                break;

            case 3:

                break;

            case 4:

                break;

            case 5:

                break;

            case 6:

                break;

            case 7:
//                if (body != null) {

//                }
                break;

            case 8:

                break;

            case 9:

                break;


            case 10:

                break;

            case 11:

                break;
        }

        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            updateData(adapterSavedState);
        }
    }

    @Override
    public void updateData(List<VieModel> data) {
        super.updateData(data);
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


    
}
