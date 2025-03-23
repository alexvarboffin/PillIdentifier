package com.walhalla.health.BodyFat;

import android.content.Context;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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

public class BodyFatFemale extends InnerAbstractFragment implements AdapterView.OnItemSelectedListener {

    double bf;
    boolean check = false;
    public boolean cms = true;
    public boolean cms2 = true;
    public boolean cms3 = true;
    public boolean cms4 = true;
    public boolean cms5 = true;
    public boolean cms6 = true;
    EditText edForearm;
    int primaryColor;
    
    EditText edHeight2;
    LinearLayout tvInputHeight2;
    EditText edHip;
    EditText edNeck;
    EditText edWaist;

    EditText edWrist;
    double forearm;
    Spinner forearmSp;
    int[] forearm_img = {R.drawable.height, R.drawable.height};
    double height;
    double height2;
    Spinner heightSp;

    double hip;
    Spinner hipSp;
    int[] hip_img = {R.drawable.height, R.drawable.height};

    /* renamed from: kg */
    public boolean isKG = true;
    double neck;
    Spinner neckSp;
    

    /* renamed from: nf */
    NumberFormat numberFormat;
    double result3;
    String str_assess;
    String str_bf;





    TextView tvcm;
    //    TextView tvcm2;
//    TextView tvcm3;
//    TextView tvcm4;
//    TextView tvcm5;
//    TextView tvcm6;
    TextView tvin;
    double waist;
    Spinner waistSp;
    int[] waist_img = {R.drawable.height, R.drawable.height};
    double weight;
    Spinner weightSp;

