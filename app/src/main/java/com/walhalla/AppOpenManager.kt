package com.walhalla;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.walhalla.pillfinder.R;
import com.walhalla.ui.BuildConfig;
import com.walhalla.wads.AppOpenManager5;
import com.walhalla.wads.DLog;

import java.util.Date;

public class AppOpenManager
        implements Application.ActivityLifecycleCallbacks {

    private long loadTime = 0;
    private static boolean isShowingAd = false;

    private Activity currentActivity;

    /**
     * Shows the ad if one isn't already showing.
     */
    public void showAdIfAvailable() {
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
        if (!isShowingAd && isAdAvailable()) {
            Log.d(LOG_TAG, "Will show ad.");

            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Set the reference to null so isAdAvailable() returns false.
                            AppOpenManager.this.appOpenAd = null;
                            isShowingAd = false;
                            fetchAd();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isShowingAd = true;
                        }
                    };

            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            if (currentActivity != null) {
                appOpenAd.show(currentActivity);
                DLog.d("@" + currentActivity.getClass().getSimpleName());
            }

        } else {
            Log.d(LOG_TAG, "Can not show ad.");
            fetchAd();
        }
    }

    private static final String LOG_TAG = "@@@";
    private final String AD_UNIT_ID;

    private AppOpenAd appOpenAd = null;

    private AppOpenAd.AppOpenAdLoadCallback loadCallback;

    private final Application myApplication;

    /**
     * Constructor
     */
    public AppOpenManager(Application myApplication, String ad_unit_id) {
        this.myApplication = myApplication;
        this.AD_UNIT_ID = ad_unit_id;

        // Register to be notified of activity state changes
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(
                new DefaultLifecycleObserver() {
                    @Override
                    public void onStart(@NonNull LifecycleOwner owner) {
                        // Show the ad (if available) when the app moves to foreground.
                        //if (ACTIVITY_MOVES_TO_FOREGROUND_HANDLE) {
                        if (needRequestAppBanner()) {
                            showAdIfAvailable();
                        }
                        //}
                        //1.
                        DLog.d("onStart::::::::");
                    }

                    @Override
                    public void onCreate(@NonNull LifecycleOwner owner) {
                        //0. DLog.d("onCreate");
                    }

                    @Override
                    public void onPause(@NonNull LifecycleOwner owner) {
                        if (BuildConfig.DEBUG) {
                            DLog.d("###" + "<pause>");
                        }
                        //sessionManager.resetBannerShown();
                    }
                }
        );
    }

    private boolean needRequestAppBanner() {
        return true;
    }

//    public AppOpenManager() {
//    }

    /**
     * Request an ad
     */
    public void fetchAd() {
        // Have unused ad, no need to fetch another.
        if (isAdAvailable()) {
            return;
        }

        loadCallback =
                new AppOpenAd.AppOpenAdLoadCallback() {
                    /**
                     * Called when an app open ad has loaded.
                     *
                     * @param ad the loaded app open ad.
                     */
                    @Override
                    public void onAdLoaded(@NonNull AppOpenAd ad) {
                        AppOpenManager.this.appOpenAd = ad;
                        AppOpenManager.this.loadTime = (new Date()).getTime();
                    }

                    /**
                     * Called when an app open ad has failed to load.
                     *
                     * @param loadAdError the error.
                     */
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                    }

                };
        AdRequest request = getAdRequest();
        AppOpenAd.load(
                myApplication, AD_UNIT_ID, request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }

    /**
     * Creates and returns ad request.
     */
    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    /**
     * Utility method to check if ad was loaded more than n hours ago.
     */
    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }

    /**
     * Utility method that checks if ad exists and can be shown.
     */
    public boolean isAdAvailable() {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if (activity instanceof com.google.android.gms.ads.AdActivity
                || activity instanceof com.walhalla.pillfinder.onboarding.OnboardingActivity) {

        } else {
            currentActivity = activity;
            if (BuildConfig.DEBUG) {
                //java.lang.Exception: Toast callstack! strTip=###activity.MainActivity
                //Toast.makeText(myApplication, "###" + activity.getLocalClassName(), Toast.LENGTH_SHORT).show();
                DLog.d("@@@" + activity.getLocalClassName());
            }
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if (activity instanceof com.google.android.gms.ads.AdActivity
                || activity instanceof com.walhalla.pillfinder.onboarding.OnboardingActivity) {

        } else {
            currentActivity = activity;
            if (BuildConfig.DEBUG) {
                //java.lang.Exception: Toast callstack! strTip=
                // @@@Toast.makeText(myApplication, "@@@" + activity.getLocalClassName(), Toast.LENGTH_SHORT).show();
                DLog.d("@@@" + activity.getLocalClassName());
            }
        }
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        currentActivity = null;
    }
}