package com.walhalla.health.BloodVolumeCalc;

import static com.walhalla.health.Constant.getShapeDrawable;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.walhalla.health.BodyMassIndex.WeightAdapter;
import com.walhalla.health.IdealWeight.GenderAdapter;
import com.walhalla.health.IdealWeight.HeightAdapter;
import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;
import com.walhalla.health.M;

import java.text.NumberFormat;

public class BloodVolumeCalcFragment extends InnerAbstractFragment implements AdapterView.OnItemSelectedListener {

    private double bloodVol;
    private boolean check = false;
    public boolean cms = true;
    
    private EditText edHeight2;
    
    private Spinner genderSp;

    private double height;
    private double height2;
    private Spinner heightSp;


    public boolean isKG = true;
    private LinearLayout tvInputHeight2;
    public boolean male = true;

    private NumberFormat numberFormat;
    private String str_bv;

    private TextView tvcm;
    private TextView tvin;
    private double weight;
    private EditText edAge;
    private Spinner weightSp;

    private LinearLayout lnrAge;


    int primaryColor;


    @Override
    protected int aLayout() {
        return R.layout.bloodvolume;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.CyanTheme;
//    }
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        String[] strArr = {getResources().getString(R.string.male), getResources().getString(R.string.female)};
        String[] strArr2 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.feets)};
        String[] strArr3 = {getResources().getString(R.string.kilograms), getResources().getString(R.string.pounds)};

        Button button = view.findViewById(R.id.calc);
        Button button2 = view.findViewById(R.id.reset);

        if (null != mainView) {
            mainView.setTitleNew(R.string.bloodvol);
        }

        lnrAge = view.findViewById(R.id.lnrAge);
        edAge = view.findViewById(R.id.edAge);
        bindHeight(view);
        bindWeight(view);

        edHeight2 = view.findViewById(R.id.edHeight2);
        tvInputHeight2 = view.findViewById(R.id.tvInputHeight2);

        //@@@ TextView//tvHeight = view.findViewById(R.id.tvHeight);
        //@@@ TextView//tvWeight = view.findViewById(R.id.tvWeight);
        //@@@ TextViewtvGender = view.findViewById(R.id.tvGender);

        tvcm = view.findViewById(R.id.tvcm);
        tvin = view.findViewById(R.id.tvin);
        weightSp = view.findViewById(R.id.weightSp);
        heightSp = view.findViewById(R.id.heightSp);
        genderSp = view.findViewById(R.id.genderSp);

        primaryColor = ContextCompat.getColor(getContext().getApplicationContext(), R.color.cyancolorPrimary);
        ImageView img_age = view.findViewById(R.id.image_age);
        ImageView img_height = view.findViewById(R.id.img_height);
        ImageView img_centimeter = view.findViewById(R.id.img_centimeter);
        ImageView img_inch = view.findViewById(R.id.img_inch);
        ImageView img_weight = view.findViewById(R.id.img_weight);
        M.setThemeColor(primaryColor, img_age);
        M.setThemeColor(primaryColor, img_height);
        M.setThemeColor(primaryColor, img_centimeter);
        M.setThemeColor(primaryColor, img_inch);
        M.setThemeColor(primaryColor, img_weight);

        lnrAge.setVisibility(View.GONE);
//        edAge.setText(String.valueOf(PrefData.getAge(getApplicationContext())));


        button.setBackground(getShapeDrawable(false, primaryColor));
        button2.setBackground(getShapeDrawable(true, primaryColor));
        edHeight2.setEnabled(false);
        edHeight.setEnabled(true);
        tvcm.setText(getResources().getString(R.string.cm));
        tvin.setText("");
        tvInputHeight2.setVisibility(View.GONE);
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        genderSp.setAdapter(new GenderAdapter(getActivity(), R.layout.spinner_down_blue, strArr));
        heightSp.setAdapter(new HeightAdapter(getActivity(), R.layout.spinner_down_blue, strArr2));
        weightSp.setAdapter(new WeightAdapter(getActivity(), R.layout.spinner_down_blue, strArr3));


        heightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (heightSp.getSelectedItem().toString().equals(
                        getActivity().getResources().getString(R.string.centimeters))) {
                    cms = true;
                    edHeight.setEnabled(true);
                    edHeight2.setEnabled(false);
                    tvInputHeight2.setVisibility(View.GONE);
                    tvcm.setText(getActivity().getResources().getString(R.string.cm));
                    tvin.setText("");
                    tvInputHeight2.setVisibility(View.GONE);
                    return;
                }
                cms = false;
                tvInputHeight2.setVisibility(View.VISIBLE);
                edHeight.setEnabled(true);
                edHeight2.setEnabled(true);
                tvcm.setText(getActivity().getResources().getString(R.string.ft));
                tvin.setText(getActivity().getResources().getString(R.string.in));
                edHeight2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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


        button2.setOnClickListener(view0 -> {
            String str = "";
            this.edHeight.setText(str);
            this.edWeight.setText(str);
            this.edHeight2.setText(str);
            this.edHeight.requestFocus();
        });
        button.setOnClickListener(view0 -> {
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
                this.height2 = Double.parseDouble(this.edHeight2.getText().toString());
            } catch (NumberFormatException unused3) {
                this.height2 = 0.0d;
            }
            if (this.check) {
                toast(R.string.valid);
                this.check = false;
                return;
            }
            if (this.cms) {
                this.height /= 100.0d;
            } else {
                this.height *= 12.0d;
                this.height += this.height2;
                this.height *= 2.54d;
                this.height /= 100.0d;
            }
            if (!this.isKG) {
                this.weight *= 0.453592d;
            }
            if (this.male) {
                this.weight *= 0.03219d;
                this.weight += 0.6041d;
                this.height = this.height * this.height * this.height;
                this.height *= 0.3669d;
            } else {
                this.weight *= 0.03308d;
                this.weight += 0.1833d;
                this.height = this.height * this.height * this.height;
                this.height *= 0.3561d;
            }

            this.bloodVol = this.weight + this.height;
            this.str_bv = this.numberFormat.format(this.bloodVol);

            if (mainView != null) {
                mainView.replaceFragment(
                        BloodVolResult.newInstance(this.str_bv)
                );
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        isKG = parent.getSelectedItem().toString().equals(
                getActivity().getResources().getString(R.string.kilograms));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
