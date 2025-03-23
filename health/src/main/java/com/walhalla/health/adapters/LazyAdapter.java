package com.walhalla.health.adapters;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.walhalla.health.R;
import com.walhalla.health.models.RowItem;

import java.util.List;

public class LazyAdapter extends RecyclerView.Adapter<LazyAdapter.MyViewHolder> {

    private final List<RowItem> rowItems;
    private final Activity activity;
    private clickInterface anInterface;


    public interface clickInterface {
        void onRecItemClick(View view, int i);
    }

    public LazyAdapter(List<RowItem> rowItems, Activity activity) {
        this.rowItems = rowItems;
        this.activity = activity;
    }

    public void setListeners(clickInterface listeners) {
        anInterface = listeners;
    }

    @NonNull
    @Override
    public LazyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.list_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LazyAdapter.MyViewHolder viewHolder, int position) {
        RowItem rowItem = rowItems.get(position);
        viewHolder.list_image.setImageResource(rowItem.getImageId());
        viewHolder.img_next.setImageResource(rowItem.getIcon());
        viewHolder.title.setText(rowItem.title);
        viewHolder.description.setText(rowItem.description);
        viewHolder.card.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.card_animation));

        Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.home_image_bg);
        if (drawable != null) {
            drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(activity, rowItem.getColor()), PorterDuff.Mode.SRC_IN));
            drawable.mutate();
            viewHolder.image_layout.setBackground(drawable);
        }
    }


    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        FrameLayout card;
        ImageView list_image, img_next;
        RelativeLayout image_layout;
        TextView title, description;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            title = itemView.findViewById(R.id.title);
            list_image = itemView.findViewById(R.id.list_image);
            img_next = itemView.findViewById(R.id.img_next);
            image_layout = itemView.findViewById(R.id.image_layout);
            description = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (anInterface != null) {
                anInterface.onRecItemClick(v, getAdapterPosition());
            }
        }
    }
}
