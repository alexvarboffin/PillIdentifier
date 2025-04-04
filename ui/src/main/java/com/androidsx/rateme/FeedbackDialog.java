package com.androidsx.rateme;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.UUtils;
import com.walhalla.ui.DLog;
import com.walhalla.ui.R;

import java.util.List;

public class FeedbackDialog extends DialogFragment {
    private static final String EXTRA_EMAIL = "email";
    private static final String EXTRA_APP_NAME = "app-name";
    private static final String EXTRA_DIALOG_TITLE_COLOR = "dialog-title-color";
    private static final String EXTRA_DIALOG_COLOR = "dialog-color";
    private static final String EXTRA_TEXT_COLOR = "text-color";
    private static final String EXTRA_HEADER_TEXT_COLOR = "header-text-color";
    private static final String EXTRA_LOGO = "icon";
    private static final String EXTRA_RATE_BUTTON_TEXT_COLOR = "button-text-color";
    private static final String EXTRA_RATE_BUTTON_BG_COLOR = "button-bg-color";
    private static final String EXTRA_TITLE_DIVIDER = "color-title-divider";
    private static final String EXTRA_RATING_BAR = "get-rating";
    private static final String EXTRA_ON_ACTION_LISTENER = "on-action-listener";

    // Views
    private View confirmDialogTitleView;
    private View confirmDialogView;
    private Button cancel;
    private Button yes;

    private OnRatingListener onActionListener;

    public static FeedbackDialog newInstance(String email,
                                             String appName,
                                             int titleBackgroundColor,
                                             int dialogColor,
                                             int headerTextColor,
                                             int textColor,
                                             int logoResId,
                                             int lineDividerColor,
                                             int rateButtonTextColor,
                                             int rateButtonBackgroundColor,
                                             float getRatingBar,
                                             OnRatingListener onRatingListener) {
        FeedbackDialog feedbackDialog = new FeedbackDialog();
        Bundle args = new Bundle();
        args.putString(EXTRA_EMAIL, email);
        args.putString(EXTRA_APP_NAME, appName);
        args.putInt(EXTRA_DIALOG_TITLE_COLOR, titleBackgroundColor);
        args.putInt(EXTRA_DIALOG_COLOR, dialogColor);
        args.putInt(EXTRA_HEADER_TEXT_COLOR, headerTextColor);
        args.putInt(EXTRA_TEXT_COLOR, textColor);
        args.putInt(EXTRA_LOGO, logoResId);
        args.putInt(EXTRA_RATE_BUTTON_TEXT_COLOR, rateButtonTextColor);
        args.putInt(EXTRA_RATE_BUTTON_BG_COLOR, rateButtonBackgroundColor);
        args.putInt(EXTRA_TITLE_DIVIDER, lineDividerColor);
        args.putFloat(EXTRA_RATING_BAR, getRatingBar);
        args.putParcelable(EXTRA_ON_ACTION_LISTENER, onRatingListener);

        feedbackDialog.setArguments(args);
        return feedbackDialog;

    }

    public FeedbackDialog() {
        // Empty constructor, required for exo_controls_pause/resume
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        initializeUiFieldsDialogGoToMail();
        //Log.d(TAG, "All components were initialized successfully");

        cancel.setOnClickListener(v -> {
            dismiss();
            onActionListener.onRating(OnRatingListener.RatingAction.LOW_RATING_REFUSED_TO_GIVE_FEEDBACK, getArguments().getFloat(EXTRA_RATING_BAR));
            //Log.d(TAG, "Canceled the feedback dialog");
        });

        yes.setOnClickListener(v -> {
            if (DLog.nonNull(getArguments())) {
                goToMail(getArguments().getString(EXTRA_APP_NAME));
            }
            onActionListener.onRating(OnRatingListener.RatingAction.LOW_RATING_GAVE_FEEDBACK, getArguments().getFloat(EXTRA_RATING_BAR));
            //Log.d(TAG, "Agreed to provide feedback");
            dismiss();
        });

        return builder.setCustomTitle(confirmDialogTitleView).setView(confirmDialogView).create();
    }

