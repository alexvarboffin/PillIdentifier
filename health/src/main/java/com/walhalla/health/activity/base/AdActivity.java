package com.walhalla.health.activity.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.walhalla.health.BuildConfig;
import com.walhalla.health.R;
import com.walhalla.ui.MainPresenter;


import java.util.ArrayList;
import java.util.List;

public abstract class AdActivity extends AppCompatActivity {

    private static final boolean ADS_ENABLED = true;

    protected Toolbar toolbar;
    protected MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        presenter = new MainPresenter(this);
        setContentView(aLayout());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAd = findViewById(R.id.adView);
        if (ADS_ENABLED) {
            loadAd();
        } else {
            mAd.setVisibility(View.GONE);
        }
    }

    protected abstract int aLayout();

    public static final String TAG = "@@@";

    /**
     * The {@link AdView} that will display the ad.
     * This must be set before an ad can be loaded.
     */
    protected AdView mAd;

    @Override
    protected void onPause() {
        if (ADS_ENABLED && mAd != null) {
            mAd.pause();
        } else {
            Log.d(TAG, "mAd has not been set");
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ADS_ENABLED && mAd != null) {
            mAd.resume();
        } else {
            Log.d(TAG, "mAd has not been set");
        }
    }

    @Override
    protected void onDestroy() {
        if (ADS_ENABLED && mAd != null) {
            mAd.destroy();
        } else {
            Log.d(TAG, "mAd has not been set");
        }

        super.onDestroy();
    }


    protected void loadAd() {
        if (mAd == null) {
            Log.e(TAG, "mAd must be set before loading an ad");
            return;
        }

//        String[] testDeviceIds = getResources().getStringArray(R.array.admob_test_device_ids);
//
        AdRequest.Builder builder = new AdRequest.Builder();
//        builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
//        for (String id : testDeviceIds) {
//            builder.addTestDevice(id);
//        }
//        builder.tagForChildDirectedTreatment(getResources()
//                .getBoolean(R.bool.child_directed_treatment));

        mAd.loadAd(builder.build());

    }
}
