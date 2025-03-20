package com.walhalla.pillfinder.adapter.viewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;

import com.walhalla.pillfinder.adapter.ComplexPresenter;


public abstract class BaseViewHolder<R> extends RecyclerView.ViewHolder {

    private final ComplexPresenter mComplexPresenter;

    protected int view_state_number;

    public BaseViewHolder(View view, int blocking_flag, ComplexPresenter presenter) {
        super(view);

        this.mComplexPresenter = presenter;
        this.view_state_number = blocking_flag;
    }

    private View inflater(ViewGroup viewGroup) {
        return itemView;
    }

    public ComplexPresenter getPresenter() {
        return mComplexPresenter;
    }

    public abstract void bind(@NonNull R model);
}