    private void initializeUiFieldsDialogGoToMail() {
        confirmDialogTitleView = View.inflate(getActivity(), R.layout.rateme__feedback_dialog_title, null);
        confirmDialogView = View.inflate(getActivity(), R.layout.rateme__feedback_dialog_message, null);
        confirmDialogTitleView.setBackgroundColor(getArguments().getInt(EXTRA_DIALOG_TITLE_COLOR));
        confirmDialogView.setBackgroundColor(getArguments().getInt(EXTRA_DIALOG_COLOR));
        if (getArguments().getInt(EXTRA_LOGO) == 0) {
            confirmDialogView.findViewById(R.id.app_icon_dialog_mail).setVisibility(View.GONE);
        } else {
            ((ImageView) confirmDialogView.findViewById(R.id.app_icon_dialog_mail)).setImageResource(getArguments().getInt(EXTRA_LOGO));
            confirmDialogView.findViewById(R.id.app_icon_dialog_mail).setVisibility(View.VISIBLE);
        }
        ((TextView) confirmDialogTitleView.findViewById(R.id.confirmDialogTitle)).setTextColor(getArguments().getInt(EXTRA_HEADER_TEXT_COLOR));
        ((TextView) confirmDialogView.findViewById(R.id.mail_dialog_message)).setTextColor(getArguments().getInt(EXTRA_TEXT_COLOR));
        cancel = confirmDialogView.findViewById(R.id.buttonCancel);
        yes = confirmDialogView.findViewById(R.id.buttonYes);
        cancel.setTextColor(getArguments().getInt(EXTRA_RATE_BUTTON_TEXT_COLOR));
        yes.setTextColor(getArguments().getInt(EXTRA_RATE_BUTTON_TEXT_COLOR));
        cancel.setBackgroundColor(getArguments().getInt(EXTRA_RATE_BUTTON_BG_COLOR));
        yes.setBackgroundColor(getArguments().getInt(EXTRA_RATE_BUTTON_BG_COLOR));
        onActionListener = getArguments().getParcelable(EXTRA_ON_ACTION_LISTENER);
    }

    private void goToMail(String appName) {
        final String subject = getResources().getString(R.string.rateme__email_subject, appName);
        final String gmailPackageName = "com.com.google.android.gm";

        try {
            if (UUtils.isPackageInstalled(getContext(), gmailPackageName)) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getArguments().getString(EXTRA_EMAIL)});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);

                final PackageManager pm = getContext().getPackageManager();
                final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                String className = null;
                for (final ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.equals("com.com.google.android.gm")) {
                        className = info.activityInfo.name;
                        if(!TextUtils.isEmpty(className)){
                            break;
                        }
                    }
                }
                if (className != null) {
                    intent.setClassName(gmailPackageName, className);
                }else {
                    //Ok, try this Activity
                    intent.setClassName(gmailPackageName, "com.com.google.android.gm.ComposeActivityGmail");
                }
                startActivity(Intent.createChooser(intent, ""));
            } else {
                sendGenericMail(subject);
            }
        } catch (android.content.ActivityNotFoundException ex) {
            sendGenericMail(subject);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        final int titleDividerId = getResources().getIdentifier("titleDivider", "id", "android");
        final View titleDivider = getDialog().findViewById(titleDividerId);
        if (titleDivider != null) {
            titleDivider.setBackgroundColor(getArguments().getInt(EXTRA_TITLE_DIVIDER));
        }
    }

    private void sendGenericMail(String subject) {
        //Log.w(TAG, "Cannot send the email with Gmail. Will use the generic chooser");
        Intent sendGeneric = new Intent(Intent.ACTION_SEND);
        sendGeneric.setType("plain/text");
        sendGeneric.putExtra(Intent.EXTRA_EMAIL, new String[]{getArguments().getString(EXTRA_EMAIL)});
        sendGeneric.putExtra(Intent.EXTRA_SUBJECT, subject);
        startActivity(Intent.createChooser(sendGeneric, ""));
    }
}