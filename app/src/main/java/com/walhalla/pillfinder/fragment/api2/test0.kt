package com.walhalla.pillfinder.fragment.api2

import android.content.Context
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

fun test0(
    context: Context,
    autoTextView: AutoCompleteTextView,
    w: TextWatcher,
    suggestionAdapter: ArrayAdapter<String>
) {

    autoTextView.addTextChangedListener(w)
    autoTextView.threshold = 1
    autoTextView.hint = "Search for a drug or ingredient"
    autoTextView.setAdapter(suggestionAdapter)
    //autoTextView.showDropdown0(adapter = suggestionAdapter)

}