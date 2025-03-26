package com.walhalla.pillfinder.adapter.viewHolder;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.walhalla.pillfinder.R;
import com.walhalla.pillfinder.adapter.obj.NValue;

public class Simple2TextViewHolder extends RecyclerView.ViewHolder {

    private final LinearLayout layout;

    public void setText(NValue object, int position) {
        if (position % 2 > 0) {
            this.layout.setBackgroundColor(Color.WHITE);
        }
        this.text1.setText(object.name);
        this.text2.setText(object.value);
    }

    private final TextView text1;
    public final TextView text2;

    public Simple2TextViewHolder(View view) {
        super(view);
        text1 = itemView.findViewById(R.id.textView1);
        text2 = itemView.findViewById(R.id.textView2);
        layout = itemView.findViewById(R.id.lLayout1);
    }

}
