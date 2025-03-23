package com.walhalla.health.BodyMassIndex;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

public class UnderstandBMI extends InnerAbstractFragment {

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;

    @Override
    protected int aLayout() {
        return R.layout.understand_bmi;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.BlueTheme;
//    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {

        if (null != mainView) {
            mainView.setTitleNew(R.string.bmi_title);
        }

        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
        tv3 = view.findViewById(R.id.tv3);
        tv4 = view.findViewById(R.id.tv4);
        tv5 = view.findViewById(R.id.tv5);
        tv6 = view.findViewById(R.id.tv6);
        tv7 = view.findViewById(R.id.tv7);
        tv8 = view.findViewById(R.id.tv8);
    }
}
