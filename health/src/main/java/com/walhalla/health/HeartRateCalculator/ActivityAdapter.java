package com.walhalla.health.HeartRateCalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.walhalla.health.R;

public class ActivityAdapter extends ArrayAdapter<String> {

    private final int[] activity_img = {R.drawable.activity, R.drawable.activity, R.drawable.activity, R.drawable.activity};

    ActivityAdapter(Context context, int i, String[] strArr) {
        super(context, i, strArr);
    }

    public View getDropDownView(int i, View view, @Nullable ViewGroup viewGroup) {
        return getCustomView(i, viewGroup);
    }

    @NonNull
    public View getView(int i, View view, @Nullable ViewGroup viewGroup) {
        return getCustomView(i, viewGroup);
    }

    public View getCustomView(int i, ViewGroup viewGroup) {
        String[] strArr = {
                getContext().getResources().getString(R.string.activity_level_moderate),
                getContext().getResources().getString(R.string.activity_level_little_diff),
                getContext().getResources().getString(R.string.activity_level_moderately_diff),
                getContext().getResources().getString(R.string.activity_level_hard)
        };
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.spinner_down_blue, viewGroup, false);
        ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
        ((ImageView) inflate.findViewById(R.id.image)).setImageResource(activity_img[i]);
        return inflate;
    }
}