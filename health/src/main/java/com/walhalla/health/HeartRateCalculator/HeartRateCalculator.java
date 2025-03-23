package com.walhalla.health.HeartRateCalculator;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.walhalla.health.Constant;
import com.walhalla.health.IdealWeight.GenderAdapter;
import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;
import com.walhalla.health.M;
import com.walhalla.health.util.PrefData;
import com.walhalla.ui.DLog;

import java.text.NumberFormat;

public class HeartRateCalculator extends InnerAbstractFragment {

    private Spinner activitySp;
    private double age;
    private Button chart;
    private boolean check = false;
    private EditText edAge;
    private EditText edRhr;
    private double factor1;
    private double factor2;
    private Spinner genderSp;
    private boolean male = true;
    private double max;
    private double mhr;
    private double min;
    private NumberFormat numberFormat;
    private double rhr;
    private String str_max;
    private String str_mhr;
    private String str_min;

    private int primaryColor;

    @Override
    protected int aLayout() {
        return R.layout.heartrate;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.YellowTheme;
//    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] strArr = {getResources().getString(R.string.male), getResources().getString(R.string.female)};
        String[] strArr2 = {
                getContext().getResources().getString(R.string.activity_level_moderate),
                getContext().getResources().getString(R.string.activity_level_little_diff),
                getContext().getResources().getString(R.string.activity_level_moderately_diff),
                getContext().getResources().getString(R.string.activity_level_hard)
        };
        init(view);
        edAge.setText(String.valueOf(pref.getAge()));

        Button button = view.findViewById(R.id.calc);
        Button button2 = view.findViewById(R.id.reset);


        init(view);
        Button chart = view.findViewById(R.id.chart);


        button.setBackground(Constant.getShapeDrawable(false, primaryColor));
        button2.setBackground(Constant.getShapeDrawable(true, primaryColor));
        chart.setBackground(Constant.getShapeDrawable(false, primaryColor));

        chart.setOnClickListener(view0 ->
                go(new Chart_heart())
        );
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        genderSp.setAdapter(new GenderAdapter(getActivity(), R.layout.spinner_down_blue, strArr));
        activitySp.setAdapter(new ActivityAdapter(getActivity(), R.layout.spinner_down_blue, strArr2));


        activitySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String obj = activitySp.getSelectedItem().toString();
                if (obj.equals(getResources().getString(R.string.activity_level_moderate))) {
                    HeartRateCalculator heartRateCalculator = HeartRateCalculator.this;
                    heartRateCalculator.factor1 = 0.6d;
                    heartRateCalculator.factor2 = 0.65d;
                } else if (obj.equals(getResources().getString(R.string.activity_level_little_diff))) {
                    HeartRateCalculator heartRateCalculator2 = HeartRateCalculator.this;
                    heartRateCalculator2.factor1 = 0.65d;
                    heartRateCalculator2.factor2 = 0.7d;
                } else if (obj.equals(
                        getResources().getString(R.string.activity_level_moderately_diff))) {
                    HeartRateCalculator heartRateCalculator3 = HeartRateCalculator.this;
                    heartRateCalculator3.factor1 = 0.7d;
                    heartRateCalculator3.factor2 = 0.75d;
                } else {
                    HeartRateCalculator heartRateCalculator4 = HeartRateCalculator.this;
                    heartRateCalculator4.factor1 = 0.75d;
                    heartRateCalculator4.factor2 = 0.8d;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        genderSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                male = genderSp.getSelectedItem().toString().equals(getResources().getString(R.string.male));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button2.setOnClickListener(view0 -> {
            String str = "";
            edAge.setText(str);
            edRhr.setText(str);
            edAge.requestFocus();
        });
        button.setOnClickListener(view0 -> {
            try {
                try {
                    age = Double.parseDouble(edAge.getText().toString());
                } catch (NumberFormatException unused) {
                    check = true;
                }
                try {
                    rhr = Double.parseDouble(edRhr.getText().toString());
                } catch (NumberFormatException unused2) {
                    check = true;
                }
                if (check) {
                    toast(R.string.valid);
                    check = false;
                    return;
                }
                if (male) {
                    age *= 0.8d;
                    HeartRateCalculator heartRateCalculator = HeartRateCalculator.this;
                    heartRateCalculator.mhr = 214.0d - heartRateCalculator.age;
                } else {
                    age *= 0.9d;
                    HeartRateCalculator heartRateCalculator2 = HeartRateCalculator.this;
                    heartRateCalculator2.mhr = 209.0d - heartRateCalculator2.age;
                }
                pref.setAge(edAge.getText().toString());

                str_mhr = numberFormat.format(mhr);
                mhr -= rhr;
                HeartRateCalculator heartRateCalculator4 = HeartRateCalculator.this;
                heartRateCalculator4.min = (heartRateCalculator4.mhr * factor1) + rhr;
                HeartRateCalculator heartRateCalculator5 = HeartRateCalculator.this;
                heartRateCalculator5.max = (heartRateCalculator5.mhr * factor2) + rhr;
                HeartRateCalculator heartRateCalculator6 = HeartRateCalculator.this;
                heartRateCalculator6.str_min = heartRateCalculator6.numberFormat.format(min);
                HeartRateCalculator heartRateCalculator7 = HeartRateCalculator.this;
                heartRateCalculator7.str_max = heartRateCalculator7.numberFormat.format(max);

                DLog.d("@@" + str_mhr + "::" + mhr);
                go(HeartRateResult.newInstance(str_mhr, str_min, str_max));
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        });
    }


    private void init(View view) {
        title(R.string.heartrate);
        edAge = view.findViewById(R.id.edAge);
        edRhr = view.findViewById(R.id.edRhr);
        //tvAge = view.findViewById(R.id.tvAge);
        //tvGender = view.findViewById(R.id.tvGender);
        //tvRhr = view.findViewById(R.id.tvRhr);
        //tvActivity = view.findViewById(R.id.tvActivity);
        genderSp = view.findViewById(R.id.genderSp);
        activitySp = view.findViewById(R.id.activitySp);
        chart = view.findViewById(R.id.chart);
        primaryColor = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.yellowcolorPrimary);

        ImageView img_age = view.findViewById(R.id.image_age);
        ImageView image_heart = view.findViewById(R.id.image_heart);
        ImageView img_itensity = view.findViewById(R.id.img_itensity);

        M.setThemeColor(primaryColor, img_age);
        M.setThemeColor(primaryColor, image_heart);
        M.setThemeColor(primaryColor, img_itensity);
    }

}
