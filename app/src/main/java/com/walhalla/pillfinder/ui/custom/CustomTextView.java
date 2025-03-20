package com.walhalla.pillfinder.ui.custom;

/**
 *
 *
 * MyriadPro-Cond
 */
import android.content.Context;

import androidx.appcompat.widget.AppCompatTextView;


import android.util.AttributeSet;


public class CustomTextView extends AppCompatTextView {
    public CustomTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public CustomTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public CustomTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {//OpenSans-Regular
//            //setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/bitter.ttf"));
//            Typeface typeface1 = ResourcesCompat.getFont(this.getContext(), R.font.cutive_mono);
//            Typeface typeface2 = ResourcesCompat.getFont(this.getContext(), R.font.oxygen_mono);//mono
//            Typeface typeface3 = ResourcesCompat.getFont(this.getContext(), R.font.vera_mono);
//            Typeface typeface4 = ResourcesCompat.getFont(this.getContext(), R.font.oxygen_mono_regular);
//            Typeface typeface5 = ResourcesCompat.getFont(this.getContext(), R.font.droid_sans_mono);
//            setTypeface(typeface2);
//            setTextColor(Color.BLACK);

        }
    }
}