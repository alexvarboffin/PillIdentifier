package com.walhalla.bloodAlcohol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.walhalla.health.R;

public class TimeAdapter extends ArrayAdapter<String> {

    private final String[] arr;
    int[] time_img = {R.drawable.time, R.drawable.time, R.drawable.time};

    TimeAdapter(Context context, int i, String[] strArr) {
        super(context, i, strArr);
        arr = new String[]{
                context.getResources().getString(R.string.hour),
                context.getResources().getString(R.string.minute),
                context.getResources().getString(R.string.day)
        };
    }

    public View getDropDownView(int i, View view, @NonNull ViewGroup viewGroup) {
        return getCustomView(i, viewGroup);
    }

    @NonNull
    public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
        return getCustomView(i, viewGroup);
    }

    public View getCustomView(int i, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.spinner_down_blue, viewGroup, false);
        ((TextView) inflate.findViewById(R.id.currency)).setText(arr[i]);
        ((ImageView) inflate.findViewById(R.id.image)).setImageResource(time_img[i]);
        return inflate;
    }
}