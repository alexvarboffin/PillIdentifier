package com.walhalla.health.CalorieCalculator;

import android.os.Bundle;

import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

public class Chart_Calorie extends InnerAbstractFragment {


    @Override
    protected int aLayout() {
        return R.layout.chart_calorie;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.GrayTheme;
//    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        init();
    }

    private void init() {
        if (null != mainView) {
            mainView.setTitleNew(R.string.chart_calorie);
        }
    }
}
