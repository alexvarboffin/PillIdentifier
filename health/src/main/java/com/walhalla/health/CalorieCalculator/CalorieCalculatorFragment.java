package com.walhalla.health.CalorieCalculator;


import android.content.Context;
import android.content.res.Resources;
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

import com.github.pdfviewer.PDFView;
import com.walhalla.health.BodyMassIndex.WeightAdapter;
import com.walhalla.health.Constant;
import com.walhalla.health.IdealWeight.GenderAdapter;
import com.walhalla.health.IdealWeight.HeightAdapter;
import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;
import com.walhalla.health.M;
import com.walhalla.health.util.PrefData;
import com.walhalla.ui.DLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;

public class CalorieCalculatorFragment extends InnerAbstractFragment implements AdapterView.OnItemSelectedListener {

    Spinner activitySp;
    int[] activity_img = {
            R.drawable.activity,
            R.drawable.activity,
            R.drawable.activity,
            R.drawable.activity,
            R.drawable.activity
    };
    double age;
    double bmr;
    Button chart;
    boolean check = false;
    public boolean cms = true;
    EditText edAge;
    
    EditText edHeight2;

    double factor = 0.0d;
    Spinner genderSp;
    LinearLayout tvInputHeight2;
    
    double height;
    double height2;
    Spinner heightSp;
    

    public boolean isKG = true;
    public boolean male = true;

    private NumberFormat numberFormat;
    private Button pdf;
    private String str_bmr;


    private TextView tvcm;
    private TextView tvin;
    private double weight;
    private Spinner weightSp;

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

