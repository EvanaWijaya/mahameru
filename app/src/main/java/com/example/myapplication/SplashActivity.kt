package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen {
                // Navigasi ke Onboarding atau Main Activity setelah Splash selesai
                navigateToNextScreen()
            }
        }
    }

    private fun navigateToNextScreen() {
        val isOnboardingComplete = PreferencesHelper.isOnboardingComplete(this)
        val nextActivity = if (isOnboardingComplete) MainActivity::class.java else OnboardingActivity::class.java
        val intent = Intent(this, nextActivity)
        startActivity(intent)
        finish()
    }
}
