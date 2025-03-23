package com.walhalla.health.WaterIntakeCalc;

import android.content.res.Resources;
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
import com.walhalla.health.Constant;
import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;
import com.walhalla.health.M;
import com.walhalla.health.util.PrefData;
import com.walhalla.ui.DLog;

import java.text.NumberFormat;

public class WaterIntakeFragment extends InnerAbstractFragment implements AdapterView.OnItemSelectedListener {

    private double age;
    private boolean check = false;
    private double dwi;
    private int dwi_int;
    private EditText edAge;
    
    private boolean isKG = true;
    private NumberFormat numberFormat;
    private String str_dwi;
    private double weight;
    private Spinner weightSp;
    private int primaryColor;

    @Override
    protected int aLayout() {
        return R.layout.waterintakecalc;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] strArr = {getResources().getString(R.string.kilograms), getResources().getString(R.string.pounds)};

        Button button = view.findViewById(R.id.calc);
        Button button2 = view.findViewById(R.id.reset);
        init(view);
        edAge.setText(String.valueOf(pref.getAge()));

        view.findViewById(R.id.chart).setOnClickListener(
                view0 -> {
                    if (mainView != null) {
                        mainView.replaceFragment(new Chart_Water());
                    }
                });
        Button chart = view.findViewById(R.id.chart);

        button.setBackground(Constant.getShapeDrawable(false, primaryColor));
        button2.setBackground(Constant.getShapeDrawable(true, primaryColor));
        chart.setBackground(Constant.getShapeDrawable(false, primaryColor));

        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        weightSp.setAdapter(new WeightAdapter(
                getActivity(), R.layout.spinner_down_blue, strArr));


        weightSp.setOnItemSelectedListener(this);

        button2.setOnClickListener(view0 -> {
            String str = "";
            this.edWeight.setText(str);
            this.edAge.setText(str);
            this.edWeight.requestFocus();
        });
        button.setOnClickListener(view0 -> {
            try {
                try {
                    this.weight = Double.parseDouble(this.edWeight.getText().toString());
                } catch (NumberFormatException unused) {
                    this.check = true;
                }
                try {
                    this.age = Double.parseDouble(this.edAge.getText().toString());
                } catch (NumberFormatException unused2) {
                    this.check = true;
                }
                if (this.check) {
                    mainView.snackbar(R.string.valid);
                    this.check = false;
                    return;
                }
                if (!this.isKG) {
                    this.weight /= 2.2d;
                }
                if (this.age <= 30.0d) {
                    this.dwi = this.weight * 40.0d;
                } else if (this.age > 55.0d) {
                    WaterIntakeFragment waterIntakeCalc3 = this;
                    waterIntakeCalc3.dwi = waterIntakeCalc3.weight * 30.0d;
                } else {
                    WaterIntakeFragment waterIntakeCalc4 = this;
                    waterIntakeCalc4.dwi = waterIntakeCalc4.weight * 35.0d;
                }

                pref.setAge(edAge.getText().toString());

                this.dwi /= 28.3d;
                this.dwi /= 8.0d;

                this.dwi_int = (int) this.dwi;
                this.str_dwi = String.valueOf(dwi_int);


                if (mainView != null) {
                    mainView.replaceFragment(
                            WaterIntakeResult.newInstance(this.str_dwi)
                    );
                }
            } catch (Resources.NotFoundException e) {
                DLog.handleException(e);
            }
        });
    }

    private void init(View view) {
        if (null != mainView) {
            mainView.setTitleNew(R.string.waterintake);
        }

        bindWeight(view);
        edAge = view.findViewById(R.id.edAge);

//        TextView //tvWeight = view.findViewById(R.id.tvWeight);
//        TextView //tvAge = view.findViewById(R.id.tvAge);
//        DLog.d("@@@" + tvWeight + " " + tvAge);

        weightSp = view.findViewById(R.id.weightSp);
        primaryColor = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.orangecolorPrimary);

        ImageView img_age = view.findViewById(R.id.image_age);
        ImageView img_weight = view.findViewById(R.id.img_weight);

        M.setThemeColor(primaryColor, img_age);
        M.setThemeColor(primaryColor, img_weight);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        this.isKG = parent.getSelectedItem().toString().equals(this.getResources().getString(R.string.kilograms));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
