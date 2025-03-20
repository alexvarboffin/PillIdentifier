package com.walhalla.pillfinder.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.text.SpannableStringBuilder;

import androidx.recyclerview.widget.RecyclerView;

import gov.nih.nlm.model.NlmRxImage;

import okhttp3.PicassoHelper;
import com.walhalla.pillfinder.databinding.RowItemNlmrximagesBinding;

public class NlmRxImagesViewHolder extends RecyclerView.ViewHolder {

    private final PaginationAdapter.ChildItemClickListener childItemClickListener;
    private final RowItemNlmrximagesBinding binding;

//        @BindView(R.id.tv_splVersion)
//        TextView tvSplVersion;
//        @BindView(R.id.tv_id)
//        TextView tvId;
//        @BindView(R.id.tv_splRootId)
//        TextView tvSplRootId;
//        @BindView(R.id.tv_splSetId)
//        TextView tvSplSetId;
//        @BindView(R.id.tv_labeler)
//        TextView tvLabeler;
//
//        @BindView(R.id.tv_rxcui)
//        TextView tvRxcui;
//        @BindView(R.id.tv_ndc11)
//        TextView tvNdc11;
//
//        @BindView(R.id.tv_acqDate)
//        TextView tvAcqDate;
//        @BindView(R.id.tv_imageSize)
//        TextView tvImageSize;
//        @BindView(R.id.tv_part)
//        TextView tvPart;
//        @BindView(R.id.tv_attribution)
//        TextView tvAttribution;


    NlmRxImagesViewHolder(
            RowItemNlmrximagesBinding binding,
            PaginationAdapter.ChildItemClickListener childItemClickListener) {
        super(binding.getRoot());
        this.binding = binding;

        this.childItemClickListener = childItemClickListener;

    }

    public void bind(Context context, PicassoHelper picasso, final NlmRxImage nlmrximages) {

//            tvSplVersion.setText(nlmrximages.getSplVersion());
//            tvId.setText(nlmrximages.getId());
//            tvSplRootId.setText(nlmrximages.getSplRootId());
//            tvSplSetId.setText(nlmrximages.getSplSetId());
//            tvLabeler.setText(nlmrximages.getLabeler());
//            tvRxcui.setText(nlmrximages.getRxcui());
//            tvNdc11.setText(nlmrximages.getNdc11());
//            tvAcqDate.setText(nlmrximages.getAcqDate());
//            tvImageSize.setText(nlmrximages.getImageSize());
//            tvPart.setText(nlmrximages.getPart());
//            tvAttribution.setText(nlmrximages.getAttribution());

        if (noneNull(nlmrximages.imageUrl)) {
            picasso.loadThumbImages(context, nlmrximages.imageUrl, binding.image);
        }
        SpannableStringBuilder value = null;
        if (nlmrximages.getLabeler() != null) {
            value = new SpannableStringBuilder();
            value.append("<b>").append(nlmrximages.getLabeler()).append("</b>");
        }
        if (nlmrximages.name != null) {
            if (value != null) {
                value.append(" ");
            } else {
                value = new SpannableStringBuilder();
            }
            value.append(nlmrximages.name);
            binding.name.setText(Html.fromHtml(value.toString()));
        }
//        if (nlmrximages.id != null) {
//            binding.id.setText(String.valueOf(nlmrximages.id));
//        }

        if (nlmrximages.rxcui != null) {
            binding.id.setText(String.valueOf(nlmrximages.rxcui));
        }

        binding.getRoot().setOnClickListener(view -> childItemClickListener.onClick(nlmrximages));
    }

    private boolean noneNull(String imageUrl) {
        return imageUrl != null && !imageUrl.isEmpty();
    }
}