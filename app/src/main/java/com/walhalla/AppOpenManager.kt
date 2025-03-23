package com.walhalla

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.AdActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.walhalla.pillfinder.onboarding.OnboardingActivity
import com.walhalla.ui.BuildConfig
import com.walhalla.ui.DLog.d
import java.util.Date

class AppOpenManager
    (private val myApplication: Application, private val AD_UNIT_ID: String) :
    ActivityLifecycleCallbacks {
    private var loadTime: Long = 0
    private var currentActivity: Activity? = null

    /**
     * Shows the ad if one isn't already showing.
     */
    fun showAdIfAvailable() {
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
        if (!isShowingAd && isAdAvailable) {
            Log.d(LOG_TAG, "Will show ad.")

            val fullScreenContentCallback: FullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        // Set the reference to null so isAdAvailable() returns false.
                        this@AppOpenManager.appOpenAd = null
                        isShowingAd = false
                        fetchAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    }

                    override fun onAdShowedFullScreenContent() {
                        isShowingAd = true
                    }
                }

            appOpenAd!!.fullScreenContentCallback = fullScreenContentCallback
            if (currentActivity != null) {
                appOpenAd!!.show(currentActivity!!)
                d("@" + currentActivity!!.javaClass.simpleName)
            }
        } else {
            Log.d(LOG_TAG, "Can not show ad.")
            fetchAd()
        }
    }

    private var appOpenAd: AppOpenAd? = null

    private var loadCallback: AppOpenAd.AppOpenAdLoadCallback? = null

    /**
     * Constructor
     */
    init {
        // Register to be notified of activity state changes
        myApplication.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(
            object : DefaultLifecycleObserver {
                override fun onStart(owner: LifecycleOwner) {
                    // Show the ad (if available) when the app moves to foreground.
                    //if (ACTIVITY_MOVES_TO_FOREGROUND_HANDLE) {
                    if (needRequestAppBanner()) {
                        showAdIfAvailable()
                    }
                    //}
                    //1.
                    d("onStart::::::::")
                }

                override fun onCreate(owner: LifecycleOwner) {
                    //0. DLog.d("onCreate");
                }

                override fun onPause(owner: LifecycleOwner) {
                    if (BuildConfig.DEBUG) {
                        d("###" + "<pause>")
                    }
                    //sessionManager.resetBannerShown();
                }
            }
        )
    }

    private fun needRequestAppBanner(): Boolean {
        return true
    }

    //    public AppOpenManager() {
    //    }
    /**
     * Request an ad
     */
    fun fetchAd() {
        // Have unused ad, no need to fetch another.
        if (isAdAvailable) {
            return
        }

        loadCallback =
            object : AppOpenAd.AppOpenAdLoadCallback() {
                /**
                 * Called when an app open ad has loaded.
                 *
                 * @param ad the loaded app open ad.
                 */
                override fun onAdLoaded(ad: AppOpenAd) {
                    this@AppOpenManager.appOpenAd = ad
                    this@AppOpenManager.loadTime = (Date()).time
                }

                /**
                 * Called when an app open ad has failed to load.
                 *
                 * @param loadAdError the error.
                 */
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error.
                }
            }
        val request = adRequest
        AppOpenAd.load(
            myApplication, AD_UNIT_ID, request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback!!
        )
    }

    private val adRequest: AdRequest
        /**
         * Creates and returns ad request.
         */
        get() = AdRequest.Builder().build()

    /**
     * Utility method to check if ad was loaded more than n hours ago.
     */
    private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
        val dateDifference = (Date()).time - this.loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return (dateDifference < (numMilliSecondsPerHour * numHours))
    }

    val isAdAvailable: Boolean
        /**
         * Utility method that checks if ad exists and can be shown.
         */
        get() = appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4)

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        if (activity is AdActivity
            || activity is OnboardingActivity
        ) {
        } else {
            currentActivity = activity
            if (BuildConfig.DEBUG) {
                //java.lang.Exception: Toast callstack! strTip=###activity.MainActivity
                //Toast.makeText(myApplication, "###" + activity.getLocalClassName(), Toast.LENGTH_SHORT).show();
                d("@@@" + activity.localClassName)
            }
        }
    }

    override fun onActivityResumed(activity: Activity) {
        if (activity is AdActivity
            || activity is OnboardingActivity
        ) {
        } else {
            currentActivity = activity
            if (BuildConfig.DEBUG) {
                //java.lang.Exception: Toast callstack! strTip=
                // @@@Toast.makeText(myApplication, "@@@" + activity.getLocalClassName(), Toast.LENGTH_SHORT).show();
                d("@@@" + activity.localClassName)
            }
        }
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        currentActivity = null
    }

    companion object {
        private var isShowingAd = false

        private const val LOG_TAG = "@@@"
    }
}