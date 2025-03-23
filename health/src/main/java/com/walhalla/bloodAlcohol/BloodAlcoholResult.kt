package com.walhalla.bloodAlcohol;


import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

public class BloodAlcoholResult extends InnerAbstractFragment {

    private static final String ARG_VALUE = "value";

    private Animation rotate;
    private TextView tvBloodAlcohol;
    private TextView tvBloodAlcoholVal;
    private TextView tvPercent;
    private String value;

    public static Fragment newInstance(@NonNull String value) {
        Fragment fragment = new BloodAlcoholResult();
        Bundle arg = new Bundle();
        arg.putString(ARG_VALUE, value);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    protected int aLayout() {
        return R.layout.bloodalcoholresult;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.GrayTheme;
//    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            value = getArguments().getString(ARG_VALUE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        ImageView imageView = view.findViewById(R.id.ivRotate);
        rotate = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate);
        imageView.startAnimation(this.rotate);
        tvBloodAlcoholVal.setText(value);
    }

    private void init(View view) {
        if (null != mainView) {
            mainView.setTitleNew(R.string.bloodalcohol);
        }
        tvBloodAlcohol = view.findViewById(R.id.tvBloodAlcohol);
        tvBloodAlcoholVal = view.findViewById(R.id.tvBloodAlcoholVal);
        tvPercent = view.findViewById(R.id.tvPercent);
    }

}
