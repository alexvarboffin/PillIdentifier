package com.walhalla.health.Ovulation;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;
import com.walhalla.health.activity.base.InnerAdActivity;

public class ChartOvu extends InnerAbstractFragment {


    @Override
    protected int aLayout() {
        return R.layout.chart_ovulation;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.YellowTheme;
//    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (mainView != null) {
            mainView.setTitleNew(R.string.chart_ovulation);
        }
    }
}
