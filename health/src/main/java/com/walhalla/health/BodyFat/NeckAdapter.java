package com.walhalla.health.BodyFat;

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

public class NeckAdapter extends ArrayAdapter<String> {

    private final String[] strArr;
    private final int[] neck_img = {R.drawable.height, R.drawable.height};

    NeckAdapter(Context context, int i, String[] strings) {
        super(context, i, strings);
        strArr = new String[]{
                context.getResources().getString(R.string.centimeters),
                context.getResources().getString(R.string.feets)
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
        ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
        ((ImageView) inflate.findViewById(R.id.image)).setImageResource(this.neck_img[i]);
        return inflate;
    }
}
