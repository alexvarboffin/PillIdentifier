package com.walhalla.health.BodyFat;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;
import com.walhalla.health.activity.base.InnerAdActivity;

public class Chart_Fat extends InnerAbstractFragment {

    @Override
    protected int aLayout() {
        return R.layout.chart_fat;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.BlueTheme;
//    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvHeader = view.findViewById(R.id.tvHeader);
        if (null != mainView) {
            mainView.setTitleNew(R.string.bmi_chart_title);
        }
    }

}