    double wrist;
    Spinner wristSp;
    int[] wrist_img = {R.drawable.height, R.drawable.height};

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        this.isKG = parent.getSelectedItem().toString().equals(BodyFatFemale.this.getResources().getString(R.string.kilograms));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    public class MyAdapter4 extends ArrayAdapter<String> {
        MyAdapter4(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BodyFatFemale.this.getResources().getString(R.string.centimeters), BodyFatFemale.this.getResources().getString(R.string.feets)};
            View inflate = BodyFatFemale.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyFatFemale.this.waist_img[i]);
            return inflate;
        }
    }

    public class MyAdapter6 extends ArrayAdapter<String> {
        MyAdapter6(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BodyFatFemale.this.getResources().getString(R.string.centimeters), BodyFatFemale.this.getResources().getString(R.string.feets)};
            View inflate = BodyFatFemale.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyFatFemale.this.forearm_img[i]);
            return inflate;
        }
    }

    public class MyAdapter7 extends ArrayAdapter<String> {
        MyAdapter7(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BodyFatFemale.this.getResources().getString(R.string.centimeters), BodyFatFemale.this.getResources().getString(R.string.feets)};
            View inflate = BodyFatFemale.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyFatFemale.this.wrist_img[i]);
            return inflate;
        }
    }

    public class MyAdapter8 extends ArrayAdapter<String> {
        MyAdapter8(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BodyFatFemale.this.getResources().getString(R.string.centimeters), BodyFatFemale.this.getResources().getString(R.string.feets)};
            View inflate = BodyFatFemale.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyFatFemale.this.hip_img[i]);
            return inflate;
        }
    }

    @Override
    protected int aLayout() {
        return R.layout.bodyfatfemale;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.BlueTheme;
//    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] strArr = {getResources().getString(R.string.centimeters), getResources().getString(R.string.feets)};
        String[] strArr2 = {getResources().getString(R.string.kilograms), getResources().getString(R.string.pounds)};
        String[] strArr3 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.in)};
        String[] strArr4 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.in)};
        String[] strArr5 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.in)};
        String[] strArr6 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.in)};
        String[] strArr7 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.in)};
        init(view);
        Button button = view.findViewById(R.id.calc);
        Button button2 = view.findViewById(R.id.reset);
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);

        button.setBackground(getShapeDrawable(false, primaryColor));
        button2.setBackground(getShapeDrawable(true, primaryColor));

        heightSp.setAdapter(new HeightAdapter(getActivity(), R.layout.spinner_down_blue, strArr));
        weightSp.setAdapter(new WeightAdapter(getActivity(), R.layout.spinner_down_blue, strArr2));
        waistSp.setAdapter(new MyAdapter4(getActivity(), R.layout.spinner_down_blue, strArr4));
        neckSp.setAdapter(new NeckAdapter(getActivity(), R.layout.spinner_down_blue, strArr5));
        forearmSp.setAdapter(new MyAdapter6(getActivity(), R.layout.spinner_down_blue, strArr6));
        wristSp.setAdapter(new MyAdapter7(getActivity(), R.layout.spinner_down_blue, strArr3));
        hipSp.setAdapter(new MyAdapter8(getActivity(), R.layout.spinner_down_blue, strArr7));

        heightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (BodyFatFemale.this.heightSp.getSelectedItem().toString().equals(BodyFatFemale.this.getResources().getString(R.string.centimeters))) {
                    BodyFatFemale bodyFatFemale = BodyFatFemale.this;
                    bodyFatFemale.cms = true;
                    bodyFatFemale.edHeight.setEnabled(true);
                    BodyFatFemale.this.edHeight2.setEnabled(false);
                    BodyFatFemale.this.tvInputHeight2.setVisibility(View.GONE);
                    BodyFatFemale.this.tvcm.setText(BodyFatFemale.this.getResources().getString(R.string.cm));
                    BodyFatFemale.this.tvin.setText("");
                    return;
                }
                BodyFatFemale bodyFatFemale2 = BodyFatFemale.this;
                bodyFatFemale2.cms = false;
                bodyFatFemale2.edHeight.setEnabled(true);
                BodyFatFemale.this.edHeight2.setEnabled(true);
                BodyFatFemale.this.tvcm.setText(BodyFatFemale.this.getResources().getString(R.string.ft));
                BodyFatFemale.this.tvin.setText(BodyFatFemale.this.getResources().getString(R.string.in));
                BodyFatFemale.this.tvInputHeight2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        waistSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (BodyFatFemale.this.waistSp.getSelectedItem().toString().equals(BodyFatFemale.this.getResources().getString(R.string.centimeters))) {
                    BodyFatFemale bodyFatFemale = BodyFatFemale.this;
                    bodyFatFemale.cms2 = true;
                    return;
                }
                BodyFatFemale bodyFatFemale2 = BodyFatFemale.this;
                bodyFatFemale2.cms2 = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        neckSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (BodyFatFemale.this.neckSp.getSelectedItem().toString().equals(BodyFatFemale.this.getResources().getString(R.string.centimeters))) {
                    BodyFatFemale bodyFatFemale = BodyFatFemale.this;
                    bodyFatFemale.cms3 = true;
                    return;
                }
                BodyFatFemale bodyFatFemale2 = BodyFatFemale.this;
                bodyFatFemale2.cms3 = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        forearmSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (BodyFatFemale.this.forearmSp.getSelectedItem().toString().equals(BodyFatFemale.this.getResources().getString(R.string.centimeters))) {
                    BodyFatFemale bodyFatFemale = BodyFatFemale.this;
                    bodyFatFemale.cms4 = true;
                    return;
                }
                BodyFatFemale bodyFatFemale2 = BodyFatFemale.this;
                bodyFatFemale2.cms4 = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        wristSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (BodyFatFemale.this.wristSp.getSelectedItem().toString().equals(BodyFatFemale.this.getResources().getString(R.string.centimeters))) {
                    BodyFatFemale bodyFatFemale = BodyFatFemale.this;
                    bodyFatFemale.cms5 = true;
                    return;
                }
                BodyFatFemale bodyFatFemale2 = BodyFatFemale.this;
                bodyFatFemale2.cms5 = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        hipSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (BodyFatFemale.this.hipSp.getSelectedItem().toString().equals(BodyFatFemale.this.getResources().getString(R.string.centimeters))) {
                    BodyFatFemale bodyFatFemale = BodyFatFemale.this;
                    bodyFatFemale.cms6 = true;
                    return;
                }
                BodyFatFemale bodyFatFemale2 = BodyFatFemale.this;
                bodyFatFemale2.cms6 = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        weightSp.setOnItemSelectedListener(this);


        button2.setOnClickListener(view1 -> {
            String str = "";
            BodyFatFemale.this.edHeight.setText(str);
            BodyFatFemale.this.edHeight2.setText(str);
            BodyFatFemale.this.edWeight.setText(str);
            BodyFatFemale.this.edWaist.setText(str);
            BodyFatFemale.this.edNeck.setText(str);
            BodyFatFemale.this.edForearm.setText(str);
            BodyFatFemale.this.edHip.setText(str);
            BodyFatFemale.this.edWrist.setText(str);
            BodyFatFemale.this.edHeight.requestFocus();
        });
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    BodyFatFemale.this.height = Double.parseDouble(BodyFatFemale.this.edHeight.getText().toString());
                } catch (NumberFormatException unused) {
                    BodyFatFemale.this.check = true;
                }
                try {
                    BodyFatFemale.this.height2 = Double.parseDouble(BodyFatFemale.this.edHeight2.getText().toString());
                } catch (NumberFormatException unused2) {
                    BodyFatFemale.this.height2 = 0.0d;
                }
                try {
                    BodyFatFemale.this.weight = Double.parseDouble(BodyFatFemale.this.edWeight.getText().toString());
                } catch (NumberFormatException unused3) {
                    BodyFatFemale.this.check = true;
                }
                try {
                    BodyFatFemale.this.waist = Double.parseDouble(BodyFatFemale.this.edWaist.getText().toString());
                } catch (NumberFormatException unused4) {
                    BodyFatFemale.this.check = true;
                }
                try {
                    BodyFatFemale.this.neck = Double.parseDouble(BodyFatFemale.this.edNeck.getText().toString());
                } catch (NumberFormatException unused5) {
                    BodyFatFemale.this.check = true;
                }
                try {
                    BodyFatFemale.this.forearm = Double.parseDouble(BodyFatFemale.this.edForearm.getText().toString());
                } catch (NumberFormatException unused6) {
                    BodyFatFemale.this.check = true;
                }
                try {
                    BodyFatFemale.this.wrist = Double.parseDouble(BodyFatFemale.this.edWrist.getText().toString());
                } catch (NumberFormatException unused7) {
                    BodyFatFemale.this.check = true;
                }
                try {
                    BodyFatFemale.this.hip = Double.parseDouble(BodyFatFemale.this.edHip.getText().toString());
                } catch (NumberFormatException unused8) {
                    BodyFatFemale.this.check = true;
                }
                if (BodyFatFemale.this.check) {
                    toast(R.string.valid);
                    BodyFatFemale.this.check = false;
                    return;
                }
                if (BodyFatFemale.this.isKG) {
                    BodyFatFemale.this.weight *= 2.20462d;
                }
                if (BodyFatFemale.this.cms) {
                    BodyFatFemale.this.height *= 0.393701d;
                } else {
                    BodyFatFemale.this.height *= 12.0d;
                    BodyFatFemale.this.height += BodyFatFemale.this.height2;
                }
                if (BodyFatFemale.this.cms2) {
                    BodyFatFemale.this.waist *= 0.393701d;
                }
                if (BodyFatFemale.this.cms3) {
                    BodyFatFemale.this.neck *= 0.393701d;
                }
                if (BodyFatFemale.this.cms4) {
                    BodyFatFemale.this.forearm *= 0.393701d;
                }
                if (BodyFatFemale.this.cms5) {
                    BodyFatFemale.this.wrist *= 0.393701d;
                }
                if (BodyFatFemale.this.cms6) {
                    BodyFatFemale.this.hip *= 0.393701d;
                }
                BodyFatFemale bodyFatFemale7 = BodyFatFemale.this;
                bodyFatFemale7.result3 = (((((bodyFatFemale7.weight * 0.0732d) + 8.987d) + (BodyFatFemale.this.wrist / 3.14d)) - (BodyFatFemale.this.waist * 0.157d)) - (BodyFatFemale.this.hip * 0.249d)) + (BodyFatFemale.this.forearm * 0.434d);
                BodyFatFemale bodyFatFemale8 = BodyFatFemale.this;
                bodyFatFemale8.bf = ((bodyFatFemale8.weight - BodyFatFemale.this.result3) * 100.0d) / BodyFatFemale.this.weight;
                if (BodyFatFemale.this.bf >= 10.0d && BodyFatFemale.this.bf <= 13.0d) {
                    BodyFatFemale bodyFatFemale9 = BodyFatFemale.this;
                    bodyFatFemale9.str_assess = bodyFatFemale9.getResources().getString(R.string.essential);
                } else if (BodyFatFemale.this.bf >= 14.0d && BodyFatFemale.this.bf <= 20.0d) {
                    BodyFatFemale bodyFatFemale10 = BodyFatFemale.this;
                    bodyFatFemale10.str_assess = bodyFatFemale10.getResources().getString(R.string.typicalathlete);
                } else if (BodyFatFemale.this.bf >= 21.0d && BodyFatFemale.this.bf <= 24.0d) {
                    BodyFatFemale bodyFatFemale11 = BodyFatFemale.this;
                    bodyFatFemale11.str_assess = bodyFatFemale11.getResources().getString(R.string.physicallyfit);
                } else if (BodyFatFemale.this.bf < 25.0d || BodyFatFemale.this.bf > 31.0d) {
                    BodyFatFemale bodyFatFemale12 = BodyFatFemale.this;
                    bodyFatFemale12.str_assess = bodyFatFemale12.getResources().getString(R.string.obese);
                } else {
                    BodyFatFemale bodyFatFemale13 = BodyFatFemale.this;
                    bodyFatFemale13.str_assess = bodyFatFemale13.getResources().getString(R.string.acceptable);
                }
                BodyFatFemale bodyFatFemale14 = BodyFatFemale.this;
                bodyFatFemale14.str_bf = bodyFatFemale14.numberFormat.format(BodyFatFemale.this.bf);

                if (mainView != null) {
                    mainView.replaceFragment(
                            BodyFatResult.newInstance(
                                    BodyFatFemale.this.str_bf,
                                    BodyFatFemale.this.str_assess
                            )
                    );
                }
            }
        });
    }

    private void init(View view) {

        if (null != mainView) {
            mainView.setTitleNew(R.string.bodyfat);
        }

        bindHeight(view);
        bindWeight(view);
        tvInputHeight2 = view.findViewById(R.id.tvInputHeight2);
        edHeight2 = view.findViewById(R.id.edHeight2);
        edWaist = view.findViewById(R.id.edWaist);
        edWrist = view.findViewById(R.id.edWrist);
        edNeck = view.findViewById(R.id.edNeck);
        edForearm = view.findViewById(R.id.edForearm);
        edHip = view.findViewById(R.id.edHip);

        //@@@ TextView //tvHeight = view.findViewById(R.id.tvHeight);
        //@@@ //tvWeight = view.findViewById(R.id.tvWeight);
        //@@@ tvWaist = view.findViewById(R.id.tvWaist);
        //@@@ tvWrist = view.findViewById(R.id.tvWrist);
        //@@@ tvNeck = view.findViewById(R.id.tvNeck);
        //@@@ tvForearm = view.findViewById(R.id.tvForearm);
        //@@@ tvHip = view.findViewById(R.id.tvHip);

        tvcm = view.findViewById(R.id.tvcm);
        tvin = view.findViewById(R.id.tvin);
        weightSp = view.findViewById(R.id.weightSp);
        heightSp = view.findViewById(R.id.heightSp);
        waistSp = view.findViewById(R.id.waistSp);
        neckSp = view.findViewById(R.id.neckSp);
        wristSp = view.findViewById(R.id.wristSp);
        BodyFatFemale.this.tvInputHeight2.setVisibility(View.GONE);
        forearmSp = view.findViewById(R.id.forearmSp);
        hipSp = view.findViewById(R.id.hipSp);

        primaryColor = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.bluecolorPrimary);

        ImageView img_waist = view.findViewById(R.id.img_waist);
        ImageView img_height = view.findViewById(R.id.img_height);
        ImageView img_centimeter = view.findViewById(R.id.img_centimeter);
        ImageView img_weight = view.findViewById(R.id.img_weight);
        ImageView img_forearm = view.findViewById(R.id.img_forearm);
        ImageView img_inch = view.findViewById(R.id.img_inch);
        ImageView img_hip = view.findViewById(R.id.img_hip);
        ImageView img_neck = view.findViewById(R.id.img_neck);
        ImageView img_wrist = view.findViewById(R.id.img_wrist);
        M.setThemeColor(primaryColor, img_waist);
        M.setThemeColor(primaryColor, img_height);
        M.setThemeColor(primaryColor, img_centimeter);
        M.setThemeColor(primaryColor, img_neck);
        M.setThemeColor(primaryColor, img_forearm);
        M.setThemeColor(primaryColor, img_hip);
        M.setThemeColor(primaryColor, img_inch);
        M.setThemeColor(primaryColor, img_weight);
        M.setThemeColor(primaryColor, img_wrist);
    }


}
