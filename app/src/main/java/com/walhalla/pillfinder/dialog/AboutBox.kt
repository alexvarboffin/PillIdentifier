package com.walhalla.pillfinder.dialog

import android.app.Activity
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.text.util.Linkify
import android.view.InflateException
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.walhalla.pillfinder.R
import com.walhalla.ui.DLog.getAppVersion

object AboutBox {
    var NEW_LINE: Char = 10.toChar()

    fun show(activity: Activity) {
        //Use a Spannable to allow for links highlighting
        val version: CharSequence = "Version: " + getAppVersion(activity)
        val aboutText = SpannableStringBuilder(version)
        aboutText.setSpan(UnderlineSpan(), 0, version.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

//        aboutText.append(NEW_LINE)
//                .append("VERSION_CODE: ")
//                .append(String.valueOf(Build.VERSION.SDK_INT))
//                .append(NEW_LINE);
        aboutText.append(NEW_LINE)
            .append(NEW_LINE)
            .append(activity.getString(R.string.about))

        var about: View
        var tvAbout: TextView
        try {
            //Inflate the custom view
            val inflater = activity.layoutInflater
            about = inflater.inflate(R.layout.dialog_about, activity.findViewById(R.id.aboutView))
            tvAbout = about.findViewById(R.id.aboutText)
        } catch (e: InflateException) {
            //Inflater can throw exception, unlikely but default to TextView if it occurs
            tvAbout = TextView(activity)
            about = tvAbout
        }
        //Set the about text
        tvAbout.text = aboutText
        // Now Linkify the text
        Linkify.addLinks(tvAbout, Linkify.ALL)
        //Build and show the dialog
        val dialog = AlertDialog.Builder(activity)
            .setTitle("About " + activity.getString(R.string.app_name))
            .setCancelable(true)
            .setIcon(R.mipmap.ic_launcher)
            .setPositiveButton(android.R.string.ok, null)
            .setView(about)
            .create()
        //dialog.getWindow().setBackgroundDrawableResource(R.drawable.pretty_gradient);
        dialog.show()
    }
}