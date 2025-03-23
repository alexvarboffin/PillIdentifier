package com.walhalla.health;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class M {

    public static void setThemeColor(int primaryColor, ImageView imageView) {
        Drawable tmp = imageView.getDrawable();
        if (tmp != null) {
            tmp.setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
        }
    }
}
