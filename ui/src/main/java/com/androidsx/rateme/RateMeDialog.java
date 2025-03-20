package com.androidsx.rateme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.UConst;
import com.walhalla.ui.DLog;
import com.walhalla.ui.R;
import com.walhalla.ui.observer.RateAppModule;

import static com.UConst.MARKET_CONSTANT;


public class RateMeDialog extends DialogFragment {

    private final static String RESOURCE_NAME = "titleDivider";
    private final static String DEFAULT_TYPE_RESOURCE = "id";
    private final static String DEFAULT_PACKAGE = "android";

    // Views
    private View mView;
    private View tView;
    private Button close;
    private RatingBar ratingBar;
    private LayerDrawable stars;
    private Button rateMe;
    private Button noThanks;
    private Button share;

    // Configuration. It all comes from the builder. On, on resume, from the saved bundle
    private String appPackageName;
    private String appName;
    private int headerBackgroundColor;
    private int headerTextColor;
    private int bodyBackgroundColor;
    private int bodyTextColor;
    private boolean feedbackByEmailEnabled;
    private String feedbackEmail;
    private boolean showShareButton;
    private int appIconResId;
    private int lineDividerColor;
    private int rateButtonBackgroundColor;
    private int rateButtonTextColor;
    private int rateButtonPressedBackgroundColor;
    private int defaultStarsSelected;
    private int iconCloseColor;
    private int iconShareColor;
    private boolean showOKButtonByDefault;
    private OnRatingListener onRatingListener;

    private boolean new_rate_module = true;
    private Activity activity;

    public RateMeDialog() {
        // Empty constructor, required for exo_controls_pause/resume
    }

