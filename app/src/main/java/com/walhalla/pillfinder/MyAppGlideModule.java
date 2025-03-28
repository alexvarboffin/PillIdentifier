//package com.walhalla.pillfinder;
//
//import android.content.Context;
//
//import androidx.annotation.NonNull;
//
//import com.bumptech.glide.GlideBuilder;
//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.load.DecodeFormat;
//import com.bumptech.glide.module.AppGlideModule;
//import com.bumptech.glide.request.RequestOptions;
//
//@GlideModule
//public final class MyAppGlideModule extends AppGlideModule {
//    @Override
//    public void applyOptions(@NonNull Context context, GlideBuilder builder) {
//        // Glide default Bitmap Format is set to RGB_565 since it
//        // consumed just 50% memory footprint compared to ARGB_8888.
//        // Increase memory usage for quality with:
//
//        builder.setDefaultRequestOptions(
//                new RequestOptions()
//                .format(DecodeFormat.PREFER_ARGB_8888));
//    }
//}
