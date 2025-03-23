package com.walhalla.health.BodyMassIndex;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;
import com.walhalla.health.activity.base.InnerAdActivity;

public class ChartBMI extends InnerAbstractFragment {


    @Override
    protected int aLayout() {
        return R.layout.chart;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.BlueTheme;
//    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        init();
        //setTitle(R.string.chart);//Not work, because we yse toolbar
    }

    private void init() {
        if (null != mainView) {
            mainView.setTitleNew(R.string.bmi_title);
        }
    }
}
