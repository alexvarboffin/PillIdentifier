package com.walhalla.health.BloodDonation;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

public class ChartBloodVolume extends InnerAbstractFragment {

    @Override
    protected int aLayout() {
        return R.layout.chart_blood;
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
        if (null != mainView) {
            mainView.setTitleNew(R.string.canidonate);
        }
        TextView tvHeader = view.findViewById(R.id.tvHeader);
    }

}
