package com.walhalla.health.HeartRateCalculator;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

public class HeartRateResult extends InnerAbstractFragment {

    private static final String KEY_VALUE_0 = "value0";
    private static final String KEY_VALUE_1 = "value1";
    private static final String KEY_VALUE_2 = "value2";


    private Animation rotate;
    private TextView tvBeats;
    private TextView tvBeats2;
    private TextView tvMhr;
    private TextView tvMhrValue;
    private TextView tvThr;
    private TextView tvThrValue;

    private String value0;
    private String value1;
    private String value2;

    @Override
    protected int aLayout() {
        return R.layout.heartrate_result;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.YellowTheme;
//    }

    public static Fragment newInstance(String value0, String value1, String value2) {
        Fragment fragment = new HeartRateResult();
        Bundle arg = new Bundle();
        arg.putString(KEY_VALUE_0, value0);
        arg.putString(KEY_VALUE_1, value1);
        arg.putString(KEY_VALUE_2, value2);
        fragment.setArguments(arg);
        return fragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            value0 = getArguments().getString(KEY_VALUE_0);
            value1 = getArguments().getString(KEY_VALUE_1);
            value2 = getArguments().getString(KEY_VALUE_2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title(R.string.targethrrate);
        this.tvMhr = view.findViewById(R.id.tvMhr);
        this.tvMhrValue = view.findViewById(R.id.tvMhrValue);
        this.tvThr = view.findViewById(R.id.tvThr);
        this.tvThrValue = view.findViewById(R.id.tvThrValue);
        this.tvBeats = view.findViewById(R.id.tvBeats);
        this.tvBeats2 = view.findViewById(R.id.tvBeats2);

//        ImageView imageView = view.findViewById(R.id.ivRotate);
//        this.rotate = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate);
//        imageView.startAnimation(this.rotate);

        this.tvMhrValue.setText(value0);
        TextView textView = this.tvThrValue;
        String sb = value1
                + " - "
                + value2;
        textView.setText(sb);
    }

}
