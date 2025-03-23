package com.walhalla.health.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.walhalla.ui.DLog;

public class PrefData {

    public static String MY_PREFS = "healthPref";

    public static int DEFAULT_AGE = 25;
    //private final Context context;
    private final SharedPreferences preferences;


    public PrefData(Context context) {
        //this.context = context;
        this.preferences = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
    }

    // !!! NOT ASYNC !!!
    public void setAge(int age) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(CheckKey.CH_AGE, age);
        editor.commit();
    }

    public int getAge() {
        return preferences.getInt(CheckKey.CH_AGE, DEFAULT_AGE);
    }

    public int getHeight() {
        return preferences.getInt(CheckKey.CH_HEIGHT, -1);
    }

    //+
    private void setHeight(String Height) {
        preferences.edit().putString(CheckKey.CH_HEIGHT, Height).apply();
    }

    public int getWeight() {
        return preferences.getInt(CheckKey.CH_WEIGHT, -1);
    }

    //+
    private void setWeight(int weight) {
        preferences.edit().putInt(CheckKey.CH_WEIGHT, weight).apply();
    }

    public void setAge(String string) {
        try {
            setAge(Integer.parseInt(string));
        } catch (NumberFormatException e) {
            DLog.handleException(e);
        }
    }


    //==============


    private static class CheckKey {
        public static final String CH_HEIGHT = "OHR8NxMwCffUTckx2VVpAA==";
        public static final String CH_AGE = "CZBU+IPgPLyQxlpFbeBMnQ==";
        public static final String CH_WEIGHT = "Cpf/Idauh75OI3PA7efbTg==";
    }

}
