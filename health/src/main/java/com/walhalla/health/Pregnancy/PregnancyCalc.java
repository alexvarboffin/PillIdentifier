package com.walhalla.health.Pregnancy;

import static com.walhalla.health.Constant.getShapeDrawable;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.github.pdfviewer.PDFView;
import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PregnancyCalc extends InnerAbstractFragment {

    private int anInt = 0;
    private Button pdf;
    private int primaryColor;
    private TextView tvdatechosen;
    private TextView tvdatechosenval;

    @Override
    protected int aLayout() {
        return R.layout.pregnancy;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.PinkTheme;
//    }

    private void init(View view) {
        pdf = view.findViewById(R.id.pdf);
        if (null != mainView) {
            mainView.setTitleNew(R.string.pregnancy);
        }
        primaryColor = ContextCompat.getColor(getContext().getApplicationContext(), R.color.pinkcolorPrimary);
        tvdatechosen = view.findViewById(R.id.tvdatechosen);
        tvdatechosenval = view.findViewById(R.id.tvdatechosenval);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.chart)
                .setOnClickListener(view0 -> {
                    if (mainView != null) {
                        mainView.replaceFragment(new ChartPreg());
                    }
                });
        Button button = view.findViewById(R.id.calc);
        Button button2 = view.findViewById(R.id.reset);
        Button chart = view.findViewById(R.id.chart);
        init(view);
        button.setBackground(getShapeDrawable(false, primaryColor));
        button2.setBackground(getShapeDrawable(false, primaryColor));
        chart.setBackground(getShapeDrawable(false, primaryColor));

        pdf.setBackground(getShapeDrawable(false, primaryColor));
        final Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.YEAR);
        int i2 = instance.get(Calendar.MONTH);
        int i3 = instance.get(Calendar.DATE);
        pdf.setOnClickListener(view0 -> {
            final String SAMPLE_FILE = "pregnancy_food_guide.pdf";

            try {
                File file = new File(getActivity().getCacheDir(), SAMPLE_FILE);
                if (!file.exists()) {

                    try {
                        InputStream asset = getActivity().getAssets().open(SAMPLE_FILE);
                        FileOutputStream output;
                        output = new FileOutputStream(file);
                        final byte[] buffer = new byte[1024];
                        int size;
                        while ((size = asset.read(buffer)) != -1) {
                            output.write(buffer, 0, size);
                        }
                        asset.close();
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

//                    PDFView.with(PregnancyCalc.this)
//                            .setfilepath(file.getAbsolutePath())
//                            .setSwipeOrientation(VERTICAL) //if false pageswipe is vertical otherwise its horizontal
//                            .start();

                PDFView.with(getActivity())
                        .fromfilepath(file.getAbsolutePath())
                        //.setSwipeOrientation(VERTICAL) //if false pageswipe is vertical otherwise its horizontal
                        .swipeHorizontal(false)
                        .start();


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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

        final DatePickerDialog.OnDateSetListener r6 = (datePicker, i12, i21, i31) -> {
            instance.set(Calendar.YEAR, i12);
            instance.set(Calendar.MONTH, i21);
            instance.set(Calendar.DATE, i31);
            String[] split1 = simpleDateFormat.format(instance.getTime()).split("/");
            String str12 = " ";
            switch (Integer.parseInt(split1[0])) {
                case 1:
                    TextView textView = PregnancyCalc.this.tvdatechosenval;
                    String sb = split1[1] +
                            str12 +
                            PregnancyCalc.this.getResources().getString(R.string.jan) +
                            str12 +
                            split1[2];
                    textView.setText(sb);
                    break;
                case 2:
                    TextView textView2 = PregnancyCalc.this.tvdatechosenval;
                    String sb2 = split1[1] +
                            str12 +
                            PregnancyCalc.this.getResources().getString(R.string.feb) +
                            str12 +
                            split1[2];
                    textView2.setText(sb2);
                    break;
                case 3:
                    TextView textView3 = PregnancyCalc.this.tvdatechosenval;
                    String sb3 = split1[1] +
                            str12 +
                            PregnancyCalc.this.getResources().getString(R.string.mar) +
                            str12 +
                            split1[2];
                    textView3.setText(sb3);
                    break;
                case 4:
                    TextView textView4 = PregnancyCalc.this.tvdatechosenval;
                    String sb4 = split1[1] +
                            str12 +
                            PregnancyCalc.this.getResources().getString(R.string.apr) +
                            str12 +
                            split1[2];
                    textView4.setText(sb4);
                    break;
                case 5:
                    TextView textView5 = PregnancyCalc.this.tvdatechosenval;
                    String sb5 = split1[1] +
                            str12 +
                            PregnancyCalc.this.getResources().getString(R.string.may) +
                            str12 +
                            split1[2];
                    textView5.setText(sb5);
                    break;
                case 6:
                    TextView textView6 = PregnancyCalc.this.tvdatechosenval;
                    String sb6 = split1[1] +
                            str12 +
                            PregnancyCalc.this.getResources().getString(R.string.jun) +
                            str12 +
                            split1[2];
                    textView6.setText(sb6);
                    break;
                case 7:
                    TextView textView7 = PregnancyCalc.this.tvdatechosenval;
                    String sb7 = split1[1] +
                            str12 +
                            PregnancyCalc.this.getResources().getString(R.string.jul) +
                            str12 +
                            split1[2];
                    textView7.setText(sb7);
                    break;
                case 8:
                    TextView textView8 = PregnancyCalc.this.tvdatechosenval;
                    String sb8 = split1[1] +
                            str12 +
                            PregnancyCalc.this.getResources().getString(R.string.aug) +
                            str12 +
                            split1[2];
                    textView8.setText(sb8);
                    break;
                case 9:
                    TextView textView9 = PregnancyCalc.this.tvdatechosenval;
                    String sb9 = split1[1] +
                            str12 +
                            PregnancyCalc.this.getResources().getString(R.string.sep) +
                            str12 +
                            split1[2];
                    textView9.setText(sb9);
                    break;
                case 10:
                    TextView textView10 = PregnancyCalc.this.tvdatechosenval;
                    String sb10 = split1[1] +
                            str12 +
                            PregnancyCalc.this.getResources().getString(R.string.oct) +
                            str12 +
                            split1[2];
                    textView10.setText(sb10);
                    break;
                case 11:
                    TextView textView11 = PregnancyCalc.this.tvdatechosenval;
                    String sb11 = split1[1] +
                            str12 +
                            PregnancyCalc.this.getResources().getString(R.string.nov) +
                            str12 +
                            split1[2];
                    textView11.setText(sb11);
                    break;
                case 12:
                    TextView textView12 = PregnancyCalc.this.tvdatechosenval;
                    String sb12 = split1[1] +
                            str12 +
                            PregnancyCalc.this.getResources().getString(R.string.dec) +
                            str12 +
                            split1[2];
                    textView12.setText(sb12);
                    break;
            }
            instance.add(Calendar.DATE, 282);
        };
        final int i4 = i;
        final int i5 = i2;
        final int i6 = i3;

        View.OnClickListener r0 = view1 -> {
            PregnancyCalc.this.anInt++;
            instance.set(i4, i5, i6);
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(), r6, instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DATE));
            datePickerDialog.show();
        };

        button2.setOnClickListener(r0);
        View.OnClickListener r02 = view0 -> {
            String str2 = "MM/dd/yyyy";
            if (anInt == 0) {
                instance.set(i4, i5, i6);
                instance.add(Calendar.DATE, 282);
                String format = new SimpleDateFormat(str2, Locale.US).format(instance.getTime());

                if (mainView != null) {
                    mainView.replaceFragment(PregnancyCalcRes.newInstance(format));
                }
                return;
            }
            String format2 = new SimpleDateFormat(str2, Locale.US).format(instance.getTime());
            if (mainView != null) {
                mainView.replaceFragment(PregnancyCalcRes.newInstance(format2));
            }
        };
        button.setOnClickListener(r02);
    }
}
