package com.walhalla.health.IdealWeight;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.walhalla.health.R;

public class ChartIdeal extends InnerAbstractFragment {

    @Override
    protected int aLayout() {
        return R.layout.chart_ideal;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.OrangeTheme;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        if (null != mainView) {
            mainView.setTitleNew(R.string.idealweight);
        }
    }
}
