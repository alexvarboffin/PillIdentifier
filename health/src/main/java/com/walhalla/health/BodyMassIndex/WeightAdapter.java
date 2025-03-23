package com.walhalla.health.BodyMassIndex;

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

/**
 * weight
 * Масса
 */
public class WeightAdapter extends ArrayAdapter<String> {

    private final String[] strArr0;
    int[] weightimg = {R.drawable.weight, R.drawable.weight};

    public WeightAdapter(Context context, int i, String[] strArr) {
        super(context, i, strArr);
        strArr0 = new String[]{
                context.getResources().getString(R.string.kilograms),
                context.getResources().getString(R.string.pounds)
        };
    }

    public View getDropDownView(int i, View view, @Nullable ViewGroup viewGroup) {
        return getCustomView(i, viewGroup);
    }

    @NonNull
    public View getView(int i, View view, @Nullable ViewGroup viewGroup) {
        return getCustomView(i, viewGroup);
    }

    public View getCustomView(int i, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.spinner_down_blue, viewGroup, false);
        ((TextView) inflate.findViewById(R.id.currency)).setText(strArr0[i]);
        ((ImageView) inflate.findViewById(R.id.image)).setImageResource(weightimg[i]);
        return inflate;
    }
}