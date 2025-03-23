package com.walhalla.health.BloodPressure;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.walhalla.health.Constant;
import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

public class BloodPressureFragment extends InnerAbstractFragment {

    /*
     * lower bp=1.5 or lower
     *
     *
     * */
    int primaryColor;
    EditText edSystolicBp, edDiastolicBp;
    Button calc, reset;
    TextInputLayout txtInputStstabolic;

    @Override
    protected int aLayout() {
        return R.layout.activity_bp;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.OrangeTheme;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        reset.setOnClickListener(v -> {
            edDiastolicBp.setText("");
            edSystolicBp.setText("");
        });

        calc.setOnClickListener(v -> {
            if (TextUtils.isEmpty(edSystolicBp.getText().toString())) {
//                    txtInputStstabolic.setError(getResources().getString(R.string.valid));
                edSystolicBp.setError(getResources().getString(R.string.valid));

                return;
            } else if (TextUtils.isEmpty(edDiastolicBp.getText().toString())) {
                edDiastolicBp.setError(getResources().getString(R.string.valid));
                return;
            } else {
                float sBp = Float.parseFloat(edSystolicBp.getText().toString());
                float dBp = Float.parseFloat(edDiastolicBp.getText().toString());
                String result = "";
                if (sBp > 180 || dBp > 110) {
                    result = getResources().getString(R.string.hypertensive_crisis);
                } else if (sBp >= 160 || dBp >= 100) {
                    result = getResources().getString(R.string.high_bp_stage2);
                } else if (sBp > 140 || dBp > 90) {
                    result = getResources().getString(R.string.high_bp_stage1);
                } else if (sBp > 120 || dBp > 80) {
                    result = getResources().getString(R.string.prehypertension);
                } else if (sBp > 80 && dBp > 60) {
                    result = getResources().getString(R.string.normal_bp);
                } else {
                    result = getResources().getString(R.string.low_bp);
                }

                if (mainView != null) {
                    mainView.replaceFragment(BpResult.newInstance(result));
                }

//                    if (sBp < 80 && dBp < 60) {
//                        result = getResources().getString(R.string.low_bp);
//                    }
//                    else if ()

            }
        });
    }

    private void init(View view) {
        primaryColor = ContextCompat.getColor(getContext().getApplicationContext(), R.color.orangecolorPrimary);
        edSystolicBp = view.findViewById(R.id.edSystolicBp);
        edDiastolicBp = view.findViewById(R.id.edDiastolicBp);

        reset = view.findViewById(R.id.reset);
        calc = view.findViewById(R.id.calc);
        txtInputStstabolic = view.findViewById(R.id.txtInputStstabolic);

        //white btn
        reset.setBackground(Constant.getShapeDrawable(true, primaryColor));
        calc.setBackground(Constant.getShapeDrawable(false, primaryColor));

    }
}
