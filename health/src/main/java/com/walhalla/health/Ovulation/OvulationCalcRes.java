package com.walhalla.health.Ovulation;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.walhalla.health.Pregnancy.PregnancyCalcRes;
import com.walhalla.health.R;

public class OvulationCalcRes extends InnerAbstractFragment {

    private static final String KEY_VALUE = "value";
    private static final String KEY_VALUE_2 = "value2";

    private Animation rotate;

    private TextView tvEddDesc;
    private TextView tvEddVal;
    private TextView tvEddVal2;


    private String value1;
    private String value2;

//    public static Intent newInstance(Context context, String result, String result2) {
//        Intent intent = new Intent(context, OvulationCalcRes.class);
//        intent.putExtra(KEY_VALUE, result);
//        intent.putExtra(KEY_VALUE_2, result2);
//        return intent;
//    }

    public static Fragment newInstance(@NonNull String value, String value2) {
        Fragment fragment = new PregnancyCalcRes();
        Bundle arg = new Bundle();
        arg.putString(KEY_VALUE, value);
        arg.putString(KEY_VALUE_2, value2);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    protected int aLayout() {
        return R.layout.ovulationres;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.YellowTheme;
//    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

//        Intent intent = getIntent();
//        value1 = intent.getStringExtra(KEY_VALUE);
//        value2 = intent.getStringExtra(KEY_VALUE_2);

        if (getArguments() != null) {
            value1 = getArguments().getString(KEY_VALUE);
            value2 = getArguments().getString(KEY_VALUE_2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        ImageView imageView = view.findViewById(R.id.ivRotate);
        rotate = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate);
        imageView.startAnimation(this.rotate);


        String str = "/";
        int parseInt = 0, parseInt2 = 0;

        String[] split = new String[3];
        String[] split2 = new String[3];

        if (!TextUtils.isEmpty(str)) {

            if (!TextUtils.isEmpty(value1)) {
                split = value1.split(str);
                parseInt = Integer.parseInt(split[0]);
            }
            if (!TextUtils.isEmpty(value2)) {
                split2 = value2.split(str);
                parseInt2 = Integer.parseInt(split2[0]);
            }

        }
        String str2 = " ";
        switch (parseInt) {
            case 1:
                TextView textView = this.tvEddVal;
                String sb = split[1] +
                        str2 +
                        getResources().getString(R.string.jan) +
                        str2 +
                        split[2];
                textView.setText(sb);
                break;
            case 2:
                TextView textView2 = this.tvEddVal;
                String sb2 = split[1] +
                        str2 +
                        getResources().getString(R.string.feb) +
                        str2 +
                        split[2];
                textView2.setText(sb2);
                break;
            case 3:
                TextView textView3 = this.tvEddVal;
                String sb3 = split[1] +
                        str2 +
                        getResources().getString(R.string.mar) +
                        str2 +
                        split[2];
                textView3.setText(sb3);
                break;
            case 4:
                TextView textView4 = this.tvEddVal;
                String sb4 = split[1] +
                        str2 +
                        getResources().getString(R.string.apr) +
                        str2 +
                        split[2];
                textView4.setText(sb4);
                break;
            case 5:
                TextView textView5 = this.tvEddVal;
                String sb5 = split[1] +
                        str2 +
                        getResources().getString(R.string.may) +
                        str2 +
                        split[2];
                textView5.setText(sb5);
                break;
            case 6:
                TextView textView6 = this.tvEddVal;
                String sb6 = split[1] +
                        str2 +
                        getResources().getString(R.string.jun) +
                        str2 +
                        split[2];
                textView6.setText(sb6);
                break;
            case 7:
                TextView textView7 = this.tvEddVal;
                String sb7 = split[1] +
                        str2 +
                        getResources().getString(R.string.jul) +
                        str2 +
                        split[2];
                textView7.setText(sb7);
                break;
            case 8:
                TextView textView8 = this.tvEddVal;
                String sb8 = split[1] +
                        str2 +
                        getResources().getString(R.string.aug) +
                        str2 +
                        split[2];
                textView8.setText(sb8);
                break;
            case 9:
                TextView textView9 = this.tvEddVal;
                String sb9 = split[1] +
                        str2 +
                        getResources().getString(R.string.sep) +
                        str2 +
                        split[2];
                textView9.setText(sb9);
                break;
            case 10:
                TextView textView10 = this.tvEddVal;
                String sb10 = split[1] +
                        str2 +
                        getResources().getString(R.string.oct) +
                        str2 +
                        split[2];
                textView10.setText(sb10);
                break;
            case 11:
                TextView textView11 = this.tvEddVal;
                String sb11 = split[1] +
                        str2 +
                        getResources().getString(R.string.nov) +
                        str2 +
                        split[2];
                textView11.setText(sb11);
                break;
            case 12:
                TextView textView12 = this.tvEddVal;
                String sb12 = split[1] +
                        str2 +
                        getResources().getString(R.string.dec) +
                        str2 +
                        split[2];
                textView12.setText(sb12);
                break;
        }
        switch (parseInt2) {
            case 1:
                TextView textView13 = this.tvEddVal2;
                String sb13 = split2[1] +
                        str2 +
                        getResources().getString(R.string.jan) +
                        str2 +
                        split2[2];
                textView13.setText(sb13);
                return;
            case 2:
                TextView textView14 = this.tvEddVal2;
                String sb14 = split2[1] +
                        str2 +
                        getResources().getString(R.string.feb) +
                        str2 +
                        split2[2];
                textView14.setText(sb14);
                return;
            case 3:
                TextView textView15 = this.tvEddVal2;
                String sb15 = split2[1] +
                        str2 +
                        getResources().getString(R.string.mar) +
                        str2 +
                        split2[2];
                textView15.setText(sb15);
                return;
            case 4:
                TextView textView16 = this.tvEddVal2;
                String sb16 = split2[1] +
                        str2 +
                        getResources().getString(R.string.apr) +
                        str2 +
                        split2[2];
                textView16.setText(sb16);
                return;
            case 5:
                TextView textView17 = this.tvEddVal2;
                String sb17 = split2[1] +
                        str2 +
                        getResources().getString(R.string.may) +
                        str2 +
                        split2[2];
                textView17.setText(sb17);
                return;
            case 6:
                TextView textView18 = this.tvEddVal2;
                String sb18 = split2[1] +
                        str2 +
                        getResources().getString(R.string.jun) +
                        str2 +
                        split2[2];
                textView18.setText(sb18);
                return;
            case 7:
                TextView textView19 = this.tvEddVal2;
                String sb19 = split2[1] +
                        str2 +
                        getResources().getString(R.string.jul) +
                        str2 +
                        split2[2];
                textView19.setText(sb19);
                return;
            case 8:
                TextView textView20 = this.tvEddVal2;
                String sb20 = split2[1] +
                        str2 +
                        getResources().getString(R.string.aug) +
                        str2 +
                        split2[2];
                textView20.setText(sb20);
                return;
            case 9:
                TextView textView21 = this.tvEddVal2;
                String sb21 = split2[1] +
                        str2 +
                        getResources().getString(R.string.sep) +
                        str2 +
                        split2[2];
                textView21.setText(sb21);
                return;
            case 10:
                TextView textView22 = this.tvEddVal2;
                String sb22 = split2[1] +
                        str2 +
                        getResources().getString(R.string.oct) +
                        str2 +
                        split2[2];
                textView22.setText(sb22);
                return;
            case 11:
                TextView textView23 = this.tvEddVal2;
                String sb23 = split2[1] +
                        str2 +
                        getResources().getString(R.string.nov) +
                        str2 +
                        split2[2];
                textView23.setText(sb23);
                return;
            case 12:
                TextView textView24 = this.tvEddVal2;
                String sb24 = split2[1] +
                        str2 +
                        getResources().getString(R.string.dec) +
                        str2 +
                        split2[2];
                textView24.setText(sb24);
                return;
            default:
        }
    }

    private void init(View view) {
//        tvEdd = view.findViewById(R.id.tvEdd);
//        tvEddVal3 = view.findViewById(R.id.tvEddVal3);

        tvEddVal = view.findViewById(R.id.tvEddVal);
        tvEddVal2 = view.findViewById(R.id.tvEddVal2);
        tvEddDesc = view.findViewById(R.id.tvEddDesc);

        if (mainView != null) {
            mainView.setTitleNew(R.string.ovulation);
        }
        tvEddDesc.setBackground(Constant.getShapeDrawable(true, ContextCompat.getColor(
                getActivity().getApplicationContext(), R.color.yellowcolorPrimary)));
    }

}
