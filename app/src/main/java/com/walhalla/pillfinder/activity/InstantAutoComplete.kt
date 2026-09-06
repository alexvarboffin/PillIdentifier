package com.walhalla.pillfinder.activity

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatAutoCompleteTextView

class InstantAutoComplete : AppCompatAutoCompleteTextView {
    constructor(context: Context) : super(context)

    constructor(arg0: Context, arg1: AttributeSet?) : super(arg0, arg1)

    constructor(arg0: Context, arg1: AttributeSet?, arg2: Int) : super(arg0, arg1, arg2)

    override fun enoughToFilter(): Boolean {
        return true
    }

    override fun onFocusChanged(
        focused: Boolean, direction: Int,
        previouslyFocusedRect: Rect?
    ) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused && adapter != null) {
            // This part was necessary to get it working when not
            // inside a TextInputLayout and multiple per activity
            if (!maybeShowSuggestions()) {
                post(Runnable { this.maybeShowSuggestions() })
            }
        }
    }

    private fun maybeShowSuggestions(): Boolean {
        if (windowVisibility == VISIBLE) {
            performFiltering(text, 0)
            showDropDown()
            return true
        } else {
            return false
        }
    }
}