    public RateMeDialog(String appPackageName,
                        String appName,
                        int headerBackgroundColor,
                        int headerTextColor,
                        int bodyBackgroundColor,
                        int bodyTextColor,
                        boolean feedbackByEmailEnabled,
                        String feedbackEmail,
                        boolean showShareButton,
                        int appIconResId,
                        int lineDividerColor,
                        int rateButtonBackgroundColor,
                        int rateButtonTextColor,
                        int rateButtonPressedBackgroundColor,
                        int defaultStarsSelected,
                        int iconCloseColor,
                        int iconShareColor,
                        boolean showOKButtonByDefault,
                        OnRatingListener onRatingListener) {
        this.appPackageName = appPackageName;
        this.appName = appName;
        this.headerBackgroundColor = headerBackgroundColor;
        this.headerTextColor = headerTextColor;
        this.bodyBackgroundColor = bodyBackgroundColor;
        this.bodyTextColor = bodyTextColor;
        this.feedbackByEmailEnabled = feedbackByEmailEnabled;
        this.feedbackEmail = feedbackEmail;
        this.showShareButton = showShareButton;
        this.appIconResId = appIconResId;
        this.lineDividerColor = lineDividerColor;
        this.rateButtonBackgroundColor = rateButtonBackgroundColor;
        this.rateButtonTextColor = rateButtonTextColor;
        this.rateButtonPressedBackgroundColor = rateButtonPressedBackgroundColor;
        this.defaultStarsSelected = defaultStarsSelected;
        this.iconCloseColor = iconCloseColor;
        this.iconShareColor = iconShareColor;
        this.showOKButtonByDefault = showOKButtonByDefault;
        this.onRatingListener = onRatingListener;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initializeUiFields();
        DLog.d("All components were initialized successfully");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        setIconsTitleColor(iconCloseColor, iconShareColor);

        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (rating >= 4.0) {
                rateMe.setVisibility(View.VISIBLE);
                noThanks.setVisibility(View.GONE);
            } else if (rating > 0.0) {
                noThanks.setVisibility(View.VISIBLE);
                rateMe.setVisibility(View.GONE);
            } else {
                noThanks.setVisibility(View.GONE);
                rateMe.setVisibility(View.GONE);
            }
            defaultStarsSelected = (int) rating;
        });
        ratingBar.setStepSize(1.0f);
        ratingBar.setRating((float) defaultStarsSelected);
        configureButtons();

        try {
            close.setOnClickListener(v -> {
                RateAppModule.appRated(getActivity(), false); //not rated
                dismiss();
                RateMeDialogTimer.clearSharedPreferences(getActivity());
                DLog.d("Clear the shared preferences");
                RateMeDialogTimer.setOptOut(getActivity(), true);
                onRatingListener.onRating(OnRatingListener.RatingAction.DISMISSED_WITH_CROSS, ratingBar.getRating());
            });
        } catch (Exception e) {
            DLog.handleException(e);
            dismiss();
        }

        try {
            share.setVisibility(showShareButton ? View.VISIBLE : View.GONE);
            share.setOnClickListener(v -> {
                startActivity(shareApp(appPackageName));
                DLog.d("Share the application");
                onRatingListener.onRating(OnRatingListener.RatingAction.SHARED_APP, ratingBar.getRating());

            });
        } catch (Exception e) {
            DLog.d("Error showing share button " + e);
            dismiss();
        }

        return builder.setView(mView).setCustomTitle(tView).setCancelable(false).create();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            this.appPackageName = savedInstanceState.getString("appPackageName");
            this.appName = savedInstanceState.getString("appName");
            this.headerBackgroundColor = savedInstanceState.getInt("headerBackgroundColor");
            this.headerTextColor = savedInstanceState.getInt("headerTextColor");
            this.bodyBackgroundColor = savedInstanceState.getInt("bodyBackgroundColor");
            this.bodyTextColor = savedInstanceState.getInt("bodyTextColor");
            this.feedbackByEmailEnabled = savedInstanceState.getBoolean("feedbackByEmailEnabled");
            this.feedbackEmail = savedInstanceState.getString("feedbackEmail");
            this.showShareButton = savedInstanceState.getBoolean("showShareButton");
            this.appIconResId = savedInstanceState.getInt("appIconResId");
            this.lineDividerColor = savedInstanceState.getInt("lineDividerColor");
            this.rateButtonBackgroundColor = savedInstanceState.getInt("rateButtonBackgroundColor");
            this.rateButtonTextColor = savedInstanceState.getInt("rateButtonTextColor");
            this.rateButtonPressedBackgroundColor = savedInstanceState.getInt("rateButtonPressedBackgroundColor");
            this.defaultStarsSelected = savedInstanceState.getInt("defaultStarsSelected");
            this.iconCloseColor = savedInstanceState.getInt("iconCloseColor");
            this.iconShareColor = savedInstanceState.getInt("iconShareColor");
            this.showOKButtonByDefault = savedInstanceState.getBoolean("showOKButtonByDefault");
            this.onRatingListener = savedInstanceState.getParcelable("onRatingListener");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("appPackageName", appPackageName);
        outState.putString("appName", appName);
        outState.putInt("headerBackgroundColor", headerBackgroundColor);
        outState.putInt("headerTextColor", headerTextColor);
        outState.putInt("bodyBackgroundColor", bodyBackgroundColor);
        outState.putInt("bodyTextColor", bodyTextColor);
        outState.putBoolean("feedbackByEmailEnabled", feedbackByEmailEnabled);
        outState.putString("feedbackEmail", feedbackEmail);
        outState.putBoolean("showShareButton", showShareButton);
        outState.putInt("appIconResId", appIconResId);
        outState.putInt("lineDividerColor", lineDividerColor);
        outState.putInt("rateButtonBackgroundColor", rateButtonBackgroundColor);
        outState.putInt("rateButtonTextColor", rateButtonTextColor);
        outState.putInt("rateButtonPressedBackgroundColor", rateButtonPressedBackgroundColor);
        outState.putInt("defaultStarsSelected", defaultStarsSelected);
        outState.putInt("iconCloseColor", iconCloseColor);
        outState.putInt("iconShareColor", iconShareColor);
        outState.putBoolean("showOKButtonByDefault", showOKButtonByDefault);
        outState.putParcelable("onRatingListener", onRatingListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        final int titleDividerId = getResources().getIdentifier(RESOURCE_NAME, DEFAULT_TYPE_RESOURCE, DEFAULT_PACKAGE);
        final View titleDivider = getDialog().findViewById(titleDividerId);
        if (titleDivider != null) {
            titleDivider.setBackgroundColor(lineDividerColor);
        }
    }

    private void initializeUiFields() {
        // Main Dialog
        mView = View.inflate(getActivity(), R.layout.rateme__dialog_message, null);
        tView = View.inflate(getActivity(), R.layout.rateme__dialog_title, null);
        close = tView.findViewById(R.id.buttonClose);
        share = tView.findViewById(R.id.buttonShare);
        TextView txt0 = mView.findViewById(R.id.rating_dialog_message);
        if (getContext() != null) {
            Resources res = getContext().getResources();
            String message = String.format(
                    res.getString(R.string.rateme__dialog_first_message), res.getString(R.string.app_name));
            txt0.setText(message);
        }

        rateMe = mView.findViewById(R.id.buttonRateMe);
        noThanks = mView.findViewById(R.id.buttonThanks);

        ratingBar = mView.findViewById(R.id.ratingBar);
        stars = (LayerDrawable) ratingBar.getProgressDrawable();
        mView.setBackgroundColor(bodyBackgroundColor);
        tView.setBackgroundColor(headerBackgroundColor);
        ((TextView) tView.findViewById(R.id.dialog_title)).setTextColor(headerTextColor);
        final View iconImage = mView.findViewById(R.id.app_icon_dialog_rating);
        if (appIconResId == 0) {
            iconImage.setVisibility(View.GONE);
        } else {
            ((ImageView) iconImage).setImageResource(appIconResId);
            iconImage.setVisibility(View.VISIBLE);
        }
        txt0.setTextColor(bodyTextColor);

        rateMe.setBackgroundColor(rateButtonBackgroundColor);
        noThanks.setBackgroundColor(rateButtonBackgroundColor);
        rateMe.setTextColor(rateButtonTextColor);
        noThanks.setTextColor(rateButtonTextColor);

    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    private void configureButtons() {
        rateMe.setOnClickListener(v -> {
            RateAppModule.appRated(getActivity(), true); //rated
//            if (new_rate_module) {
//                //ReviewManager manager = ReviewManagerFactory.create(this.getContext());
//                ReviewManager manager = new FakeReviewManager(activity);
//
//                Task<ReviewInfo> request = manager.requestReviewFlow();
//                request.addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        // We can get the ReviewInfo object
//                        ReviewInfo reviewInfo = task.getResult();
//                        Task<Void> flow = manager.launchReviewFlow(activity, reviewInfo);
//                        flow.addOnCompleteListener(task0 -> {
//                            if (task0.isSuccessful()) {
//                                DLog.d("@@@@@@@@@@@" + task0.getResult());
//                                //rateApp0();
//                            } else {
//                                rateApp0();
//                            }
//                        });
//                    } else {
//                        // There was some problem, continue regardless of the result.
//                        rateApp0();
//                    }
//                });
//
//            } else {
//                rateApp0();
//            }
            rateApp0();

            DLog.d("Yes: open the Google Play Store");
            RateMeDialogTimer.setOptOut(getActivity(), true);
            onRatingListener.onRating(OnRatingListener.RatingAction.HIGH_RATING_WENT_TO_GOOGLE_PLAY, ratingBar.getRating());
            dismiss();
        });

        noThanks.setOnClickListener(v -> {
            RateAppModule.appRated(getActivity(), true); //rated
            dismiss();

            if (feedbackByEmailEnabled) {
                DialogFragment dialogMail = FeedbackDialog.newInstance(feedbackEmail,
                        appName,
                        headerBackgroundColor,
                        bodyBackgroundColor,
                        headerTextColor,
                        bodyTextColor,
                        appIconResId,
                        lineDividerColor,
                        rateButtonTextColor,
                        rateButtonBackgroundColor,
                        ratingBar.getRating(),
                        onRatingListener);
                dialogMail.show(getFragmentManager(), "feedbackByEmailEnabled");
                DLog.d("No: open the feedback dialog");
            } else {
                onRatingListener.onRating(OnRatingListener.RatingAction.LOW_RATING, ratingBar.getRating());
            }
            RateMeDialogTimer.setOptOut(getActivity(), true);
        });
    }

    //not attached to Activity
    private void rateApp0() {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_CONSTANT + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(UConst.GOOGLE_PLAY_CONSTANT + appPackageName)));
        }catch (Exception e){
            DLog.handleException(e);
        }
    }

    private Intent shareApp(String appPackageName) {
        Intent shareApp = new Intent();
        shareApp.setAction(Intent.ACTION_SEND);
        try {
            shareApp.putExtra(Intent.EXTRA_TEXT, MARKET_CONSTANT + appPackageName);
        } catch (android.content.ActivityNotFoundException anfe) {
            shareApp.putExtra(Intent.EXTRA_TEXT, UConst.GOOGLE_PLAY_CONSTANT + appPackageName);
        }
        shareApp.setType("text/plain");
        return shareApp;
    }

    private void setIconsTitleColor(int colorClose, int colorShare) {
        ContextCompat.getDrawable(getContext(),android.R.drawable.ic_menu_close_clear_cancel)
                .setColorFilter(new LightingColorFilter(colorClose, colorClose));
        ContextCompat.getDrawable(getContext(),android.R.drawable.ic_menu_share)
                .setColorFilter(new LightingColorFilter(colorShare, colorShare));
    }

    public static class Builder {
        private static final int LINE_DIVIDER_COLOR_UNSET = -1;

        private static final int C_W = 0xFFFFFFFF; //Color white
        private static final int C_B = 0xFF000000; //black

        private final String appPackageName;
        private final String appName;
        private int headerBackgroundColor = C_B;
        private int headerTextColor = C_W;
        private int bodyBackgroundColor = Color.DKGRAY;
        private int bodyTextColor = C_W;
        private boolean feedbackByEmailEnabled = false;
        private String feedbackEmail = null;
        private boolean showShareButton = false;
        private int appIconResId = 0;
        private int lineDividerColor = LINE_DIVIDER_COLOR_UNSET;
        private int rateButtonBackgroundColor = C_B;
        private int rateButtonTextColor = C_W;
        private int rateButtonPressedBackgroundColor = Color.GRAY;
        private int defaultStarsSelected = 0;
        private int iconCloseColor = C_W;
        private int iconShareColor = C_W;
        private boolean showOKButtonByDefault = true;
        private OnRatingListener onRatingListener = new DefaultOnRatingListener();

        /**
         * @param appPackageName package name of the application. Available in {@code Context.getPackageName()}.
         * @param appName        name of the application. Typically {@code getResources().getString(R.string.app_name)}.
         */
        public Builder(String appPackageName, String appName) {
            this.appPackageName = appPackageName;
            this.appName = appName;
        }

        public Builder setHeaderBackgroundColor(int headerBackgroundColor) {
            this.headerBackgroundColor = headerBackgroundColor;
            return this;
        }

        public Builder setHeaderTextColor(int headerTextColor) {
            this.headerTextColor = headerTextColor;
            return this;
        }

        public Builder setBodyBackgroundColor(int bodyBackgroundColor) {
            this.bodyBackgroundColor = bodyBackgroundColor;
            return this;
        }

        public Builder setBodyTextColor(int bodyTextColor) {
            this.bodyTextColor = bodyTextColor;
            return this;
        }

        /**
         * Enables a second dialog that opens if the rating is low, from which the user can send
         * an e-mail to the provided e-mail address.
         */
        public Builder enableFeedbackByEmail(String email) {
            this.feedbackByEmailEnabled = true;
            this.feedbackEmail = email;
            return this;
        }

        public Builder setShowShareButton(boolean showShareButton) {
            this.showShareButton = showShareButton;
            return this;
        }

        public Builder setLineDividerColor(int lineDividerColor) {
            this.lineDividerColor = lineDividerColor;
            return this;
        }

        /**
         * Sets an icon to be placed on the left-hand side of the dialog. No icon will show up
         * otherwise.
         * <p>
         * Careful: before 3.0.0, there was a default icon.
         */
        public Builder showAppIcon(int appIconResId) {
            this.appIconResId = appIconResId;
            return this;
        }

        public Builder setRateButtonBackgroundColor(int rateButtonBackgroundColor) {
            this.rateButtonBackgroundColor = rateButtonBackgroundColor;
            return this;
        }

        public Builder setRateButtonTextColor(int rateButtonTextColor) {
            this.rateButtonTextColor = rateButtonTextColor;
            return this;
        }

        public Builder setRateButtonPressedBackgroundColor(int rateButtonPressedBackgroundColor) {
            this.rateButtonPressedBackgroundColor = rateButtonPressedBackgroundColor;
            return this;
        }

        public Builder setDefaultNumberOfStars(int numStars) {
            this.defaultStarsSelected = numStars;
            return this;
        }

        public Builder setIconCloseColorFilter(int iconColor) {
            this.iconCloseColor = iconColor;
            return this;
        }

        public Builder setIconShareColorFilter(int iconColor) {
            this.iconShareColor = iconColor;
            return this;
        }

        public Builder setShowOKButtonByDefault(boolean visible) {
            this.showOKButtonByDefault = visible;
            return this;
        }

        /**
         * @see com.androidsx.rateme.OnRatingListener
         */
        public Builder setOnRatingListener(OnRatingListener onRatingListener) {
            this.onRatingListener = onRatingListener;
            return this;
        }

        public RateMeDialog build() {
            if (lineDividerColor == LINE_DIVIDER_COLOR_UNSET) {
                lineDividerColor = headerBackgroundColor;
            }
            return new RateMeDialog(appPackageName,
                    appName,
                    headerBackgroundColor,
                    headerTextColor,
                    bodyBackgroundColor,
                    bodyTextColor,
                    feedbackByEmailEnabled,
                    feedbackEmail,
                    showShareButton,
                    appIconResId,
                    lineDividerColor,
                    rateButtonBackgroundColor,
                    rateButtonTextColor,
                    rateButtonPressedBackgroundColor,
                    defaultStarsSelected,
                    iconCloseColor,
                    iconShareColor,
                    showOKButtonByDefault,
                    onRatingListener);
        }
    }
}
