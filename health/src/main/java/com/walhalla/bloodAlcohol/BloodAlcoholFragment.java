package com.walhalla.bloodAlcohol;

import static com.walhalla.health.Constant.getShapeDrawable;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.walhalla.health.BodyMassIndex.WeightAdapter;
import com.walhalla.health.IdealWeight.GenderAdapter;
import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;
import com.walhalla.health.M;
import com.walhalla.ui.DLog;

import java.text.NumberFormat;

public class BloodAlcoholFragment extends InnerAbstractFragment implements AdapterView.OnItemSelectedListener {

    private double alcohollevel;
    private double bac;
    private boolean check = false;
    private EditText edAlcoholLevel;
    private EditText edTime;
    private EditText edVolDrinked;
    
    private int factor = 0;
    private int factor2 = 0;

    /* renamed from: kg */
    public boolean isKG = true;
    public boolean male = true;

    /* renamed from: nf */
    NumberFormat numberFormat;
    String str_bac;
    double time;

    Spinner genderSp;
    Spinner timeSp;
    Spinner volumeSp;
    Spinner weightSp;


    private double volDrinked;


    double weight;
    int primaryColor;

    @Override
    protected int aLayout() {
        return R.layout.bloodalcoholcalc;
    }

    //    @Override
//    protected int aTheme() {
//        return R.style.GrayTheme;
//    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] strArr = {getResources().getString(R.string.male), getResources().getString(R.string.female)};
        String[] strArr2 = {getResources().getString(R.string.kilograms), getResources().getString(R.string.pounds)};
        String[] strArr3 = {getResources().getString(R.string.hour), getResources().getString(R.string.minute), getResources().getString(R.string.day)};
        String[] strArr4 = {getResources().getString(R.string.ounces), getResources().getString(R.string.ml), getResources().getString(R.string.cup)};

        Button button = view.findViewById(R.id.calc);
        Button reset = view.findViewById(R.id.reset);
        Button chart = view.findViewById(R.id.chart);
        init(view);

        chart.setBackground(getShapeDrawable(false, primaryColor));
        button.setBackground(getShapeDrawable(false, primaryColor));
        reset.setBackground(getShapeDrawable(true, primaryColor));

        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(3);
        genderSp.setAdapter(new GenderAdapter(getActivity(), R.layout.spinner_down_blue, strArr));
        volumeSp.setAdapter(new VolumeAdapter(getActivity(), R.layout.spinner_down_blue, strArr4));
        weightSp.setAdapter(new WeightAdapter(getActivity(), R.layout.spinner_down_blue, strArr2));
        timeSp.setAdapter(new TimeAdapter(getActivity(), R.layout.spinner_down_blue, strArr3));

        view.findViewById(R.id.chart).setOnClickListener(view0 -> {
            if (mainView != null) {
                mainView.replaceFragment(
                        new Chart_Alcohol()
                );
            }
        });

        weightSp.setOnItemSelectedListener(this);


        genderSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                male = genderSp.getSelectedItem().toString().equals(
                        getActivity().getResources().getString(R.string.male));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        volumeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String obj = volumeSp.getSelectedItem().toString();
                if (obj.equals(getActivity().getResources().getString(R.string.ounces))) {
                    factor = 1;
                } else if (obj.equals(getActivity().getResources().getString(R.string.ml))) {
                    factor = 2;
                } else {
                    factor = 3;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        timeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String obj = timeSp.getSelectedItem().toString();
                if (obj.equals(getActivity().getResources().getString(R.string.hour))) {
                    factor2 = 1;
                } else if (obj.equals(getActivity().getResources().getString(R.string.minute))) {
                    factor2 = 2;
                } else {
                    factor2 = 3;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        reset.setOnClickListener(view0 -> {
            String str = "";
            this.edAlcoholLevel.setText(str);
            this.edWeight.setText(str);
            this.edTime.setText(str);
            this.edAlcoholLevel.requestFocus();
        });
        button.setOnClickListener(view0 -> {
            try {
                this.alcohollevel = Double.parseDouble(this.edAlcoholLevel.getText().toString());
            } catch (NumberFormatException unused) {
                this.check = true;
            }
            try {
                this.volDrinked = Double.parseDouble(this.edVolDrinked.getText().toString());
            } catch (NumberFormatException unused2) {
                this.check = true;
            }
            try {
                this.weight = Double.parseDouble(this.edWeight.getText().toString());
            } catch (NumberFormatException unused3) {
                this.check = true;
            }
            try {
                this.time = Double.parseDouble(this.edTime.getText().toString());
            } catch (NumberFormatException unused4) {
                this.check = true;
            }
            if (this.check) {
                toast(R.string.valid);
                this.check = false;
                return;
            }
            if (this.isKG) {
                this.weight *= 2.20462d;
            } else {
            }
            if (this.factor == 1) {
            } else if (this.factor == 2) {
                this.volDrinked *= 0.033814d;
            } else {
                this.volDrinked *= 8.0d;
            }
            this.alcohollevel /= 100.0d;
            this.volDrinked *= this.alcohollevel;
            if (this.factor2 == 1) {
            } else if (this.factor2 == 2) {
                this.time *= 0.0166d;
            } else {
                this.time *= 24.0d;
            }
            this.weight = 5.14d / this.weight;
            this.time *= 0.015d;
            if (this.male) {
                this.bac = ((this.volDrinked * this.weight) * 0.73d) - this.time;
            } else {
                this.bac = ((this.volDrinked * this.weight) * 0.66d) - this.time;
            }

            if(bac<0){
                bac=0.0;
            }
            this.str_bac = this.numberFormat.format(this.bac);

            if (mainView != null) {
                mainView.replaceFragment(BloodAlcoholResult.newInstance(this.str_bac));
            }
        });
    }

    private void init(View view) {

        if (null != mainView) {
            mainView.setTitleNew(R.string.bloodalcohol);
        }

        edTime = view.findViewById(R.id.edTime);
        bindWeight(view);
        edAlcoholLevel = view.findViewById(R.id.edAlcoholLevel);
        edVolDrinked = view.findViewById(R.id.edVolDrinked);

        //@@@TextView tvVolDrinked = view.findViewById(R.id.tvVolDrinked);
        //@@@ TextView tvAlcoholLevel = view.findViewById(R.id.tvAlcoholLevel);
        //@@@ TextView tvTime = view.findViewById(R.id.tvTime);
        //@@@ TextViewtvGender = view.findViewById(R.id.tvGender);
        //@@@ TextViewtvPercent = view.findViewById(R.id.tvPercent);
        //@@@ TextView//tvWeight = view.findViewById(R.id.tvWeight);


        weightSp = view.findViewById(R.id.weightSp);
        volumeSp = view.findViewById(R.id.volumeSp);
        genderSp = view.findViewById(R.id.genderSp);
        timeSp = view.findViewById(R.id.timeSp);
        primaryColor = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.graycolorPrimary);

        ImageView img_gender = view.findViewById(R.id.img_gender);
        ImageView img_cocktail = view.findViewById(R.id.img_cocktail);
        ImageView img_drink = view.findViewById(R.id.img_drink);
        ImageView img_time = view.findViewById(R.id.img_time);
        ImageView img_weight = view.findViewById(R.id.img_weight);
        M.setThemeColor(primaryColor, img_cocktail);
        M.setThemeColor(primaryColor, img_gender);
        M.setThemeColor(primaryColor, img_drink);
        M.setThemeColor(primaryColor, img_time);
        M.setThemeColor(primaryColor, img_weight);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        isKG = parent.getSelectedItem().toString()
                .equals(getActivity().getResources().getString(R.string.kilograms));
        DLog.d("@@@@"+parent.getSelectedItem().toString()+" "+position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
