package com.walhalla.pillfinder.adapter.viewHolder;

import static com.walhalla.pillfinder.MpcField.IMPRINT;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.walhalla.pillfinder.MpcField;
import com.walhalla.pillfinder.R;
import com.walhalla.pillfinder.adapter.ComplexPresenter;
import com.walhalla.pillfinder.adapter.mpc.MpcObj;

public class MpcViewHolder extends RecyclerView.ViewHolder {

    private final ViewGroup layout;
    private final View btn;
    private final ComplexPresenter presenter;

    public void bind(MpcObj object, int position) {
        if (position % 2 > 0) {
            this.layout.setBackgroundColor(Color.WHITE);
        }
        makeField(object.field);
        this.text2.setText(object.value);
        btn.setOnClickListener(v -> {
            presenter.onItemClicked(v, object);
        });
    }

    private void makeField(MpcField field) {
        int res;
        if (field == MpcField.IMPRINT) {
            res = R.string.mpc_imprint;
        } else if (field == MpcField.COLOR) {
            res = R.string.mpc_color;
        } else if (field == MpcField.SHAPE) {
            res = R.string.mpc_shape;
        } else if (field == MpcField.SCORE) {
            res = R.string.mpc_score;
        } else if (field == MpcField.SIZE) {
            res = R.string.mpc_size;
        } else if (field == MpcField.IMPRINT_COLOR) {
            res = R.string.mpc_imprint_color;
        } else if (field == MpcField.IMPRINT_TYPE) {
            res = R.string.mpc_imprint_type;
        } else if (field == MpcField.SYMBOL) {
            res = R.string.mpc_symbol;
        } else {
            res = R.string.app_name;
        }
        this.text1.setText(res);
    }

    private final TextView text1;
    private final TextView text2;

    public MpcViewHolder(View view, ComplexPresenter presenter) {
        super(view);
        text1 = itemView.findViewById(R.id.textView1);
        text2 = itemView.findViewById(R.id.textView2);
        layout = itemView.findViewById(R.id.lLayout1);
        btn = itemView.findViewById(R.id.search_btn);
        this.presenter = presenter;
    }

}
