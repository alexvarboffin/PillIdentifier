package com.walhalla.health.IdealWeight;

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

public class GenderAdapter extends ArrayAdapter<String> {

    private final String[] strArr0;
    private final int[] gender_img = {R.drawable.man, R.drawable.girl};

    public GenderAdapter(Context context, int i, String[] strArr) {
        super(context, i, strArr);
        strArr0 = new String[]{
                context.getResources().getString(R.string.male),
                context.getResources().getString(R.string.female)
        };
    }

    @Override
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
        ((ImageView) inflate.findViewById(R.id.image)).setImageResource(this.gender_img[i]);
        return inflate;
    }
}