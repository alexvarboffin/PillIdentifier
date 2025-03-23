package com.walhalla.health.CalorieCalculator;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.walhalla.health.Constant;
import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

public class Calorie_result extends InnerAbstractFragment {

    private static final String ARG_VALUE = "value";

    private TextView header1;
    private TextView header2;
    private TextView header3;
    private Animation rotate;
    private TextView row11;
    private TextView row12;
    private TextView row13;
    private TextView row21;
    private TextView row22;
    private TextView row23;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tvCalories;
    private TextView tvTdee;
    private TextView tvTdeeValue;

    private String string;


    public static Fragment newInstance(@NonNull String value) {
        Fragment fragment = new Calorie_result();
        Bundle arg = new Bundle();
        arg.putString(ARG_VALUE, value);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    protected int aLayout() {
        return R.layout.calorie_result;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.GrayTheme;
//    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            string = getArguments().getString(ARG_VALUE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        ImageView imageView = view.findViewById(R.id.ivRotate);
        rotate = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate);
        imageView.startAnimation(rotate);
        tvTdeeValue.setText(string);
    }
    
    private void init(View view) {
        if (null != mainView) {
            mainView.setTitleNew(R.string.calories);
        }
        tvTdee = view.findViewById(R.id.tvTdee);
        tvTdeeValue = view.findViewById(R.id.tvTdeeValue);
        tvCalories = view.findViewById(R.id.tvCalories);
        header1 = view.findViewById(R.id.header1);
        header2 = view.findViewById(R.id.header2);
        header3 = view.findViewById(R.id.header3);
        row11 = view.findViewById(R.id.row11);
        row12 = view.findViewById(R.id.row12);
        row13 = view.findViewById(R.id.row13);
        row21 = view.findViewById(R.id.row21);
        row22 = view.findViewById(R.id.row22);
        row23 = view.findViewById(R.id.row23);
        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
        tv3 = view.findViewById(R.id.tv3);
        tv4 = view.findViewById(R.id.tv4);

        view.findViewById(R.id.layout)
                .setBackground(Constant.getShapeDrawable(true,
                        ContextCompat.getColor(getActivity().getApplicationContext(), R.color.graycolorPrimary)));

    }

}
