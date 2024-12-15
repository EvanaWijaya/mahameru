package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.OnboardingScreen
import com.example.myapplication.ui.screens.WelcomeScreen

class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "onboarding_screen") {
                composable("onboarding_screen") {
                    OnboardingScreen(
                        navController = navController,
                        setOnboardingCompleted = { setOnboardingCompleted() }
                    )
                }
                composable("welcome") {
                    WelcomeScreen(navController = navController)
                }
                composable("login_screen") {
                    LoginScreen(navController = navController)
                }
                composable("main_screen") {
                    LaunchedEffect(Unit) {
                        startActivity(Intent(this@OnboardingActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }

    private fun setOnboardingCompleted() {
        val sharedPref = getSharedPreferences("app_preferences", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("onboarding_completed", true)
            apply()
        }
    }
}
