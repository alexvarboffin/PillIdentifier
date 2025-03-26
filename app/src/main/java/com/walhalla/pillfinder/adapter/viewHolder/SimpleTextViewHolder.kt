package com.walhalla.pillfinder.adapter.viewHolder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.walhalla.pillfinder.R;

public class SimpleTextViewHolder extends RecyclerView.ViewHolder {

    public void setText(String text1, int position) {
        if (position % 2 > 0) {
            this.text1.setBackgroundColor(Color.WHITE);
        }
        this.text1.setText(text1);
    }

    public final TextView text1;

    public SimpleTextViewHolder(View view) {
        super(view);
        text1 = itemView.findViewById(R.id.textView1);
    }

}
