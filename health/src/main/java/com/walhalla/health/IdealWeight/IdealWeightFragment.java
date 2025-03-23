package com.walhalla.health.IdealWeight;

import static com.walhalla.health.Constant.getShapeDrawable;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.walhalla.health.R;
import com.walhalla.health.M;
import com.walhalla.health.databinding.IdealweightcalcBinding;
import com.walhalla.health.util.PrefData;
import com.walhalla.ui.DLog;

import java.text.NumberFormat;

public class IdealWeightFragment extends Fragment {

    double age;
    boolean check = false;
    public boolean cms = true;


    private double height;
    private double height2;
    public boolean male = true;

    NumberFormat numberFormat;
    String str_id;
    String str_id2;
    int primaryColor;

    public boolean toast = false;
    private TextView tvAge;


    private IdealweightcalcBinding binding;

    protected InnerAbstractFragment.FInnerCallback mainView;
    protected PrefData pref;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new PrefData(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = IdealweightcalcBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] strArr = {getResources().getString(R.string.male), getResources().getString(R.string.female)};
        String[] strArr2 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.feets)};

        init(view);
        binding.edAge.setText(String.valueOf(pref.getAge()));
        Button chart = view.findViewById(R.id.chart);
        chart.setOnClickListener(view0 -> {
            go((new ChartIdeal()));
        });

        Button button2 = view.findViewById(R.id.reset);

        binding.calc.setBackground(getShapeDrawable(false, primaryColor));
        button2.setBackground(getShapeDrawable(true, primaryColor));
        chart.setBackground(getShapeDrawable(false, primaryColor));

        binding.edHeight2.setEnabled(false);
        binding.edHeight.setEnabled(true);
        binding.tvcm.setText(getResources().getString(R.string.cm));
        binding.tvin.setText("");
        binding.tvInputHeight2.setVisibility(View.GONE);
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        binding.genderSp.setAdapter(new GenderAdapter(getActivity(), R.layout.spinner_down_blue, strArr));
        binding.heightSp.setAdapter(new HeightAdapter(getActivity(), R.layout.spinner_down_blue, strArr2));


        binding.heightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (IdealWeightFragment.this.binding.heightSp.getSelectedItem().toString().equals(IdealWeightFragment.this.getResources().getString(R.string.centimeters))) {
                    IdealWeightFragment aaa = IdealWeightFragment.this;
                    aaa.cms = true;
                    aaa.binding.edHeight.setEnabled(true);
                    aaa.binding.edHeight2.setEnabled(false);
                    aaa.binding.tvInputHeight2.setVisibility(View.GONE);
                    aaa.binding.tvcm.setText(IdealWeightFragment.this.getResources().getString(R.string.cm));
                    aaa.binding.tvin.setText("");
                    return;
                }
                IdealWeightFragment idealWeightCalc2 = IdealWeightFragment.this;
                idealWeightCalc2.cms = false;
                idealWeightCalc2.binding.edHeight.setEnabled(true);
                IdealWeightFragment.this.binding.edHeight2.setEnabled(true);
                IdealWeightFragment.this.binding.tvcm.setText(IdealWeightFragment.this.getResources().getString(R.string.ft));
                IdealWeightFragment.this.binding.tvin.setText(IdealWeightFragment.this.getResources().getString(R.string.in));
                IdealWeightFragment.this.binding.tvInputHeight2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.genderSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                IdealWeightFragment.this.male = IdealWeightFragment.this.binding.genderSp.getSelectedItem().toString().equals(IdealWeightFragment.this.getResources().getString(R.string.male));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.calc.setOnClickListener(v -> calculate());
        button2.setOnClickListener(view0 -> {
            String str = "";
            IdealWeightFragment.this.binding.edHeight.setText(str);
            IdealWeightFragment.this.binding.edAge.setText(str);
            IdealWeightFragment.this.binding.edHeight2.setText(str);
            IdealWeightFragment.this.binding.edAge.requestFocus();
        });

    }

    private void init(View view) {
        if (null != mainView) {
            mainView.setTitleNew(R.string.idealweight);
        }
        bindHeight(view);
        binding.tvInputHeight2.setVisibility(View.GONE);
        primaryColor = ContextCompat.getColor(
                getActivity().getApplicationContext(), R.color.orangecolorPrimary);


        M.setThemeColor(primaryColor, binding.imageAge);
        M.setThemeColor(primaryColor, binding.imgHeight);
        M.setThemeColor(primaryColor, binding.imgCentimeter);
        M.setThemeColor(primaryColor, binding.imgInch);
    }


    public void calculate() {
        try {
            try {
                this.height = Double.parseDouble(this.binding.edHeight.getText().toString());
            } catch (NumberFormatException unused) {
                this.check = true;
            }
            try {
                this.age = Double.parseDouble(this.binding.edAge.getText().toString());

            } catch (NumberFormatException unused2) {
                this.check = true;
            }
            try {
                this.height2 = Double.parseDouble(this.binding.edHeight2.getText().toString());
            } catch (NumberFormatException unused3) {
                this.height2 = 0.0d;
            }
            if (this.check) {
                toast(R.string.valid);
                this.check = false;
                return;
            } else if (this.cms) {
                if (this.height < 153.0d) {
                    toast(R.string.entercm);
                    this.toast = true;
                    return;
                } else {
                    this.height *= 0.393701d;
                    this.height -= 60.0d;
                }
            } else if (this.height < 5.0d) {
                toast(R.string.enterfeet);
                this.toast = true;
                return;
            } else {
                this.height *= 12.0d;
                this.height += this.height2;
                this.height -= 60.0d;
            }

            if (this.toast) {
                this.toast = false;
            }

            double thisid = 0.0d;
            if (this.male) {
                thisid = (this.height * 1.9d) + 52.0d;
            } else {
                thisid = (this.height * 1.7d) + 49.0d;
            }
            pref.setAge(binding.edAge.getText().toString());

            this.str_id = this.numberFormat.format(thisid);
            thisid *= 2.20462d;
            this.str_id2 = this.numberFormat.format(thisid);


            if (mainView != null) {
                Fragment jj = IdealWeightResult.newInstance(this.str_id, this.str_id2);
                mainView.replaceFragment(jj);
            }
        } catch (Resources.NotFoundException e) {
            DLog.handleException(e);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof InnerAbstractFragment.FInnerCallback) {
            this.mainView = (InnerAbstractFragment.FInnerCallback) context;
        } else {
            throw new RuntimeException(context + " must implement FInnerCallback");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mainView = null;
    }

    protected void go(Fragment fragment) {
        if (mainView != null) {
            mainView.replaceFragment(fragment);
        }
    }

    protected void title(int res) {
        if (mainView != null) {
            mainView.setTitleNew(res);
        }
    }

    public void toast(int string) {
        if (mainView != null) {
            mainView.snackbar(string);
        }
    }

    protected void bindHeight(View view) {
        int aa = pref.getHeight();
        if (aa > 0) {
            binding.edHeight.setText("" + aa);
        }
    }

//    protected void bindWeight(View view) {
//        int aa = pref.getWeight();
//        if (aa > 0) {
//            binding.edWeight.setText("" + aa);
//        }
//    }
}
