//package com.walhalla.pillfinder.loader;
//
//import android.content.Context;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//
//import com.walhalla.pillfinder.GlideApp;
//import com.walhalla.pillfinder.R;
//
//public class GUtil {
//
//    public static void loader(@NonNull String images, Context context, ImageView imageView) {
//
//        //DLog.d("@@ " + images);
//
//        switch (images) {
//            case "default":
//                GlideApp.with(context)
//                        .load(R.mipmap.ic_launcher)
//                        .placeholder(R.drawable.placeholder)
//                        .error(R.drawable.ic_not_available)
//                        .into(imageView);
//                break;
//            default:
//                GlideApp.with(context)
//                        .load(images)
//                        .placeholder(R.drawable.placeholder)
//                        .error(R.drawable.ic_not_available)
//                        .into(imageView);
//                break;
//        }
//    }
//}