package com.walhalla.health.WaterIntakeCalc;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;
import com.walhalla.health.activity.base.InnerAdActivity;

public class Chart_Water extends InnerAbstractFragment {


    @Override
    protected int aLayout() {
        return R.layout.chart_water;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.OrangeTheme;
//    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        init();
    }

    private void init() {
        if (null != mainView) {
            mainView.setTitleNew(R.string.chart_water);
        }
    }

}
