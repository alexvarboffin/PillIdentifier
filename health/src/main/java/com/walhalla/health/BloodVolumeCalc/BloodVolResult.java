package com.walhalla.health.BloodVolumeCalc;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

public class BloodVolResult extends InnerAbstractFragment {

    private static final String ARG_VALUE = "value0";
    private Animation rotate;
    private TextView tvBloodVol;
    private TextView tvBloodVolVal;
    private TextView tvLiters;
    private String arg0;

    @Override
    protected int aLayout() {
        return R.layout.bloodvolumeresult;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.CyanTheme;
//    }

    public static Fragment newInstance(@NonNull String value) {
        Fragment fragment = new BloodVolResult();
        Bundle arg = new Bundle();
        arg.putString(ARG_VALUE, value);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            arg0 = getArguments().getString(ARG_VALUE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(getContext(), view);
    }

    private void init(Context context, View view) {

//        ImageView imageView = view.findViewById(R.id.ivRotate);
//        rotate = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.rotate);
//        imageView.startAnimation(this.rotate);

        if (null != mainView) {
            mainView.setTitleNew(R.string.bloodvol);
        }
        tvBloodVol = view.findViewById(R.id.tvBloodVol);
        tvBloodVolVal = view.findViewById(R.id.tvBloodVolVal);
        tvBloodVolVal.setText(arg0);
        tvLiters = view.findViewById(R.id.tvLiters);
    }
}
