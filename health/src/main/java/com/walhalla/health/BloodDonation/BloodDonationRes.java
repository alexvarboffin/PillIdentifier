package com.walhalla.health.BloodDonation;


import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.walhalla.health.Constant;
import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

public class BloodDonationRes extends InnerAbstractFragment {

    private static final String ARG_VALUE = "value";
    
    Animation rotate;
    TextView tvEdd;
    TextView tvEddDesc;
    TextView tvEddVal;
    TextView tvEddVal2;
    
    private String string;


    @Override
    protected int aLayout() {
        return R.layout.blood_donate_res;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.PinkTheme;
//    }
    public static Fragment newInstance(String value) {
        Fragment fragment = new BloodDonationRes();
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
        rotate = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate);
        imageView.startAnimation(this.rotate);
//        Typeface createFromAsset = Typeface.createFromAsset(getAssets(), "fonts/popind_regular_regular.ttf");
//        tvEdd.setTypeface(createFromAsset);
//        tvEddVal.setTypeface(createFromAsset);
//        tvEddVal2.setTypeface(createFromAsset);
//        tvEddDesc.setTypeface(createFromAsset);

        String[] split = new String[0];
        if (string != null) {
            split = string.split("/");
        }
        String str = " ";
        switch (Integer.parseInt(split[0])) {
            case 1:
                TextView textView = this.tvEddVal2;
                StringBuilder sb = new StringBuilder();
                sb.append(split[1]);
                sb.append(str);
                sb.append(getResources().getString(R.string.jan));
                sb.append(str);
                sb.append(split[2]);
                textView.setText(sb.toString());
                break;
            case 2:
                TextView textView2 = this.tvEddVal2;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(split[1]);
                sb2.append(str);
                sb2.append(getResources().getString(R.string.feb));
                sb2.append(str);
                sb2.append(split[2]);
                textView2.setText(sb2.toString());
                break;
            case 3:
                TextView textView3 = this.tvEddVal2;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(split[1]);
                sb3.append(str);
                sb3.append(getResources().getString(R.string.mar));
                sb3.append(str);
                sb3.append(split[2]);
                textView3.setText(sb3.toString());
                break;
            case 4:
                TextView textView4 = this.tvEddVal2;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(split[1]);
                sb4.append(str);
                sb4.append(getResources().getString(R.string.apr));
                sb4.append(str);
                sb4.append(split[2]);
                textView4.setText(sb4.toString());
                break;
            case 5:
                TextView textView5 = this.tvEddVal2;
                StringBuilder sb5 = new StringBuilder();
                sb5.append(split[1]);
                sb5.append(str);
                sb5.append(getResources().getString(R.string.may));
                sb5.append(str);
                sb5.append(split[2]);
                textView5.setText(sb5.toString());
                break;
            case 6:
                TextView textView6 = this.tvEddVal2;
                StringBuilder sb6 = new StringBuilder();
                sb6.append(split[1]);
                sb6.append(str);
                sb6.append(getResources().getString(R.string.jun));
                sb6.append(str);
                sb6.append(split[2]);
                textView6.setText(sb6.toString());
                break;
            case 7:
                TextView textView7 = this.tvEddVal2;
                StringBuilder sb7 = new StringBuilder();
                sb7.append(split[1]);
                sb7.append(str);
                sb7.append(getResources().getString(R.string.jul));
                sb7.append(str);
                sb7.append(split[2]);
                textView7.setText(sb7.toString());
                break;
            case 8:
                TextView textView8 = this.tvEddVal2;
                StringBuilder sb8 = new StringBuilder();
                sb8.append(split[1]);
                sb8.append(str);
                sb8.append(getResources().getString(R.string.aug));
                sb8.append(str);
                sb8.append(split[2]);
                textView8.setText(sb8.toString());
                break;
            case 9:
                TextView textView9 = this.tvEddVal2;
                StringBuilder sb9 = new StringBuilder();
                sb9.append(split[1]);
                sb9.append(str);
                sb9.append(getResources().getString(R.string.sep));
                sb9.append(str);
                sb9.append(split[2]);
                textView9.setText(sb9.toString());
                break;
            case 10:
                TextView textView10 = this.tvEddVal2;
                StringBuilder sb10 = new StringBuilder();
                sb10.append(split[1]);
                sb10.append(str);
                sb10.append(getResources().getString(R.string.oct));
                sb10.append(str);
                sb10.append(split[2]);
                textView10.setText(sb10.toString());
                break;
            case 11:
                TextView textView11 = this.tvEddVal2;
                StringBuilder sb11 = new StringBuilder();
                sb11.append(split[1]);
                sb11.append(str);
                sb11.append(getResources().getString(R.string.nov));
                sb11.append(str);
                sb11.append(split[2]);
                textView11.setText(sb11.toString());
                break;
            case 12:
                TextView textView12 = this.tvEddVal2;
                StringBuilder sb12 = new StringBuilder();
                sb12.append(split[1]);
                sb12.append(str);
                sb12.append(getResources().getString(R.string.dec));
                sb12.append(str);
                sb12.append(split[2]);
                textView12.setText(sb12.toString());
                break;
        }
        this.tvEddVal.setText(string);
    }


    private void init(View view) {
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        setTitle(getResources().getString(R.string.blood_donate));

        if (null != mainView) {
            mainView.setTitleNew(R.string.blood_donate);
        }

        tvEdd = view.findViewById(R.id.tvEdd);
        tvEddVal = view.findViewById(R.id.tvEddVal);
        tvEddVal2 = view.findViewById(R.id.tvEddVal2);
        tvEddDesc = view.findViewById(R.id.tvEddDesc);

        tvEddDesc.setBackground(Constant.getShapeDrawable(true,
                ContextCompat.getColor(getActivity().getApplicationContext(), R.color.pinkcolorPrimary)));
    }
}
