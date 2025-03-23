package com.walhalla.health.activity.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.walhalla.health.R
import com.walhalla.ui.MainPresenter

abstract class AdActivity : AppCompatActivity() {
    protected var toolbar: Toolbar? = null
    protected var presenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        presenter = MainPresenter(this)
        setContentView(aLayout())

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        mAd = findViewById(R.id.adView)
        if (ADS_ENABLED) {
            loadAd()
        } else {
            mAd.setVisibility(View.GONE)
        }
    }

    protected abstract fun aLayout(): Int

    /**
     * The [AdView] that will display the ad.
     * This must be set before an ad can be loaded.
     */
    protected lateinit var mAd: AdView

    override fun onPause() {
        if (ADS_ENABLED && mAd != null) {
            mAd.pause()
        } else {
            Log.d(TAG, "mAd has not been set")
        }
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (ADS_ENABLED && mAd != null) {
            mAd.resume()
        } else {
            Log.d(TAG, "mAd has not been set")
        }
    }

    override fun onDestroy() {
        if (ADS_ENABLED && mAd != null) {
            mAd.destroy()
        } else {
            Log.d(TAG, "mAd has not been set")
        }

        super.onDestroy()
    }


    protected fun loadAd() {
        if (mAd == null) {
            Log.e(TAG, "mAd must be set before loading an ad")
            return
        }

//        String[] testDeviceIds = getResources().getStringArray(R.array.admob_test_device_ids);
//
        val builder = AdRequest.Builder()

        //        builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
//        for (String id : testDeviceIds) {
//            builder.addTestDevice(id);
//        }
//        builder.tagForChildDirectedTreatment(getResources()
//                .getBoolean(R.bool.child_directed_treatment));
        mAd!!.loadAd(builder.build())
    }

    companion object {
        private const val ADS_ENABLED = true

        const val TAG: String = "@@@"
    }
}
