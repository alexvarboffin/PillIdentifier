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

public class VolumeAdapter extends ArrayAdapter<String> {

    private final String[] arr;
    private final int[] vol_img = {R.drawable.volume, R.drawable.volume, R.drawable.volume};

    VolumeAdapter(Context context, int i, String[] strArr) {
        super(context, i, strArr);
        arr = new String[]{
                context.getResources().getString(R.string.ounces),
                context.getResources().getString(R.string.ml),
                context.getResources().getString(R.string.cup)};

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
        ((ImageView) inflate.findViewById(R.id.image)).setImageResource(vol_img[i]);
        return inflate;
    }
}