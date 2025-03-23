package com.walhalla.health.BodyMassIndex;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

import java.io.PrintStream;

public class Result extends InnerAbstractFragment {

    private static final String ARG_VALUE = "value";

    Animation animBlink;
    Button bmi;
    TextView case1;
    TextView case2;
    TextView case3;
    TextView case4;
    TextView case5;
    TextView case6;
    TextView case7;
    TextView casy1;
    TextView casy2;
    TextView casy3;
    TextView casy4;
    TextView casy5;
    TextView casy6;
    TextView casy7;
    ImageView image;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView image5;
    ImageView image6;
    ImageView image7;
    double myNum = 0.0d;
    int num = 0;

    ProgressBar progressBar;
    String strOne;
    TextView sub1;
    TextView sub2;
    TextView sub3;
    TextView sub4;
    TextView sub5;
    TextView sub6;
    TextView tvCat;
    TextView tvOne;
    TextView tvTwo;

    public static Fragment newInstance(String value) {
        Fragment fragment = new Result();
        Bundle arg = new Bundle();
        arg.putString(ARG_VALUE, value);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            strOne = getArguments().getString(ARG_VALUE);
        }
    }

    @Override
    protected int aLayout() {
        return R.layout.bodymassindexresult;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.BlueTheme;
//    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animBlink = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.blink);
        init(view);
        tvTwo.setText(this.strOne);
        try {
            myNum = Double.valueOf(this.tvTwo.getText().toString());
            num = (int) this.myNum;
            if (num < 16) {
                num = 1;
                tvCat.setText(getResources().getString(R.string.exunder));
                image1.setVisibility(View.VISIBLE);
                image1.startAnimation(this.animBlink);
            } else if (this.num > 40) {
                num = 100;
                tvCat.setText(getResources().getString(R.string.morbid));
                image7.setVisibility(View.VISIBLE);
                image7.startAnimation(this.animBlink);
            } else {
                if (myNum >= 16.0d && this.myNum <= 18.5d) {
                    tvCat.setText(getResources().getString(R.string.underweight));
                    image2.setVisibility(View.VISIBLE);
                    image2.startAnimation(this.animBlink);
                } else if (this.myNum > 18.5d && this.myNum <= 25.0d) {
                    tvCat.setText(getResources().getString(R.string.normalweight));
                    image3.setVisibility(View.VISIBLE);
                    image3.startAnimation(this.animBlink);
                } else if (this.myNum > 25.0d && this.myNum <= 30.0d) {
                    tvCat.setText(getResources().getString(R.string.overweight));
                    image4.setVisibility(View.VISIBLE);
                    image4.startAnimation(this.animBlink);
                } else if (this.myNum > 30.0d && this.myNum <= 35.0d) {
                    tvCat.setText(getResources().getString(R.string.obeseone));
                    image5.setVisibility(View.VISIBLE);
                    image5.startAnimation(this.animBlink);
                } else if (this.myNum > 35.0d && this.myNum <= 40.0d) {
                    tvCat.setText(getResources().getString(R.string.obesetwo));
                    image6.setVisibility(View.VISIBLE);
                    image6.startAnimation(this.animBlink);
                } else if (this.myNum < 16.0d) {
                    tvCat.setText(getResources().getString(R.string.exunder));
                    image1.setVisibility(View.VISIBLE);
                    image1.startAnimation(this.animBlink);
                } else if (this.myNum > 40.0d) {
                    tvCat.setText(getResources().getString(R.string.morbid));
                    image7.setVisibility(View.VISIBLE);
                    image7.startAnimation(this.animBlink);
                }
                num -= 15;
                num *= 4;
            }
        } catch (NumberFormatException e) {
            PrintStream printStream = System.out;
            String sb = "Could not parse " +
                    e;
            printStream.println(sb);
            num = 100;
            tvCat.setText(getResources().getString(R.string.morbid));
            image7.setVisibility(View.VISIBLE);
            image7.startAnimation(this.animBlink);
        }
        progressBar.setProgress(this.num);
    }

    private void init(View view) {
        if (null != mainView) {
            mainView.setTitleNew(R.string.bmi_title);
        }

        progressBar = view.findViewById(R.id.progressBar2);
        image = view.findViewById(R.id.image);
        tvOne = view.findViewById(R.id.tvOne);
        tvTwo = view.findViewById(R.id.tvTwo);
        bmi = view.findViewById(R.id.bmi);
        bmi.setOnClickListener(view0 ->
        {
            if (mainView != null) {
                mainView.replaceFragment(
                        new UnderstandBMI()
                );
            }
        });
        tvCat = view.findViewById(R.id.cat);
        sub1 = view.findViewById(R.id.sub1);
        sub2 = view.findViewById(R.id.sub2);
        sub3 = view.findViewById(R.id.sub3);
        sub4 = view.findViewById(R.id.sub4);
        sub5 = view.findViewById(R.id.sub5);
        sub6 = view.findViewById(R.id.sub6);
        case1 = view.findViewById(R.id.case1);
        case2 = view.findViewById(R.id.case2);
        case3 = view.findViewById(R.id.case3);
        case4 = view.findViewById(R.id.case4);
        case5 = view.findViewById(R.id.case5);
        case6 = view.findViewById(R.id.case6);
        case7 = view.findViewById(R.id.case7);
        casy1 = view.findViewById(R.id.casy1);
        casy2 = view.findViewById(R.id.casy2);
        casy3 = view.findViewById(R.id.casy3);
        casy4 = view.findViewById(R.id.casy4);
        casy5 = view.findViewById(R.id.casy5);
        casy6 = view.findViewById(R.id.casy6);
        casy7 = view.findViewById(R.id.casy7);
        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        image4 = view.findViewById(R.id.image4);
        image5 = view.findViewById(R.id.image5);
        image6 = view.findViewById(R.id.image6);
        image7 = view.findViewById(R.id.image7);
    }

}
