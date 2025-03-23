package com.walhalla.bloodAlcohol

import android.os.Bundle
import android.view.View
import com.walhalla.health.IdealWeight.InnerAbstractFragment
import com.walhalla.health.R


class Chart_Alcohol : InnerAbstractFragment() {
    override fun aLayout(): Int {
        return R.layout.chart_alcohol
    }

    //    @Override
    //    protected int aTheme() {
    //        return R.style.GrayTheme;
    //    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (null != mainView) {
            mainView.setTitleNew(R.string.alcohol_chart)
        }
    }
}
