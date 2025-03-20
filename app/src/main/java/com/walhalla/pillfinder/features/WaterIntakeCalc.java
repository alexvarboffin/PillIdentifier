package com.walhalla.pillfinder.features;

import android.os.Bundle;

import com.walhalla.health.R;
import com.walhalla.health.WaterIntakeCalc.WaterIntakeFragment;
import com.walhalla.health.activity.base.InnerAdActivity;
import com.walhalla.pillfinder.MyApp;

import com.walhalla.domain.repository.AdvertRepository;

public class WaterIntakeCalc extends InnerAdActivity {

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
        return R.style.OrangeTheme;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.scrollcontainer, new WaterIntakeFragment())
                    .commit();
        }
    }
}
