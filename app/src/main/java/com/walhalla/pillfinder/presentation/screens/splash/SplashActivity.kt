package com.walhalla.pillfinder.presentation.screens.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.walhalla.pillfinder.activity.HealthMainActivity
import com.walhalla.pillfinder.onboarding.OnboardingActivity
import com.walhalla.pillfinder.onboarding.OnboardingManager
import com.walhalla.pillfinder.ui.theme.PillFinderTheme

class SplashActivity : ComponentActivity() {
    private lateinit var manager: OnboardingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manager = OnboardingManager(this)

        setContent {
            PillFinderTheme {
                SplashScreen(
                    onSplashFinished = { startMain() }
                )
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun startMain() {
//        if (manager.isOnbording) {
//            startActivity(Intent(this, HealthMainActivity::class.java).apply {
//                flags = 335544320
//            })
//        } else {
//            startActivity(Intent(this, OnboardingActivity::class.java).apply {
//                flags = 335544320
//            })
//        }
//        finish()
    }
} 