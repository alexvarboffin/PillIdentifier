package com.walhalla.pillfinder.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.onboarding.OnboardingActivity
import com.walhalla.pillfinder.onboarding.OnboardingManager
import com.walhalla.ui.DLog.getAppVersion
import java.util.Locale

class SplashActivity : AppCompatActivity() {
    private var manager: OnboardingManager? = null
    private var small_to_big: Animation? = null

    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_splash)

        val appName = findViewById<TextView>(R.id.app_name)
        appName.visibility = View.VISIBLE
        appName.text = getString(R.string.app_name).uppercase(Locale.getDefault())

        val appVersion = findViewById<TextView>(R.id.tv_info)
        //appVersion.setVisibility(View.VISIBLE);
        appVersion.text = "" + getAppVersion(this)

        val imageView = findViewById<ImageView>(R.id.splashIc)

        small_to_big = AnimationUtils.loadAnimation(this, R.anim.small_to_big_splash)
        imageView.startAnimation(small_to_big)

        manager = OnboardingManager(this)
        //initAds();
        startMain()
    }

    @SuppressLint("WrongConstant")
    private fun startMain() {
        if (manager!!.isOnbording) {
            val intent = Intent(
                this@SplashActivity,
                HealthMainActivity::class.java
            ).setFlags(335544320)
            startActivity(intent)
        } else {
            val openMainActivity = Intent(
                this@SplashActivity,
                OnboardingActivity::class.java
            ).setFlags(335544320)
            startActivity(openMainActivity)
        }
        this@SplashActivity.finish()
    }
}
