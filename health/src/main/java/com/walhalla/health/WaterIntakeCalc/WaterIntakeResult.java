package com.walhalla.health.WaterIntakeCalc;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.walhalla.health.Constant;
import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

public class WaterIntakeResult extends InnerAbstractFragment {

    private static final String ARG_VALUE = "value";

    //private Button chart;
    //private Animation rotate;
    private TextView tvDwiVal;
    private String string;

    public static Fragment newInstance(@NonNull String value) {
        Fragment fragment = new WaterIntakeResult();
        Bundle arg = new Bundle();
        arg.putString(ARG_VALUE, value);
        fragment.setArguments(arg);
        return fragment;
    }


    @Override
    protected int aLayout() {
        return R.layout.waterintakeresult;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.OrangeTheme;
//    }

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
        //ImageView imageView = view.findViewById(R.id.ivRotate);
        //rotate = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate);
        //imageView.startAnimation(this.rotate);
        tvDwiVal.setText(string);
    }

    private void init(View view) {

        if (null != mainView) {
            mainView.setTitleNew(R.string.waterintake);
        }

        TextView tvDwi = view.findViewById(R.id.tvDwi);
        tvDwiVal = view.findViewById(R.id.tvDwiVal);
        TextView tvDwiDesc = view.findViewById(R.id.tvDwiDesc);
        TextView tvGlasses = view.findViewById(R.id.tvGlasses);
        tvDwiDesc.setBackground(Constant.getShapeDrawable(true, ContextCompat.getColor(getActivity().getApplicationContext(), R.color.orangecolorPrimary)));
    }
}