        @NonNull
        public View getView(int i, View view, @Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {
                    getResources().getString(R.string.sedentary),
                    getResources().getString(R.string.lightly_active),
                    getResources().getString(R.string.moderately_active),
                    getResources().getString(R.string.very_active),
                    getResources().getString(R.string.extremely_active)
            };
            View inflate = getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(activity_img[i]);
            return inflate;
        }
    }

    int primaryColor;

    @Override
    protected int aLayout() {
        return R.layout.calorie_calc;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.GrayTheme;
//    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] strArr = {getResources().getString(R.string.male), getResources().getString(R.string.female)};
        String[] strArr2 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.feets)};

        String[] strArr4 = {getResources().getString(R.string.sedentary), getResources().getString(R.string.lightly_active), getResources().getString(R.string.moderately_active), getResources().getString(R.string.very_active), getResources().getString(R.string.extremely_active)};
        init(view);
        edAge.setText(String.valueOf(pref.getAge()));
        Button button = view.findViewById(R.id.calc);
        Button button2 = view.findViewById(R.id.reset);
        Button chart = view.findViewById(R.id.chart);
        Button pdf = view.findViewById(R.id.pdf);

        chart.setBackground(Constant.getShapeDrawable(false, primaryColor));
        pdf.setBackground(Constant.getShapeDrawable(false, primaryColor));
        button.setBackground(Constant.getShapeDrawable(false, primaryColor));
        button2.setBackground(Constant.getShapeDrawable(true, primaryColor));

        edHeight2.setEnabled(false);
        edHeight.setEnabled(true);
        tvcm.setText(getResources().getString(R.string.cm));
        tvin.setText("");
        tvInputHeight2.setVisibility(View.GONE);
        chart.setOnClickListener(view0 -> {
            if (mainView != null) {
                mainView.replaceFragment(
                        new Chart_Calorie()
                );
            }
        });

        pdf.setOnClickListener(view0 -> {
            final String SAMPLE_FILE = "food_calorie_list.pdf";
            try {
                File file = new File(getActivity().getCacheDir(), SAMPLE_FILE);
                if (!file.exists()) {

                    try {
                        InputStream asset = getActivity().getAssets().open(SAMPLE_FILE);
                        FileOutputStream output;
                        output = new FileOutputStream(file);
                        final byte[] buffer = new byte[1024];
                        int size;
                        while ((size = asset.read(buffer)) != -1) {
                            output.write(buffer, 0, size);
                        }
                        asset.close();
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

//                    PDFView.with(CalorieCalculatorFragment.this)
//                            .setfilepath(file.getAbsolutePath())
//                            .setSwipeOrientation(VERTICAL)
//                            .start();

                PDFView.with(getActivity())
                        .fromfilepath(file.getAbsolutePath())
                        //.setSwipeOrientation(VERTICAL) //if false pageswipe is vertical otherwise its horizontal
                        .swipeHorizontal(false)
                        .start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        genderSp.setAdapter(new GenderAdapter(getActivity(), R.layout.spinner_down_blue, strArr));
        heightSp.setAdapter(new HeightAdapter(getActivity(), R.layout.spinner_down_blue, strArr2));
        String[] strArr3 = new String[]{
                getResources().getString(R.string.kilograms),
                getResources().getString(R.string.pounds)
        };
        weightSp.setAdapter(new WeightAdapter(getActivity(), R.layout.spinner_down_blue, strArr3));
        activitySp.setAdapter(new MyAdapter4(getActivity(), R.layout.spinner_down_blue, strArr4));


        heightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (CalorieCalculatorFragment.this.heightSp.getSelectedItem().toString().equals(CalorieCalculatorFragment.this.getResources().getString(R.string.centimeters))) {
                    CalorieCalculatorFragment calorieCalculator = CalorieCalculatorFragment.this;
                    calorieCalculator.cms = true;
                    calorieCalculator.edHeight.setEnabled(true);
                    CalorieCalculatorFragment.this.edHeight2.setEnabled(false);
                    CalorieCalculatorFragment.this.tvInputHeight2.setVisibility(View.GONE);
                    CalorieCalculatorFragment.this.tvcm.setText(CalorieCalculatorFragment.this.getResources().getString(R.string.cm));
                    CalorieCalculatorFragment.this.tvin.setText("");
                    return;
                }
                CalorieCalculatorFragment calorieCalculator2 = CalorieCalculatorFragment.this;
                calorieCalculator2.cms = false;
                calorieCalculator2.edHeight.setEnabled(true);
                CalorieCalculatorFragment.this.edHeight2.setEnabled(true);
                CalorieCalculatorFragment.this.tvcm.setText(CalorieCalculatorFragment.this.getResources().getString(R.string.ft));
                CalorieCalculatorFragment.this.tvin.setText(CalorieCalculatorFragment.this.getResources().getString(R.string.in));
                CalorieCalculatorFragment.this.tvInputHeight2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        weightSp.setOnItemSelectedListener(this);
        activitySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String obj = CalorieCalculatorFragment.this.activitySp.getSelectedItem().toString();
                if (obj.equals(CalorieCalculatorFragment.this.getResources().getString(R.string.sedentary))) {
                    CalorieCalculatorFragment.this.factor = 1.2d;
                } else if (obj.equals(CalorieCalculatorFragment.this.getResources().getString(R.string.lightly_active))) {
                    CalorieCalculatorFragment.this.factor = 1.375d;
                } else if (obj.equals(CalorieCalculatorFragment.this.getResources().getString(R.string.moderately_active))) {
                    CalorieCalculatorFragment.this.factor = 1.55d;
                } else if (obj.equals(CalorieCalculatorFragment.this.getResources().getString(R.string.very_active))) {
                    CalorieCalculatorFragment.this.factor = 1.725d;
                } else {
                    CalorieCalculatorFragment.this.factor = 1.9d;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        genderSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CalorieCalculatorFragment.this.male = CalorieCalculatorFragment.this.genderSp.getSelectedItem().toString().equals(CalorieCalculatorFragment.this.getResources().getString(R.string.male));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        button2.setOnClickListener(view0 -> {
            String str = "";
            CalorieCalculatorFragment.this.edHeight.setText(str);
            CalorieCalculatorFragment.this.edWeight.setText(str);
            CalorieCalculatorFragment.this.edAge.setText(str);
            CalorieCalculatorFragment.this.edHeight2.setText(str);
            CalorieCalculatorFragment.this.edAge.requestFocus();
        });
        button.setOnClickListener(view0 -> {
            try {
                try {
                    CalorieCalculatorFragment.this.height = Double.parseDouble(CalorieCalculatorFragment.this.edHeight.getText().toString());
                } catch (NumberFormatException unused) {
                    CalorieCalculatorFragment.this.check = true;
                }
                try {
                    CalorieCalculatorFragment.this.weight = Double.parseDouble(CalorieCalculatorFragment.this.edWeight.getText().toString());
                } catch (NumberFormatException unused2) {
                    CalorieCalculatorFragment.this.check = true;
                }
                try {
                    CalorieCalculatorFragment.this.age = Double.parseDouble(CalorieCalculatorFragment.this.edAge.getText().toString());
                } catch (NumberFormatException unused3) {
                    CalorieCalculatorFragment.this.check = true;
                }
                try {
                    CalorieCalculatorFragment.this.height2 = Double.parseDouble(CalorieCalculatorFragment.this.edHeight2.getText().toString());
                } catch (NumberFormatException unused4) {
                    CalorieCalculatorFragment.this.height2 = 0.0d;
                }
                if (this.check) {
                    toast(R.string.valid);
                    CalorieCalculatorFragment.this.check = false;
                    return;
                }
                if (!CalorieCalculatorFragment.this.cms) {
                    CalorieCalculatorFragment.this.height *= 12.0d;
                    CalorieCalculatorFragment.this.height += CalorieCalculatorFragment.this.height2;
                    CalorieCalculatorFragment.this.height *= 2.54d;
                }
                if (!CalorieCalculatorFragment.this.isKG) {
                    CalorieCalculatorFragment.this.weight *= 0.453592d;
                }
                if (CalorieCalculatorFragment.this.male) {
                    CalorieCalculatorFragment.this.weight *= 13.7d;
                    CalorieCalculatorFragment.this.weight += 66.0d;
                    CalorieCalculatorFragment.this.height *= 5.0d;
                    CalorieCalculatorFragment.this.age *= 6.8d;
                } else {
                    CalorieCalculatorFragment.this.weight *= 9.6d;
                    CalorieCalculatorFragment.this.weight += 655.0d;
                    CalorieCalculatorFragment.this.height *= 1.8d;
                    CalorieCalculatorFragment.this.age *= 4.7d;
                }
                
                pref.setAge(edAge.getText().toString());

                CalorieCalculatorFragment calorieCalculator3 = CalorieCalculatorFragment.this;
                calorieCalculator3.bmr = (calorieCalculator3.weight + CalorieCalculatorFragment.this.height) - CalorieCalculatorFragment.this.age;
                CalorieCalculatorFragment.this.bmr *= CalorieCalculatorFragment.this.factor;
                CalorieCalculatorFragment calorieCalculator4 = CalorieCalculatorFragment.this;
                calorieCalculator4.str_bmr = calorieCalculator4.numberFormat.format(CalorieCalculatorFragment.this.bmr);

                if (mainView != null) {
                    mainView.replaceFragment(
                            Calorie_result.newInstance(this.str_bmr)
                    );
                }
            } catch (Resources.NotFoundException e) {
                DLog.handleException(e);
            }
        });
    }

    private void init(View view) {

        if (null != mainView) {
            mainView.setTitleNew(R.string.calories);
        }

        bindHeight(view);
        bindWeight(view);
        edAge = view.findViewById(R.id.edAge);

        edHeight2 = view.findViewById(R.id.edHeight2);
        ////tvHeight = view.findViewById(R.id.tvHeight);
        //tvWeight = view.findViewById(R.id.tvWeight);

//        //tvAge = view.findViewById(R.id.tvAge);
//        tvGender = view.findViewById(R.id.tvGender);
        tvcm = view.findViewById(R.id.tvcm);
        tvin = view.findViewById(R.id.tvin);
//        tvActivity = view.findViewById(R.id.tvActivity);
        weightSp = view.findViewById(R.id.weightSp);
        heightSp = view.findViewById(R.id.heightSp);
        genderSp = view.findViewById(R.id.genderSp);
        activitySp = view.findViewById(R.id.activitySp);
        tvInputHeight2 = view.findViewById(R.id.tvInputHeight2);
        chart = view.findViewById(R.id.chart);
        pdf = view.findViewById(R.id.pdf);


        primaryColor = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.graycolorPrimary);

        ImageView img_age = view.findViewById(R.id.image_age);
        ImageView img_height = view.findViewById(R.id.img_height);
        ImageView img_centimeter = view.findViewById(R.id.img_centimeter);
        ImageView img_inch = view.findViewById(R.id.img_inch);
        ImageView img_weight = view.findViewById(R.id.img_weight);
        ImageView img_activity = view.findViewById(R.id.img_activity);
        M.setThemeColor(primaryColor, img_age);
        M.setThemeColor(primaryColor, img_height);
        M.setThemeColor(primaryColor, img_centimeter);
        M.setThemeColor(primaryColor, img_inch);
        M.setThemeColor(primaryColor, img_weight);
        M.setThemeColor(primaryColor, img_activity);
    }
}
