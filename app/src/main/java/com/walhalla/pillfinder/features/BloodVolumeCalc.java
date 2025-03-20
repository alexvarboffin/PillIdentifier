package com.walhalla.pillfinder.features;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.walhalla.health.BloodVolumeCalc.BloodVolumeCalcFragment;
import com.walhalla.health.R;
import com.walhalla.health.activity.base.InnerAdActivity;
import com.walhalla.pillfinder.MyApp;

import com.walhalla.domain.repository.AdvertRepository;

public class BloodVolumeCalc extends InnerAdActivity {

    @Override
    protected AdvertRepository loadRepository() {
        return MyApp.repository;
    }

    @Override
    protected int aLayout() {
        return R.layout.activity_container;
    }

    @Override
    protected int aTheme() {
        return R.style.CyanTheme;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.scrollcontainer, new BloodVolumeCalcFragment())
                    .commit();
        }
    }
}