package com.walhalla.pillfinder.ui.custom;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;


public class SquareToggleButton extends androidx.appcompat.widget.AppCompatToggleButton {


    public SquareToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquareToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareToggleButton(Context context) {
        super(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int size;
        if (widthMode == MeasureSpec.EXACTLY && widthSize > 0) {
            size = widthSize;
        } else if (heightMode == MeasureSpec.EXACTLY && heightSize > 0) {
            size = heightSize;
        } else {
            size = Math.min(widthSize, heightSize);
        }
        int finalMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        super.onMeasure(finalMeasureSpec, finalMeasureSpec);
    }
    
}
