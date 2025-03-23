package com.walhalla.bloodAlcohol;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

public class Chart_Alcohol extends InnerAbstractFragment {

    @Override
    protected int aLayout() {
        return R.layout.chart_alcohol;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.GrayTheme;
//    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (null != mainView) {
            mainView.setTitleNew(R.string.alcohol_chart);
        }
    }
}
