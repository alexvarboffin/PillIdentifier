package com.walhalla.health.Ovulation;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.walhalla.health.Constant;
import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class OvulationCalc extends InnerAbstractFragment {

    private int anInt = 0;
    private String result = "";
    private int primaryColor;
    private String result2;
    private TextView tvdatechosen;
    private TextView tvdatechosenval;
    private Toolbar toolbar;

    @Override
    protected int aLayout() {
        return R.layout.ovulation;
    }

    //    @Override
//    protected int aTheme() {
//        return R.style.YellowTheme;
//    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.chart).setOnClickListener(
                view9 -> {
                    if (mainView != null) {
                        mainView.replaceFragment(new ChartOvu());
                    }
                });

        Button button = view.findViewById(R.id.calc);
        Button button2 = view.findViewById(R.id.reset);
        Button chart = view.findViewById(R.id.chart);
        init(view);
        button.setBackground(Constant.getShapeDrawable(false, primaryColor));
        button2.setBackground(Constant.getShapeDrawable(false, primaryColor));
        chart.setBackground(Constant.getShapeDrawable(false, primaryColor));

        final Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.YEAR);
        int i2 = instance.get(Calendar.MONTH);
        int i3 = instance.get(Calendar.DATE);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String[] split = simpleDateFormat.format(instance.getTime()).split("/");
        String str = " ";
        switch (Integer.parseInt(split[0])) {
            case 1:
                TextView textView = this.tvdatechosenval;
                String sb = split[1] +
                        str +
                        getResources().getString(R.string.jan) +
                        str +
                        split[2];
                textView.setText(sb);
                break;
            case 2:
                TextView textView2 = this.tvdatechosenval;
                String sb2 = split[1] +
                        str +
                        getResources().getString(R.string.feb) +
                        str +
                        split[2];
                textView2.setText(sb2);
                break;
            case 3:
                TextView textView3 = this.tvdatechosenval;
                String sb3 = split[1] +
                        str +
                        getResources().getString(R.string.mar) +
                        str +
                        split[2];
                textView3.setText(sb3);
                break;
            case 4:
                TextView textView4 = this.tvdatechosenval;
                String sb4 = split[1] +
                        str +
                        getResources().getString(R.string.apr) +
                        str +
                        split[2];
                textView4.setText(sb4);
                break;
            case 5:
                TextView textView5 = this.tvdatechosenval;
                String sb5 = split[1] +
                        str +
                        getResources().getString(R.string.may) +
                        str +
                        split[2];
                textView5.setText(sb5);
                break;
            case 6:
                TextView textView6 = this.tvdatechosenval;
                String sb6 = split[1] +
                        str +
                        getResources().getString(R.string.jun) +
                        str +
                        split[2];
                textView6.setText(sb6);
                break;
            case 7:
                TextView textView7 = this.tvdatechosenval;
                String sb7 = split[1] +
                        str +
                        getResources().getString(R.string.jul) +
                        str +
                        split[2];
                textView7.setText(sb7);
                break;
            case 8:
                TextView textView8 = this.tvdatechosenval;
                String sb8 = split[1] +
                        str +
                        getResources().getString(R.string.aug) +
                        str +
                        split[2];
                textView8.setText(sb8);
                break;
            case 9:
                TextView textView9 = this.tvdatechosenval;
                String sb9 = split[1] +
                        str +
                        getResources().getString(R.string.sep) +
                        str +
                        split[2];
                textView9.setText(sb9);
                break;
            case 10:
                TextView textView10 = this.tvdatechosenval;
                String sb10 = split[1] +
                        str +
                        getResources().getString(R.string.oct) +
                        str +
                        split[2];
                textView10.setText(sb10);
                break;
            case 11:
                TextView textView11 = this.tvdatechosenval;
                String sb11 = split[1] +
                        str +
                        getResources().getString(R.string.nov) +
                        str +
                        split[2];
                textView11.setText(sb11);
                break;
            case 12:
                TextView textView12 = this.tvdatechosenval;
                String sb12 = split[1] +
                        str +
                        getResources().getString(R.string.dec) +
                        str +
                        split[2];
                textView12.setText(sb12);
                break;
        }

        final DatePickerDialog.OnDateSetListener r10 = (datePicker, i1, i21, i31) -> {
            instance.set(Calendar.YEAR, i1);
            instance.set(Calendar.MONTH, i21);
            instance.set(Calendar.DATE, i31);
            String[] split1 = simpleDateFormat.format(instance.getTime()).split("/");
            String str1 = " ";
            switch (Integer.parseInt(split1[0])) {
                case 1:
                    TextView textView = OvulationCalc.this.tvdatechosenval;
                    String sb = split1[1] +
                            str1 +
                            OvulationCalc.this.getResources().getString(R.string.jan) +
                            str1 +
                            split1[2];
                    textView.setText(sb);
                    break;
                case 2:
                    TextView textView2 = OvulationCalc.this.tvdatechosenval;
                    String sb2 = split1[1] +
                            str1 +
                            OvulationCalc.this.getResources().getString(R.string.feb) +
                            str1 +
                            split1[2];
                    textView2.setText(sb2);
                    break;
                case 3:
                    TextView textView3 = OvulationCalc.this.tvdatechosenval;
                    String sb3 = split1[1] +
                            str1 +
                            OvulationCalc.this.getResources().getString(R.string.mar) +
                            str1 +
                            split1[2];
                    textView3.setText(sb3);
                    break;
                case 4:
                    TextView textView4 = OvulationCalc.this.tvdatechosenval;
                    String sb4 = split1[1] +
                            str1 +
                            OvulationCalc.this.getResources().getString(R.string.apr) +
                            str1 +
                            split1[2];
                    textView4.setText(sb4);
                    break;
                case 5:
                    TextView textView5 = OvulationCalc.this.tvdatechosenval;
                    String sb5 = split1[1] +
                            str1 +
                            OvulationCalc.this.getResources().getString(R.string.may) +
                            str1 +
                            split1[2];
                    textView5.setText(sb5);
                    break;
                case 6:
                    TextView textView6 = OvulationCalc.this.tvdatechosenval;
                    String sb6 = split1[1] +
                            str1 +
                            OvulationCalc.this.getResources().getString(R.string.jun) +
                            str1 +
                            split1[2];
                    textView6.setText(sb6);
                    break;
                case 7:
                    TextView textView7 = OvulationCalc.this.tvdatechosenval;
                    String sb7 = split1[1] +
                            str1 +
                            OvulationCalc.this.getResources().getString(R.string.jul) +
                            str1 +
                            split1[2];
                    textView7.setText(sb7);
                    break;
                case 8:
                    TextView textView8 = OvulationCalc.this.tvdatechosenval;
                    String sb8 = split1[1] +
                            str1 +
                            OvulationCalc.this.getResources().getString(R.string.aug) +
                            str1 +
                            split1[2];
                    textView8.setText(sb8);
                    break;
                case 9:
                    TextView textView9 = OvulationCalc.this.tvdatechosenval;
                    String sb9 = split1[1] +
                            str1 +
                            OvulationCalc.this.getResources().getString(R.string.sep) +
                            str1 +
                            split1[2];
                    textView9.setText(sb9);
                    break;
                case 10:
                    TextView textView10 = OvulationCalc.this.tvdatechosenval;
                    String sb10 = split1[1] +
                            str1 +
                            OvulationCalc.this.getResources().getString(R.string.oct) +
                            str1 +
                            split1[2];
                    textView10.setText(sb10);
                    break;
                case 11:
                    TextView textView11 = OvulationCalc.this.tvdatechosenval;
                    String sb11 = split1[1] +
                            str1 +
                            OvulationCalc.this.getResources().getString(R.string.nov) +
                            str1 +
                            split1[2];
                    textView11.setText(sb11);
                    break;
                case 12:
                    TextView textView12 = OvulationCalc.this.tvdatechosenval;
                    String sb12 = split1[1] +
                            str1 +
                            OvulationCalc.this.getResources().getString(R.string.dec) +
                            str1 +
                            split1[2];
                    textView12.setText(sb12);
                    break;
            }
            instance.add(Calendar.DATE, 12);
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            OvulationCalc.this.result = simpleDateFormat1.format(instance.getTime());
            instance.add(Calendar.DATE, 4);
            OvulationCalc.this.result2 = simpleDateFormat1.format(instance.getTime());
        };
        final int i4 = i;
        final int i5 = i2;
        final int i6 = i3;


        button2.setOnClickListener(view0 -> {
            OvulationCalc.this.anInt++;
            instance.set(i4, i5, i6);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    r10, instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DATE));
            datePickerDialog.show();
        });
        View.OnClickListener r42 = view0 -> {

            if (this.anInt == 0) {
                instance.set(i4, i5, i6);
                instance.add(Calendar.DATE, 12);
                SimpleDateFormat simpleDateFormat12 = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                OvulationCalc.this.result = simpleDateFormat12.format(instance.getTime());
                instance.add(Calendar.DATE, 4);
                OvulationCalc.this.result2 = simpleDateFormat12.format(instance.getTime());

                go(OvulationCalcRes.newInstance(this.result, this.result2));
                return;
            }
            go(OvulationCalcRes.newInstance(this.result, this.result2));
        };
        button.setOnClickListener(r42);
    }

    private void init(View view) {
        tvdatechosen = view.findViewById(R.id.tvdatechosen);
        tvdatechosenval = view.findViewById(R.id.tvdatechosenval);
        if (null != mainView) {
            mainView.setTitleNew(R.string.ovulation);
        }
        primaryColor = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.yellowcolorPrimary);
    }
}
