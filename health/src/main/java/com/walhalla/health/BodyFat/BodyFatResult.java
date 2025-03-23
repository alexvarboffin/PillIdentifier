package com.walhalla.health.BodyFat;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;
import com.walhalla.health.IdealWeight.IdealWeightResult;
import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;


public class BodyFatResult extends InnerAbstractFragment {

    private TextView header1;
    private TextView header2;
    private TextView header3;
    private Animation rotate;
    private TextView row11;
    private TextView row12;
    private TextView row13;
    private TextView row21;
    private TextView row22;
    private TextView row23;
    private TextView row31;
    private TextView row32;
    private TextView row33;
    private TextView row41;
    private TextView row42;
    private TextView row43;
    private TextView row51;
    private TextView row52;
    private TextView row53;
    private TextView tvAssessment;
    private TextView tvAssessmentVal;
    private TextView tvBodyFat;
    private TextView tvBodyFatVal;
    private TextView tvPercent;


    @Override
    protected int aLayout() {
        return R.layout.bodyfatresult;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.BlueTheme;
//    }

    private static final String ARG_VALUE = "value";
    private static final String ARG_VALUE2 = "value2";


    private String string;
    private String string2;

    public static Fragment newInstance(@NonNull String value, @NonNull String value2) {
        Fragment fragment = new BodyFatResult();
        Bundle arg = new Bundle();
        arg.putString(ARG_VALUE, value);
        arg.putString(ARG_VALUE2, value2);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            string = getArguments().getString(ARG_VALUE);
            string2 = getArguments().getString(ARG_VALUE2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        ImageView imageView = view.findViewById(R.id.ivRotate);
        rotate = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate);
        imageView.startAnimation(this.rotate);

        tvBodyFatVal.setText(string);
        tvAssessmentVal.setText(string2);
    }

    private void init(View view) {
        if (null != mainView) {
            mainView.setTitleNew(R.string.bodyfat);
        }
        tvBodyFat = view.findViewById(R.id.tvBodyFat);
        tvBodyFatVal = view.findViewById(R.id.tvBodyFatVal);
        tvPercent = view.findViewById(R.id.tvPercent);
        tvAssessment = view.findViewById(R.id.tvAssessment);
        tvAssessmentVal = view.findViewById(R.id.tvAssessmentVal);
        header1 = view.findViewById(R.id.header1);
        header2 = view.findViewById(R.id.header2);
        header3 = view.findViewById(R.id.header3);
        row11 = view.findViewById(R.id.row11);
        row12 = view.findViewById(R.id.row12);
        row13 = view.findViewById(R.id.row13);
        row21 = view.findViewById(R.id.row21);
        row22 = view.findViewById(R.id.row22);
        row23 = view.findViewById(R.id.row23);
        row31 = view.findViewById(R.id.row31);
        row32 = view.findViewById(R.id.row32);
        row33 = view.findViewById(R.id.row33);
        row41 = view.findViewById(R.id.row41);
        row42 = view.findViewById(R.id.row42);
        row43 = view.findViewById(R.id.row43);
        row51 = view.findViewById(R.id.row51);
        row52 = view.findViewById(R.id.row52);
        row53 = view.findViewById(R.id.row53);

    }
}
