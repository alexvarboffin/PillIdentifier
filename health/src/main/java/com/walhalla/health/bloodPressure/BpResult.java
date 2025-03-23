package com.walhalla.health.bloodPressure;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

public class BpResult extends InnerAbstractFragment {

    private static final String ARG_VALUE = "value";

    Button chart;
    Animation rotate;
    TextView tvDwi;
    //    TextView tvDwiDesc;
    TextView tvDwiVal;
    TextView tvGlasses;

    @Override
    protected int aLayout() {
        return R.layout.bpresult;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.OrangeTheme;
//    }

    private String string;

    public static Fragment newInstance(@NonNull String value) {
        Fragment fragment = new BpResult();
        Bundle arg = new Bundle();
        arg.putString(ARG_VALUE, value);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            string = getArguments().getString(ARG_VALUE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        ImageView imageView = view.findViewById(R.id.ivRotate);
        rotate = AnimationUtils.loadAnimation(getContext().getApplicationContext(), R.anim.rotate);
        imageView.startAnimation(this.rotate);
        tvDwiVal.setText(string);
    }

    private void init(View view) {
        if (null != mainView) {
            mainView.setTitleNew(R.string.blood_pressure);
        }
        tvDwi = view.findViewById(R.id.tvDwi);
        tvDwiVal = view.findViewById(R.id.tvDwiVal);
//        tvDwiDesc = view.findViewById(R.id.tvDwiDesc);
        tvGlasses = view.findViewById(R.id.tvGlasses);

//        tvDwiDesc.setBackground(getShapeDrawble(true, ContextCompat.getColor(getApplicationContext(), R.color.orangecolorPrimary)));
    }

//    @Override
//    public void onBackPressed() {
//        finish();
//    }
}
