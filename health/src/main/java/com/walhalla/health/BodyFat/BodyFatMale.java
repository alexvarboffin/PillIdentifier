package com.walhalla.health.BodyFat;

import android.content.Context;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.walhalla.health.BodyMassIndex.WeightAdapter;
import com.walhalla.health.IdealWeight.HeightAdapter;
import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;
import com.walhalla.health.M;


import java.text.NumberFormat;

import static com.walhalla.health.Constant.getShapeDrawable;

public class BodyFatMale extends InnerAbstractFragment implements AdapterView.OnItemSelectedListener {

    double bf;
    boolean check = false;
    public boolean cms = true;
    public boolean cms2 = true;
    public boolean cms3 = true;
    
    EditText edHeight2;
    LinearLayout tvInputHeight2;
    EditText edNeck;
    EditText edWaist;
    double height;
    double height2;
    Spinner heightSp;


    public boolean isKG = true;
    double neck;
    Spinner neckSp;


    NumberFormat numberFormat;
    double result1;
    double result2;
    String str_assess;
    String str_bf;
    
    TextView tvNeck;
    TextView tvWaist;
    
    TextView tvcm;
    TextView tvin;
    double waist;
    Spinner waistSp;
    int[] waist_img = {R.drawable.height, R.drawable.height};
    double weight;
    Spinner weightSp;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        this.isKG = parent.getSelectedItem().toString().equals(this.getResources().getString(R.string.kilograms));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public class MyAdapter4 extends ArrayAdapter<String> {
        MyAdapter4(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, @Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, @Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BodyFatMale.this.getResources().getString(R.string.centimeters), BodyFatMale.this.getResources().getString(R.string.feets)};
            View inflate = BodyFatMale.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyFatMale.this.waist_img[i]);
            return inflate;
        }
    }

    @Override
    protected int aLayout() {
        return R.layout.bodyfatmale;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.BlueTheme;
//    }
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] strArr = {getResources().getString(R.string.centimeters), getResources().getString(R.string.feets)};
        String[] strArr2 = {getResources().getString(R.string.kilograms), getResources().getString(R.string.pounds)};
        String[] strArr3 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.inches)};
        String[] strArr4 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.inches)};

        Button button = view.findViewById(R.id.calc);
        Button button2 = view.findViewById(R.id.reset);
        init(view);

        button.setBackground(getShapeDrawable(false, primaryColor));
        button2.setBackground(getShapeDrawable(true, primaryColor));
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        heightSp.setAdapter(new HeightAdapter(getActivity(), R.layout.spinner_down_blue, strArr));
        weightSp.setAdapter(new WeightAdapter(getActivity(), R.layout.spinner_down_blue, strArr2));
        waistSp.setAdapter(new MyAdapter4(getActivity(), R.layout.spinner_down_blue, strArr3));
        neckSp.setAdapter(new NeckAdapter(getActivity(), R.layout.spinner_down_blue, strArr4));


        heightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (BodyFatMale.this.heightSp.getSelectedItem().toString().equals(BodyFatMale.this.getResources().getString(R.string.centimeters))) {
                    BodyFatMale bodyFatMale = BodyFatMale.this;
                    bodyFatMale.cms = true;
                    bodyFatMale.edHeight.setEnabled(true);
                    BodyFatMale.this.edHeight2.setEnabled(false);
                    BodyFatMale.this.tvInputHeight2.setVisibility(View.GONE);
                    BodyFatMale.this.tvcm.setText(BodyFatMale.this.getResources().getString(R.string.cm));
                    BodyFatMale.this.tvin.setText("");
                    return;
                }
                BodyFatMale bodyFatMale2 = BodyFatMale.this;
                bodyFatMale2.cms = false;
                bodyFatMale2.edHeight.setEnabled(true);
                BodyFatMale.this.edHeight2.setEnabled(true);
                BodyFatMale.this.tvcm.setText(BodyFatMale.this.getResources().getString(R.string.ft));
                BodyFatMale.this.tvin.setText(BodyFatMale.this.getResources().getString(R.string.in));
                BodyFatMale.this.tvInputHeight2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        waistSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (BodyFatMale.this.waistSp.getSelectedItem().toString().equals(BodyFatMale.this.getResources().getString(R.string.centimeters))) {
                    BodyFatMale bodyFatMale = BodyFatMale.this;
                    bodyFatMale.cms2 = true;
                    return;
                }
                BodyFatMale bodyFatMale2 = BodyFatMale.this;
                bodyFatMale2.cms2 = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        neckSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (BodyFatMale.this.neckSp.getSelectedItem().toString().equals(BodyFatMale.this.getResources().getString(R.string.centimeters))) {
                    BodyFatMale bodyFatMale = BodyFatMale.this;
                    bodyFatMale.cms3 = true;
                    return;
                }
                BodyFatMale bodyFatMale2 = BodyFatMale.this;
                bodyFatMale2.cms3 = false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        weightSp.setOnItemSelectedListener(this);

        button2.setOnClickListener(view0 -> {
            String str = "";
            BodyFatMale.this.edHeight.setText(str);
            BodyFatMale.this.edHeight2.setText(str);
            BodyFatMale.this.edWeight.setText(str);
            BodyFatMale.this.edWaist.setText(str);
            BodyFatMale.this.edNeck.setText(str);
            BodyFatMale.this.edHeight.requestFocus();
        });
        button.setOnClickListener(view0 -> {
            try {
                BodyFatMale.this.height = Double.parseDouble(BodyFatMale.this.edHeight.getText().toString());
            } catch (NumberFormatException unused) {
                BodyFatMale.this.check = true;
            }
            try {
                BodyFatMale.this.height2 = Double.parseDouble(BodyFatMale.this.edHeight2.getText().toString());
            } catch (NumberFormatException unused2) {
                BodyFatMale.this.height2 = 0.0d;
            }
            try {
                BodyFatMale.this.weight = Double.parseDouble(BodyFatMale.this.edWeight.getText().toString());
            } catch (NumberFormatException unused3) {
                BodyFatMale.this.check = true;
            }
            try {
                BodyFatMale.this.waist = Double.parseDouble(BodyFatMale.this.edWaist.getText().toString());
            } catch (NumberFormatException unused4) {
                BodyFatMale.this.check = true;
            }
            try {
                BodyFatMale.this.neck = Double.parseDouble(BodyFatMale.this.edNeck.getText().toString());
            } catch (NumberFormatException unused5) {
                BodyFatMale.this.check = true;
            }
            if (BodyFatMale.this.check) {
                toast(R.string.valid);
                BodyFatMale.this.check = false;
                return;
            }
            if (BodyFatMale.this.isKG) {
                BodyFatMale.this.weight *= 2.20462d;
            }
            if (BodyFatMale.this.cms) {
                BodyFatMale.this.height *= 0.393701d;
            } else {
                BodyFatMale.this.height *= 12.0d;
                BodyFatMale.this.height += BodyFatMale.this.height2;
            }
            if (BodyFatMale.this.cms2) {
                BodyFatMale.this.waist *= 0.393701d;
            }
            if (BodyFatMale.this.cms3) {
                BodyFatMale.this.neck *= 0.393701d;
            }
            BodyFatMale bodyFatMale4 = BodyFatMale.this;
            bodyFatMale4.result1 = (bodyFatMale4.weight * 1.082d) + 94.42d;
            BodyFatMale bodyFatMale5 = BodyFatMale.this;
            bodyFatMale5.result2 = bodyFatMale5.result1 - (BodyFatMale.this.waist * 4.15d);
            BodyFatMale bodyFatMale6 = BodyFatMale.this;
            bodyFatMale6.bf = ((bodyFatMale6.weight - BodyFatMale.this.result2) * 100.0d) / BodyFatMale.this.weight;
            if (BodyFatMale.this.bf >= 2.0d && BodyFatMale.this.bf <= 5.0d) {
                BodyFatMale bodyFatMale7 = BodyFatMale.this;
                bodyFatMale7.str_assess = bodyFatMale7.getResources().getString(R.string.essential);
            } else if (BodyFatMale.this.bf >= 6.0d && BodyFatMale.this.bf <= 13.0d) {
                BodyFatMale bodyFatMale8 = BodyFatMale.this;
                bodyFatMale8.str_assess = bodyFatMale8.getResources().getString(R.string.typicalathlete);
            } else if (BodyFatMale.this.bf >= 14.0d && BodyFatMale.this.bf <= 17.0d) {
                BodyFatMale bodyFatMale9 = BodyFatMale.this;
                bodyFatMale9.str_assess = bodyFatMale9.getResources().getString(R.string.physicallyfit);
            } else if (BodyFatMale.this.bf < 18.0d || BodyFatMale.this.bf > 24.0d) {
                BodyFatMale bodyFatMale10 = BodyFatMale.this;
                bodyFatMale10.str_assess = bodyFatMale10.getResources().getString(R.string.obese);
            } else {
                BodyFatMale bodyFatMale11 = BodyFatMale.this;
                bodyFatMale11.str_assess = bodyFatMale11.getResources().getString(R.string.acceptable);
            }
            BodyFatMale bodyFatMale12 = BodyFatMale.this;
            bodyFatMale12.str_bf = bodyFatMale12.numberFormat.format(BodyFatMale.this.bf);

            if (mainView != null) {
                mainView.replaceFragment(
                        BodyFatResult.newInstance(
                                BodyFatMale.this.str_bf,
                                BodyFatMale.this.str_assess
                        )
                );
            }

        });
    }

    int primaryColor;

    private void init(View view) {

        if (null != mainView) {
            mainView.setTitleNew(R.string.bodyfat);
        }

        bindHeight(view);
        bindWeight(view);
        edHeight2 = view.findViewById(R.id.edHeight2);
        tvInputHeight2 = view.findViewById(R.id.tvInputHeight2);
        edWaist = view.findViewById(R.id.edWaist);
        edNeck = view.findViewById(R.id.edNeck);
        ////tvHeight = view.findViewById(R.id.tvHeight);
        ////tvWeight = view.findViewById(R.id.tvWeight);
        //tvWaist = view.findViewById(R.id.tvWaist);
        //tvNeck = view.findViewById(R.id.tvNeck);
        tvcm = view.findViewById(R.id.tvcm);
        tvin = view.findViewById(R.id.tvin);
        weightSp = view.findViewById(R.id.weightSp);
        heightSp = view.findViewById(R.id.heightSp);
        waistSp = view.findViewById(R.id.waistSp);
        neckSp = view.findViewById(R.id.neckSp);
        tvInputHeight2 = view.findViewById(R.id.tvInputHeight2);

        primaryColor = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.bluecolorPrimary);

        ImageView img_height = view.findViewById(R.id.img_height);
        ImageView img_waist = view.findViewById(R.id.img_waist);
        ImageView img_weight = view.findViewById(R.id.img_weight);
        ImageView img_centimeter = view.findViewById(R.id.img_centimeter);
        ImageView img_inch = view.findViewById(R.id.img_inch);
        ImageView img_neck = view.findViewById(R.id.img_neck);
        M.setThemeColor(primaryColor, img_waist);
        M.setThemeColor(primaryColor, img_centimeter);
        M.setThemeColor(primaryColor, img_height);
        M.setThemeColor(primaryColor, img_neck);
        M.setThemeColor(primaryColor, img_weight);
        M.setThemeColor(primaryColor, img_inch);
    }


}
