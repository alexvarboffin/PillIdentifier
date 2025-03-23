package com.walhalla.health.RespirationRate;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.walhalla.health.Constant;
import com.walhalla.health.IdealWeight.InnerAbstractFragment;
import com.walhalla.health.R;
import com.walhalla.ui.DLog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class RespirationRate extends InnerAbstractFragment {

    @Override
    protected int aLayout() {
        return R.layout.breath_count;
    }

//    @Override
//    protected int aTheme() {
//        return R.style.CyanTheme;
//    }

    private TextView tvdatechosenval;
    private Button chooseDate, calc;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        final Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.YEAR);
        int i2 = instance.get(Calendar.MONTH);
        int i3 = instance.get(Calendar.DATE);

        final DatePickerDialog.OnDateSetListener r6 = (datePicker, i1, i21, i31) -> {
            instance.set(Calendar.YEAR, i1);
            instance.set(Calendar.MONTH, i21);
            instance.set(Calendar.DATE, i31);
            String[] split = simpleDateFormat.format(instance.getTime()).split("/");
            String str = " ";
            switch (Integer.parseInt(split[0])) {
                case 1:
                    TextView textView = RespirationRate.this.tvdatechosenval;
                    String sb = split[1] +
                            str +
                            RespirationRate.this.getResources().getString(R.string.jan) +
                            str +
                            split[2];
                    textView.setText(sb);
                    break;
                case 2:
                    TextView textView2 = RespirationRate.this.tvdatechosenval;
                    String sb2 = split[1] +
                            str +
                            RespirationRate.this.getResources().getString(R.string.feb) +
                            str +
                            split[2];
                    textView2.setText(sb2);
                    break;
                case 3:
                    TextView textView3 = RespirationRate.this.tvdatechosenval;
                    String sb3 = split[1] +
                            str +
                            RespirationRate.this.getResources().getString(R.string.mar) +
                            str +
                            split[2];
                    textView3.setText(sb3);
                    break;
                case 4:
                    TextView textView4 = RespirationRate.this.tvdatechosenval;
                    String sb4 = split[1] +
                            str +
                            RespirationRate.this.getResources().getString(R.string.apr) +
                            str +
                            split[2];
                    textView4.setText(sb4);
                    break;
                case 5:
                    TextView textView5 = RespirationRate.this.tvdatechosenval;
                    String sb5 = split[1] +
                            str +
                            RespirationRate.this.getResources().getString(R.string.may) +
                            str +
                            split[2];
                    textView5.setText(sb5);
                    break;
                case 6:
                    TextView textView6 = RespirationRate.this.tvdatechosenval;
                    String sb6 = split[1] +
                            str +
                            RespirationRate.this.getResources().getString(R.string.jun) +
                            str +
                            split[2];
                    textView6.setText(sb6);
                    break;
                case 7:
                    TextView textView7 = RespirationRate.this.tvdatechosenval;
                    String sb7 = split[1] +
                            str +
                            RespirationRate.this.getResources().getString(R.string.jul) +
                            str +
                            split[2];
                    textView7.setText(sb7);
                    break;
                case 8:
                    TextView textView8 = RespirationRate.this.tvdatechosenval;
                    String sb8 = split[1] +
                            str +
                            RespirationRate.this.getResources().getString(R.string.aug) +
                            str +
                            split[2];
                    textView8.setText(sb8);
                    break;
                case 9:
                    TextView textView9 = RespirationRate.this.tvdatechosenval;
                    String sb9 = split[1] +
                            str +
                            RespirationRate.this.getResources().getString(R.string.sep) +
                            str +
                            split[2];
                    textView9.setText(sb9);
                    break;
                case 10:
                    TextView textView10 = RespirationRate.this.tvdatechosenval;
                    String sb10 = split[1] +
                            str +
                            RespirationRate.this.getResources().getString(R.string.oct) +
                            str +
                            split[2];
                    textView10.setText(sb10);
                    break;
                case 11:
                    TextView textView11 = RespirationRate.this.tvdatechosenval;
                    String sb11 = split[1] +
                            str +
                            RespirationRate.this.getResources().getString(R.string.nov) +
                            str +
                            split[2];
                    textView11.setText(sb11);
                    break;
                case 12:
                    TextView textView12 = RespirationRate.this.tvdatechosenval;
                    String sb12 = split[1] +
                            str +
                            RespirationRate.this.getResources().getString(R.string.dec) +
                            str +
                            split[2];
                    textView12.setText(sb12);
                    break;
            }
        };

        final int i4 = i;
        final int i5 = i2;
        final int i6 = i3;
        tvdatechosenval.setText(simpleDateFormat.format(instance.getTime()));

        View.OnClickListener r0 = view0 -> {
            instance.set(i4, i5, i6);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), r6, instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DATE));
            datePickerDialog.show();
        };
        chooseDate.setOnClickListener(r0);
        calc.setOnClickListener(v -> {
            final Calendar instanceNew = Calendar.getInstance();
            if (simpleDateFormat.format(instance.getTime()).equals(simpleDateFormat.format(instanceNew.getTime())) || instance.getTime().after(instanceNew.getTime())) {
                mainView.snackbar(R.string.valid_date);
                return;
            } else {
                getDiffBetweenDate(instance);
            }

        });
    }

    public void getDiffBetweenDate(Calendar dateBirth) {
//        Calendar dateCurrent=Calendar.getInstance();
        long msDiff = Calendar.getInstance().getTimeInMillis() - dateBirth.getTimeInMillis();
        long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);

