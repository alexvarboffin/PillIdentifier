package com.walhalla.pillfinder.features;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.walhalla.bloodAlcohol.BloodAlcoholFragment;
import com.walhalla.domain.repository.AdvertRepository;
import com.walhalla.health.R;
import com.walhalla.health.activity.base.InnerAdActivity;
import com.walhalla.pillfinder.MyApp;


public class BloodAlcoholContent0 extends InnerAdActivity {


    @Override
    protected int aLayout() {
        return R.layout.activity_container;
    }

    @Override
    protected int aTheme() {
        return R.style.GrayTheme;
    }

    @Override
    protected AdvertRepository loadRepository() {
        return MyApp.repository;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.scrollcontainer, new BloodAlcoholFragment())
                    .commit();
        }
    }
}
