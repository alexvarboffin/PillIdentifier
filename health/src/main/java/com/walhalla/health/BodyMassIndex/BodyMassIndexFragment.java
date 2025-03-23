package com.walhalla.health.BodyMassIndex;


import static com.walhalla.health.Constant.getShapeDrawable;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.IdealWeight.GenderAdapter;
import com.walhalla.health.IdealWeight.HeightAdapter;
import com.walhalla.health.R;
import com.walhalla.health.M;
import com.walhalla.health.util.PrefData;
import com.walhalla.ui.DLog;

import java.text.NumberFormat;

public class BodyMassIndexFragment
        extends InnerAbstractFragment implements AdapterView.OnItemSelectedListener {
    double age;
    double bmi;

    boolean check = false;
    public boolean cms = true;
    EditText edAge;
    
    EditText edHeight2;
    LinearLayout tvInputHeight2;

    Spinner genderSp;

    double height;
    double height2;
    Spinner heightSp;

    public boolean isKG = true;
    NumberFormat numberFormat;
    String str_bmi;

    
    
    TextView tvcm;
    TextView tvin;
    double weight;
    Spinner weightSp;

    int primaryColor;

    @Override
    protected int aLayout() {
        return R.layout.bodymassindex;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.BlueTheme;
//    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] sex = {getResources().getString(R.string.male), getResources().getString(R.string.female)};
        String[] height = {getResources().getString(R.string.centimeters), getResources().getString(R.string.feets)};
        String[] strArr3 = {getResources().getString(R.string.kilograms), getResources().getString(R.string.pounds)};
        init(view);
        edAge.setText(String.valueOf(pref.getAge()));
        Button button = view.findViewById(R.id.calc);
        Button resetBtn = view.findViewById(R.id.reset);
        Button chart = view.findViewById(R.id.chart);
        button.setBackground(getShapeDrawable(false, primaryColor));
        resetBtn.setBackground(getShapeDrawable(true, primaryColor));
        chart.setBackground(getShapeDrawable(false, primaryColor));

        edHeight2.setEnabled(false);
        edHeight.setEnabled(true);
        tvcm.setText(getResources().getString(R.string.cm));
        tvin.setText("");
        tvInputHeight2.setVisibility(View.GONE);
        chart = view.findViewById(R.id.chart);
        chart.setOnClickListener(view0 ->
        {
            mainView.replaceFragment(new ChartBMI());
        });
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);

        genderSp.setAdapter(new GenderAdapter(getActivity(), R.layout.spinner_down_blue, sex));
        heightSp.setAdapter(new HeightAdapter(getActivity(), R.layout.spinner_down_blue, height));
        weightSp.setAdapter(new WeightAdapter(getActivity(), R.layout.spinner_down_blue, strArr3));


        heightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BodyMassIndexFragment aaa = BodyMassIndexFragment.this;
                if (aaa.heightSp.getSelectedItem().toString()
                        .equals(getActivity().getResources().getString(R.string.centimeters))) {
                    aaa.cms = true;
                    aaa.edHeight.setEnabled(true);
                    aaa.edHeight2.setEnabled(false);
                    aaa.tvInputHeight2.setVisibility(View.GONE);
                    aaa.tvcm.setText(getActivity().getResources().getString(R.string.cm));
                    aaa.tvin.setText("");
                    return;
                }
                aaa.cms = false;
                aaa.edHeight.setEnabled(true);
                aaa.edHeight2.setEnabled(true);
                aaa.tvcm.setText(getActivity().getResources().getString(R.string.ft));
                aaa.tvin.setText(getActivity().getResources().getString(R.string.in));
                aaa.tvInputHeight2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        weightSp.setOnItemSelectedListener(this);

        resetBtn.setOnClickListener(view0 -> {
            String str = "";
            this.edHeight.setText(str);
            this.edWeight.setText(str);
            this.edAge.setText(str);
            this.edHeight2.setText(str);
            this.edAge.requestFocus();
        });

        button.setOnClickListener(view0 -> {
            try {
                try {
                    this.height = Double.parseDouble(this.edHeight.getText().toString());
                } catch (NumberFormatException unused) {
                    this.check = true;
                }
                try {
                    this.weight = Double.parseDouble(this.edWeight.getText().toString());
                } catch (NumberFormatException unused2) {
                    this.check = true;
                }
                try {
                    this.age = Double.parseDouble(this.edAge.getText().toString());
                } catch (NumberFormatException unused3) {
                    this.check = true;
                }
                try {
                    this.height2 = Double.parseDouble(this.edHeight2.getText().toString());
                } catch (NumberFormatException unused4) {
                    this.height2 = 0.0d;
                }
                if (this.check) {
                    mainView.snackbar(R.string.valid);
                    this.check = false;
                    return;
                }
                if (this.cms) {
                    this.height /= 100.0d;
                } else {
                    this.height *= 12.0d;
                    this.height += this.height2;
                    this.height *= 0.0254d;
                }

                if (!this.isKG) {
                    this.weight *= 0.453592d;
                }

                pref.setAge(edAge.getText().toString());
                BodyMassIndexFragment bodyMassIndex2 = BodyMassIndexFragment.this;
                bodyMassIndex2.bmi = (bodyMassIndex2.weight / (this.height * this.height));
                BodyMassIndexFragment bodyMassIndex3 = BodyMassIndexFragment.this;
                bodyMassIndex3.str_bmi = bodyMassIndex3.numberFormat.format(this.bmi);

                if (mainView != null) {
                    mainView.replaceFragment(
                            Result.newInstance(this.str_bmi)
                    );
                }
            } catch (Resources.NotFoundException e) {
                DLog.handleException(e);
            }
        });
    }

    private void init(View view) {
        if (null != mainView) {
            mainView.setTitleNew(R.string.bmi_title);
        }
        bindHeight(view);
        bindWeight(view);
        edAge = view.findViewById(R.id.edAge);
        edHeight2 = view.findViewById(R.id.edHeight2);
        tvInputHeight2 = view.findViewById(R.id.tvInputHeight2);
        edHeight2 = view.findViewById(R.id.edHeight2);
        ////tvHeight = view.findViewById(R.id.tvHeight);
        //tvWeight = view.findViewById(R.id.tvWeight);


        //Macca
        //edWeight.setText("100");
        //edHeight.setText("180");

        //tvAge = view.findViewById(R.id.tvAge);
        //tvGender = view.findViewById(R.id.tvGender);

        tvcm = view.findViewById(R.id.tvcm);
        tvin = view.findViewById(R.id.tvin);
        weightSp = view.findViewById(R.id.weightSp);
        heightSp = view.findViewById(R.id.heightSp);
        genderSp = view.findViewById(R.id.genderSp);
        tvInputHeight2.setVisibility(View.GONE);

        primaryColor = ContextCompat.getColor(
                getActivity().getApplicationContext(), R.color.bluecolorPrimary);
        ImageView img_age = view.findViewById(R.id.image_age);
        ImageView img_height = view.findViewById(R.id.img_height);
        ImageView img_centimeter = view.findViewById(R.id.img_centimeter);
        ImageView img_inch = view.findViewById(R.id.img_inch);
        ImageView img_weight = view.findViewById(R.id.img_weight);
        M.setThemeColor(primaryColor, img_age);
        M.setThemeColor(primaryColor, img_height);
        M.setThemeColor(primaryColor, img_centimeter);
        M.setThemeColor(primaryColor, img_weight);
        M.setThemeColor(primaryColor, img_inch);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        this.isKG = parent.getSelectedItem().toString().equals(this.getResources().getString(R.string.kilograms));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
