package com.walhalla.health.HeartRateCalculator;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;
import com.walhalla.health.activity.base.InnerAdActivity;

public class Chart_heart extends InnerAbstractFragment {


    @Override
    protected int aLayout() {
        return R.layout.chart_heart;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.YellowTheme;
//    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        title(R.string.heart_chart);
    }
}
