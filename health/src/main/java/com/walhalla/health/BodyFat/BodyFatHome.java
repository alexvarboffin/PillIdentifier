package com.walhalla.health.BodyFat;

import static com.walhalla.health.Constant.getShapeDrawable;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.walhalla.health.IdealWeight.GenderAdapter;
import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;
import com.walhalla.health.M;
import com.walhalla.health.util.PrefData;

public class BodyFatHome extends InnerAbstractFragment {

    double age;
    boolean check = false;
    EditText edAge;
    Spinner genderSp;

    public boolean male = true;

    int primaryColor;

    @Override
    protected int aLayout() {
        return R.layout.bodyfathome;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.BlueTheme;
//    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] strArr = {getResources().getString(R.string.male), getResources().getString(R.string.female)};

        view.findViewById(R.id.chart).setOnClickListener(view0 -> {
            if (mainView != null) {
                mainView.replaceFragment(new Chart_Fat());
            }
        });
        Button button = view.findViewById(R.id.calc);
        Button button2 = view.findViewById(R.id.reset);

        init(view);
        edAge.setText(String.valueOf(pref.getAge()));

        Button chart = view.findViewById(R.id.chart);
        button.setBackground(getShapeDrawable(false, primaryColor));
        button2.setBackground(getShapeDrawable(true, primaryColor));
        chart.setBackground(getShapeDrawable(false, primaryColor));

        genderSp.setAdapter(new GenderAdapter(getActivity(), R.layout.spinner_down_blue, strArr));

        genderSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                BodyFatHome.this.male = BodyFatHome.this.genderSp.getSelectedItem().toString().equals(BodyFatHome.this.getResources().getString(R.string.male));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button2.setOnClickListener(view0 -> {
            BodyFatHome.this.edAge.setText("");
            BodyFatHome.this.edAge.requestFocus();
        });
        button.setOnClickListener(view1 -> {
            try {
                try {
                    BodyFatHome.this.age = Double.parseDouble(BodyFatHome.this.edAge.getText().toString());
                } catch (NumberFormatException unused) {
                    BodyFatHome.this.check = true;
                }
                pref.setAge(edAge.getText().toString());
                if (BodyFatHome.this.check) {
                    toast(R.string.valid);
                    BodyFatHome.this.check = false;
                } else if (BodyFatHome.this.male) {
                    if (mainView != null) {
                        mainView.replaceFragment(
                                new BodyFatMale()
                        );
                    }
                } else {
                    if (mainView != null) {
                        mainView.replaceFragment(
                                new BodyFatFemale()
                        );
                    }
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void init(View view) {
        if (null != mainView) {
            mainView.setTitleNew(R.string.bodyfat);
        }
        edAge = view.findViewById(R.id.edAge);

        //@@@ //tvAge = view.findViewById(R.id.tvAge);
        //@@@ tvGender = view.findViewById(R.id.tvGender);

        genderSp = view.findViewById(R.id.genderSp);
        primaryColor = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.bluecolorPrimary);
        ImageView img_age = view.findViewById(R.id.image_age);
        M.setThemeColor(primaryColor, img_age);
    }
}
