package com.walhalla.pillfinder.features

import android.os.Bundle
import com.walhalla.domain.repository.AdvertRepository
import com.walhalla.health.BodyFat.BodyFatHome
import com.walhalla.health.R
import com.walhalla.health.activity.base.InnerAdActivity
import com.walhalla.pillfinder.MyApp
import com.walhalla.pillfinder.fragment.api2.RxNorm
import com.walhalla.pillfinder.fragment.main.IMainView

class BodyFatContainer : InnerAdActivity(), IMainView {
    override fun loadRepository(): AdvertRepository {
        return MyApp.repository!!
    }

    override fun aLayout(): Int {
        return R.layout.activity_container
    }

    override fun aTheme(): Int {
        return R.style.BlueTheme
    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.scrollcontainer, BodyFatHome()).commit()
        }
    }

    
    
    override fun mSnackbar(message: Int) {
    }

    override fun mSnackbar(s: String?) {
        
    }

    override fun hideProgressBar() {
        
    }

    override fun showProgressBar() {
        
    }
}
