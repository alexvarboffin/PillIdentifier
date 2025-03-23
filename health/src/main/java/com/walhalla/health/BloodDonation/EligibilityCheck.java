package com.walhalla.health.BloodDonation;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

public class EligibilityCheck extends InnerAbstractFragment {

    private TextView tv1;
    private TextView tv10;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;


    @Override
    protected int aLayout() {
        return R.layout.eligibility_check;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.PinkTheme;
//    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }
    
    private void init(View view) {

        if (null != mainView) {
            mainView.setTitleNew(R.string.eligibility_check);
        }
        
        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
        tv3 = view.findViewById(R.id.tv3);
        tv4 = view.findViewById(R.id.tv4);
        tv5 = view.findViewById(R.id.tv5);
        tv6 = view.findViewById(R.id.tv6);
        tv7 = view.findViewById(R.id.tv7);
        tv8 = view.findViewById(R.id.tv8);
        tv9 = view.findViewById(R.id.tv9);
        tv10 = view.findViewById(R.id.tv10);
    }
}
