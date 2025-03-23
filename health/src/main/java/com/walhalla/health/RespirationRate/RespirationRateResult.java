package com.walhalla.health.RespirationRate;

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

public class RespirationRateResult extends InnerAbstractFragment {

    private static final String ARG_VALUE = "value";

    private Button chart;
    private Animation rotate;
    private TextView tvDwi;
    //private     TextView tvDwiDesc;
    private TextView tvDwiVal;

    private TextView tvGlasses;
    private String value;


    @Override
    protected int aLayout() {
        return R.layout.respirationrateresult;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.CyanTheme;
//    }

    public static Fragment newInstance(@NonNull String value) {
        Fragment fragment = new RespirationRateResult();
        Bundle arg = new Bundle();
        arg.putString(ARG_VALUE, value);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            value = getArguments().getString(ARG_VALUE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        ImageView imageView = view.findViewById(R.id.ivRotate);
        rotate = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate);
        imageView.startAnimation(this.rotate);
        tvDwiVal.setText(value);
    }

    private void init(View view) {
        title(R.string.title_breath_count);
        tvDwi = view.findViewById(R.id.tvDwi);
        tvDwiVal = view.findViewById(R.id.tvDwiVal);
//        tvDwiDesc = view.findViewById(R.id.tvDwiDesc);
        tvGlasses = view.findViewById(R.id.tvGlasses);

//        tvDwiDesc.setBackground(getShapeDrawble(true, ContextCompat.getColor(getApplicationContext(), R.color.orangecolorPrimary)));
    }
}
