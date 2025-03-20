package com.ratingdialog.simple;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.walhalla.ui.DLog;
import com.walhalla.ui.R;

import static android.content.Context.MODE_PRIVATE;


public class UiRatingDialog {
    private Context context;

    private Dialog dialog;
    private RelativeLayout main;
    private ImageView ratingFace;
    private RotationRatingBar rotationratingbar_main;
    private SharedPreferences preferences;
    private int defRating = 0;
    private RatingDialogInterFace mRatingDialogListener;

    public UiRatingDialog(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("rateData", MODE_PRIVATE);

        dialog = new Dialog(this.context);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rate_ex);
        if (dialog.getWindow() != null) {
            dialog.getWindow()
                    .setBackgroundDrawable(new ColorDrawable(
                            0 //Color.TRANSPARENT
                    ));
        }

        final TextView text = dialog.findViewById(R.id.rate_text);
        text.setText(context.getString(R.string.rateme__dialog_first_message, context.getString(R.string.app_name)));

        ImageView btnCancel = dialog.findViewById(R.id.btnCacncel);
        ratingFace = dialog.findViewById(R.id.ratingFace);
        main = dialog.findViewById(R.id.main);
        rotationratingbar_main = dialog.findViewById(R.id.rotationratingbar_main);
        TextView btnSubmit = dialog.findViewById(R.id.ok);
        dialog.setOnDismissListener(dialogInterface -> {
            main.setRotation(0);
            main.setAlpha(0);
            main.setScaleY(0);
            main.setScaleX(0);
            main.clearAnimation();
            rotationratingbar_main.setVisibility(View.INVISIBLE);
            if (mRatingDialogListener != null) {
                mRatingDialogListener.onDismiss();
            }
        });
        btnCancel.setOnClickListener(view -> closeDialog());

        rotationratingbar_main.setOnRatingChangeListener((ratingBar, rating) -> {
            if (ratingBar.getRating() < 4.0f) {
                setRatingFace(false);
            } else {
                setRatingFace(true);
            }
            if (mRatingDialogListener != null) {
                mRatingDialogListener.onRatingChanged(rotationratingbar_main.getRating());
            }
        });

        btnSubmit.setOnClickListener(view -> main.animate().scaleY(0).scaleX(0).alpha(0).rotation(-1800).setDuration(600).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                dialog.dismiss();
                main.clearAnimation();
                rotationratingbar_main.setVisibility(View.INVISIBLE);
                if (DLog.nonNull(mRatingDialogListener)) {
                    mRatingDialogListener.onSubmit(rotationratingbar_main.getRating());
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start());

    }

    public void showDialog() {
        boolean isEnable = preferences.getBoolean("enb", true);
        if (isEnable) {
            dialog.show();
            rotationratingbar_main.clearAnimation();
            rotationratingbar_main.setRating(defRating);
            setRatingFace(true);
            main.animate().scaleY(1).scaleX(1).rotation(1800).alpha(1).setDuration(600).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    main.clearAnimation();
                    rotationratingbar_main.setVisibility(View.VISIBLE);
                    rotationratingbar_main.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bounce_amn));
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            }).start();
        }
    }


    public void setEnable(boolean isEnable) {
        preferences.edit().putBoolean("enb", isEnable).apply();
    }

    public boolean getEnable() {
        return preferences.getBoolean("enb", true);
    }

    private void setRatingFace(boolean isTrue) {
        if (isTrue) {
            ratingFace.setImageResource(R.drawable.favorite);
        } else {
            ratingFace.setImageResource(R.drawable.favorite2);
        }

    }

    private void closeDialog() {
        main.animate().scaleY(0).scaleX(0).alpha(0).rotation(-1800).setDuration(600).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                dialog.dismiss();
                main.clearAnimation();
                rotationratingbar_main.setVisibility(View.INVISIBLE);
                if (mRatingDialogListener != null) {
                    mRatingDialogListener.onDismiss();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();
    }

    public void setDefaultRating(int defaultRating) {
        this.defRating = defaultRating;
    }

    public void setRatingDialogListener(RatingDialogInterFace mRatingDialogListener) {
        this.mRatingDialogListener = mRatingDialogListener;
    }

    public interface RatingDialogInterFace {
        void onDismiss();

        void onSubmit(float rating);

        void onRatingChanged(float rating);
    }

}
