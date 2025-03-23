package com.walhalla.health.Pregnancy;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

public class ChartPreg extends InnerAbstractFragment {


    @Override
    protected int aLayout() {
        return R.layout.chart_preg;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.PinkTheme;
//    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        init();
    }

    private void init() {
        if (null != mainView) {
            mainView.setTitleNew(R.string.preg_chart);
        }
    }

}
