package com.walhalla.health.BloodDonation;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.walhalla.health.BodyMassIndex.Result;
import com.walhalla.health.Constant;
import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

public class CanIDonate extends InnerAbstractFragment {

    TextView tv1;

    @Override
    protected int aLayout() {
        return R.layout.blood_vol_canidonate;
    }

    //    @Override
//    protected int aTheme() {
//        return R.style.PinkTheme;
//    }
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        view.findViewById(R.id.chart).setOnClickListener(view0 -> {
            if (mainView != null) {
                mainView.replaceFragment(
                        new ChartBloodVolume()
                );
            }
        });
    }

    private void init(View view) {
        if (null != mainView) {
            mainView.setTitleNew(R.string.canidonate);
        }

        tv1 = view.findViewById(R.id.tv1);
        Button chart = view.findViewById(R.id.chart);
        chart.setBackground(Constant.getShapeDrawable(false,
                ContextCompat.getColor(getContext().getApplicationContext(), R.color.pinkcolorPrimary)));
    }

}
