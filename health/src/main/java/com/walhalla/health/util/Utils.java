package com.walhalla.health.util;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;

public class Utils {

    public static boolean isOk(InterstitialAd mInterstitialAd) {
        return mInterstitialAd != null;// && mInterstitialAd.isLoaded();
    }

    public static void aaa(InterstitialAd mInterstitialAd, AdListener adListener) {
    }
}
