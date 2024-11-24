package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnboardingScreen {
                completeOnboarding()
            }
        }
    }

    private fun completeOnboarding() {
        PreferencesHelper.setOnboardingComplete(this, true) // Tandai onboarding selesai
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Tutup OnboardingActivity
    }
}