//        Calendar birthDay = dateBirth;
//        Calendar birthDay = new GregorianCalendar(1955, Calendar.MAY, 19);
        Calendar today = Calendar.getInstance();
//        today.setTime(new Date());
        DLog.d("yearrrrrget==--" + simpleDateFormat.format(dateBirth.getTime()));
        int yearsInBetween = today.get(Calendar.YEAR)
                - dateBirth.get(Calendar.YEAR);
        int monthsDiff = today.get(Calendar.MONTH)
                - dateBirth.get(Calendar.MONTH);
//        long ageInMonths = yearsInBetween*12 + monthsDiff;
//        long age = yearsInBetween;

        Date dateBefore = dateBirth.getTime();
        Date dateAfter = today.getTime();
        long difference = dateAfter.getTime() - dateBefore.getTime();
        int daysBetween = (int) (difference / (1000 * 60 * 60 * 24));

        Log.e("year,month-day==", "--" + yearsInBetween + "," + monthsDiff + "," + daysBetween);

        float daysAdd = 0;
        int totalDays = daysBetween;

//        long diff1 = Date.UTC(today.YEAR, today.MONTH- 1,today.DAY_OF_YEAR, 0, 0, 0) - Date.UTC(dateBirth.YEAR, dateBirth.MONTH - 1,dateBirth.DAY_OF_YEAR, 0, 0, 0);
        long diff1 = difference;
        Log.e("difff==", "---" + diff1);
        float secleft = diff1 / 1000 / 60;
        float hrsleft = secleft / 60;
        float daysleft = (hrsleft / 24) + daysAdd;
        int breaths = (int) daysleft;
        if (yearsInBetween >= 1 && yearsInBetween <= 5) {

            breaths = breaths * 25 * 1440;


        } else if (yearsInBetween > 5) {
            breaths = breaths * 11 * 1440;

        } else {
            if (monthsDiff < 6) {
                breaths = breaths * 45 * 1440;
            } else {
                breaths = breaths * 27 * 1440;
            }

        }
        go(RespirationRateResult.newInstance(String.valueOf(breaths)));
        Log.e("breathcounts===", "0---" + breaths);
    }

    private void init(View view) {
        title(R.string.title_breath_count);

        int primaryColor = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.cyancolorPrimary);
        chooseDate = view.findViewById(R.id.chooseDate);
        calc = view.findViewById(R.id.calc);
        tvdatechosenval = view.findViewById(R.id.tvdatechosenval);
        chooseDate.setBackground(Constant.getShapeDrawable(false, primaryColor));
        calc.setBackground(Constant.getShapeDrawable(false, primaryColor));
    }
}
