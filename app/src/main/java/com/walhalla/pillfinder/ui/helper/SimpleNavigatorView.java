package com.walhalla.pillfinder.ui.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.walhalla.pillfinder.R;


public class SimpleNavigatorView extends LinearLayout implements NavigatorView {

    private OnClickListener mListener;

//    public interface NavigatorCallback {
//        void navigate(int page);
//    }

    // Index from which pagination should start (0 is 1st page in our case)

    public int CURRENT_PAGE_NUMBER = PAGE_START_INDEX;
    private int TOTAL_PAGES = 0;


    public int getTOTAL_PAGES() {
        return TOTAL_PAGES;
    }

    public void setTOTAL_PAGES(int TOTAL_PAGES) {
        this.TOTAL_PAGES = TOTAL_PAGES;
    }

//    private String mExampleString; // TODO: use a default from R.string...
//    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
//    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
//    private Drawable mExampleDrawable;
//
//    private TextPaint mTextPaint;
//    private float mTextWidth;
//    private float mTextHeight;

    public SimpleNavigatorView(Context context) {
        super(context);
        init(null, 0);
    }

    public SimpleNavigatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SimpleNavigatorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SimpleNavigatorView, defStyle, 0);

//        mExampleString = a.getString(R.styleable.Navigator_exampleString);
//        mExampleColor = a.getColor(R.styleable.Navigator_exampleColor, mExampleColor);
//        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
//        // values that should fall on pixel boundaries.
//        mExampleDimension = a.getDimension(R.styleable.Navigator_exampleDimension, mExampleDimension);
//
//        if (a.hasValue(R.styleable.Navigator_exampleDrawable)) {
//            mExampleDrawable = a.getDrawable(
//                    R.styleable.Navigator_exampleDrawable);
//            mExampleDrawable.setCallback(this);
//        }
//
        a.recycle();
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);
//
//        // Set up a default TextPaint object
//        mTextPaint = new TextPaint();
//        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
//        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
//        mTextPaint.setTextSize(mExampleDimension);
//        mTextPaint.setColor(mExampleColor);
//        mTextWidth = mTextPaint.measureText(mExampleString);
//
//        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
//        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
//        canvas.drawText(mExampleString,
//                paddingLeft + (contentWidth - mTextWidth) / 2,
//                paddingTop + (contentHeight + mTextHeight) / 2,
//                mTextPaint);
//
//        // Draw the example drawable on top of the text.
//        if (mExampleDrawable != null) {
//            mExampleDrawable.setBounds(paddingLeft, paddingTop,
//                    paddingLeft + contentWidth, paddingTop + contentHeight);
//            mExampleDrawable.draw(canvas);
//        }
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
//    public String getExampleString() {
//        return mExampleString;
//    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
//    public void setExampleString(String exampleString) {
//        mExampleString = exampleString;
//        invalidateTextPaintAndMeasurements();
//    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
//    public int getExampleColor() {
//        return mExampleColor;
//    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
//    public void setExampleColor(int exampleColor) {
//        mExampleColor = exampleColor;
//        invalidateTextPaintAndMeasurements();
//    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
//    public float getExampleDimension() {
//        return mExampleDimension;
//    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
//    public void setExampleDimension(float exampleDimension) {
//        mExampleDimension = exampleDimension;
//        invalidateTextPaintAndMeasurements();
//    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
//    public Drawable getExampleDrawable() {
//        return mExampleDrawable;
//    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @para//m exampleDrawable The example drawable attribute value to use.
     */
//    public void setExampleDrawable(Drawable exampleDrawable) {
//        mExampleDrawable = exampleDrawable;
//    }
    private void show() {

        removeAllViews();

        if (TOTAL_PAGES == 0) {
            return;
        }

        FloatingActionButton fab;
        if (CURRENT_PAGE_NUMBER > 1) {


//            fab = new ImageButton(new ContextThemeWrapper(getContext(), R.style.round_nav_batton), null,
//                    R.style.round_nav_batton);

//            //fab.setText(R.string.prev_page);
//
//            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_navigation_before);
//            fab.setImageDrawable(drawable);


            fab = new FloatingActionButton(new ContextThemeWrapper(getContext(), R.style.round_nav_batton));
            fab.setId(R.id.prev_page_btn);
            fab.setImageResource(R.drawable.ic_navigation_before);
            fab.setOnClickListener(mListener);
            addView(fab);
        }
        if (TOTAL_PAGES > CURRENT_PAGE_NUMBER) {
//            fab = new ImageButton(new ContextThemeWrapper(getContext(), R.style.round_nav_batton), null,
//                    R.style.round_nav_batton);

//            //fab.setText(R.string.next_page);
//
//            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_navigation_next);
//            fab.setImageDrawable(drawable);

            fab = new FloatingActionButton(new ContextThemeWrapper(getContext(), R.style.round_nav_batton));
            fab.setId(R.id.next_page_btn);
            fab.setImageResource(R.drawable.ic_navigation_next);
            fab.setOnClickListener(mListener);
            addView(fab);
        }
    }


    @Override
    public void setOnClickListener(@Nullable OnClickListener onClickListener) {
        //super.setOnClickListener(onClickListener);
        mListener = onClickListener;
    }

    public int nextPage() {
        return ++CURRENT_PAGE_NUMBER;
    }

    public int prevPage() {
        return --CURRENT_PAGE_NUMBER;
    }


    @Override
    public void reset() {
        setTOTAL_PAGES(0);
        CURRENT_PAGE_NUMBER = 0;
        removeAllViews();
    }
}

