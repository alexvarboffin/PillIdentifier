package com.walhalla.pillfinder.adapter.viewHolder;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.walhalla.pillfinder.MpcField;
import com.walhalla.pillfinder.R;
import com.walhalla.pillfinder.adapter.ComplexPresenter;
import com.walhalla.pillfinder.adapter.emptyView.EmptyViewObj;
import com.walhalla.pillfinder.adapter.mpc.MpcObj;
import com.walhalla.pillfinder.adapter.obj.RxCuiObjString;

public class SimpleRxCuiViewHolder extends RecyclerView.ViewHolder {

    private final ViewGroup layout;
    private final View btn;
    private final ComplexPresenter presenter;

    public void bind(RxCuiObjString object, int position) {
        if (position % 2 > 0) {
            this.layout.setBackgroundColor(Color.WHITE);
        }
        this.text1.setText(""+object.rxcui);
        btn.setOnClickListener(v -> {
            presenter.onItemClicked(v, object);
        });
    }



    private final TextView text1;

    public SimpleRxCuiViewHolder(View view, ComplexPresenter presenter) {
        super(view);
        text1 = itemView.findViewById(R.id.textView1);
        layout = itemView.findViewById(R.id.lLayout1);
        btn = itemView.findViewById(R.id.search_btn);
        this.presenter = presenter;
    }


}
