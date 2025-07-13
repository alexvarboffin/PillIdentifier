package com.walhalla.pillfinder.features

import android.os.Bundle
import com.walhalla.domain.repository.AdvertRepository
import com.walhalla.health.HeartRateCalculator.HeartRateCalculator
import com.walhalla.health.R
import com.walhalla.health.activity.base.InnerAdActivity
import com.walhalla.pillfinder.MyApp

class HeartRate : InnerAdActivity() {
    override fun loadRepository(): AdvertRepository {
        return MyApp.repository!!
    }

    override fun aLayout(): Int {
        return R.layout.activity_container
    }

    override fun aTheme(): Int {
        return R.style.YellowTheme
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.scrollcontainer, HeartRateCalculator())
                .commit()
        }
    }
}
