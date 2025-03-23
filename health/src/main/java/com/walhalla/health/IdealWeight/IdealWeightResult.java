package com.walhalla.health.IdealWeight;

import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;
import com.walhalla.health.R;
import com.walhalla.health.activity.base.InnerAdActivity;
import com.walhalla.ui.DLog;

public class IdealWeightResult extends InnerAbstractFragment {

    private static final String ARG_VALUE = "value";
    private static final String ARG_VALUE2 = "value2";


    private String string;
    private String string2;

    public static Fragment newInstance(@NonNull String value, @NonNull String value2) {
        DLog.d("@@@" + value + ", " + value2);

        Fragment fragment = new IdealWeightResult();
        Bundle arg = new Bundle();
        arg.putString(ARG_VALUE, value);
        arg.putString(ARG_VALUE2, value2);
        fragment.setArguments(arg);
        return fragment;
    }

    Animation rotate;
    TextView tvIdealWeight;
    TextView tvIdealWeightVal;

    @Override
    protected int aLayout() {
        return R.layout.idealweightresult;
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
            string2 = getArguments().getString(ARG_VALUE2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (null != mainView) {
            mainView.setTitleNew(R.string.idealweight);
        }
        this.tvIdealWeight = view.findViewById(R.id.tvIdealWeight);
        this.tvIdealWeightVal = view.findViewById(R.id.tvIdealWeightVal);

        //ImageView imageView = view.findViewById(R.id.ivRotate);
        //this.rotate = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate);
        //imageView.startAnimation(this.rotate);


        String str = " ";
        String sb = string +
                str +
                getResources().getString(R.string.kg) +
                " / " +
                string2 +
                str +
                getResources().getString(R.string.lbs);
        this.tvIdealWeightVal.setText(sb);
    }

}
