package com.walhalla.pillfinder;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

/**
 * Created by ponch on 14.03.17.
 */

public class MyLocalStorage {
    private static MyLocalStorage instance = null;
    private SharedPreferences app_prefs;


    @SuppressLint("CommitPrefEdits")
    private MyLocalStorage() {
        this.app_prefs = MyApp.sharedPreferences;
    }

    public synchronized static MyLocalStorage getInstance() {
        if (instance == null) {
            instance = new MyLocalStorage();
        }

        return instance;
    }

    private SharedPreferences sharedPreferences() {
        return app_prefs;
    }


}
