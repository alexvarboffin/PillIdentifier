package com.walhalla.health.Pregnancy;


import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.walhalla.health.Constant;
import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

import java.util.Objects;

public class PregnancyCalcRes extends InnerAbstractFragment {

    private static final String ARG_VALUE = "value";

    private Animation rotate;
    private TextView tvEdd;
    private TextView tvEddDesc;
    private TextView tvEddVal;
    private TextView tvEddVal2;

    @Override
    protected int aLayout() {
        return R.layout.pregnancyres;
    }

    //    @Override
//    protected int aTheme() {
//        return R.style.PinkTheme;
//    }

    private String value;

    public static Fragment newInstance(@NonNull String value) {
        Fragment fragment = new PregnancyCalcRes();
        Bundle arg = new Bundle();
        arg.putString(ARG_VALUE, value);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            value = getArguments().getString(ARG_VALUE);
        } else {
            value = "";
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        ImageView imageView = view.findViewById(R.id.ivRotate);
        rotate = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate);
        imageView.startAnimation(this.rotate);

        String[] split = Objects.requireNonNull(value).split("/");
        String str = " ";
        switch (Integer.parseInt(split[0])) {
            case 1:
                TextView textView = this.tvEddVal2;
                String sb = split[1] +
                        str +
                        getResources().getString(R.string.jan) +
                        str +
                        split[2];
                textView.setText(sb);
                break;
            case 2:
                TextView textView2 = this.tvEddVal2;
                String sb2 = split[1] +
                        str +
                        getResources().getString(R.string.feb) +
                        str +
                        split[2];
                textView2.setText(sb2);
                break;
            case 3:
                TextView textView3 = this.tvEddVal2;
                String sb3 = split[1] +
                        str +
                        getResources().getString(R.string.mar) +
                        str +
                        split[2];
                textView3.setText(sb3);
                break;
            case 4:
                TextView textView4 = this.tvEddVal2;
                String sb4 = split[1] +
                        str +
                        getResources().getString(R.string.apr) +
                        str +
                        split[2];
                textView4.setText(sb4);
                break;
            case 5:
                TextView textView5 = this.tvEddVal2;
                String sb5 = split[1] +
                        str +
                        getResources().getString(R.string.may) +
                        str +
                        split[2];
                textView5.setText(sb5);
                break;
            case 6:
                TextView textView6 = this.tvEddVal2;
                String sb6 = split[1] +
                        str +
                        getResources().getString(R.string.jun) +
                        str +
                        split[2];
                textView6.setText(sb6);
                break;
            case 7:
                TextView textView7 = this.tvEddVal2;
                String sb7 = split[1] +
                        str +
                        getResources().getString(R.string.jul) +
                        str +
                        split[2];
                textView7.setText(sb7);
                break;
            case 8:
                TextView textView8 = this.tvEddVal2;
                String sb8 = split[1] +
                        str +
                        getResources().getString(R.string.aug) +
                        str +
                        split[2];
                textView8.setText(sb8);
                break;
            case 9:
                TextView textView9 = this.tvEddVal2;
                String sb9 = split[1] +
                        str +
                        getResources().getString(R.string.sep) +
                        str +
                        split[2];
                textView9.setText(sb9);
                break;
            case 10:
                TextView textView10 = this.tvEddVal2;
                String sb10 = split[1] +
                        str +
                        getResources().getString(R.string.oct) +
                        str +
                        split[2];
                textView10.setText(sb10);
                break;
            case 11:
                TextView textView11 = this.tvEddVal2;
                String sb11 = split[1] +
                        str +
                        getResources().getString(R.string.nov) +
                        str +
                        split[2];
                textView11.setText(sb11);
                break;
            case 12:
                TextView textView12 = this.tvEddVal2;
                String sb12 = split[1] +
                        str +
                        getResources().getString(R.string.dec) +
                        str +
                        split[2];
                textView12.setText(sb12);
                break;
        }
        this.tvEddVal.setText(value);
    }


    private void init(View view) {
        if (null != mainView) {
            mainView.setTitleNew(R.string.pregnancy);
        }

        tvEdd = view.findViewById(R.id.tvEdd);
        tvEddVal = view.findViewById(R.id.tvEddVal);
        tvEddVal2 = view.findViewById(R.id.tvEddVal2);
        tvEddDesc = view.findViewById(R.id.tvEddDesc);

        tvEddDesc.setBackground(Constant.getShapeDrawable(true, ContextCompat.getColor(getActivity().getApplicationContext(), R.color.pinkcolorPrimary)));
    }

}
