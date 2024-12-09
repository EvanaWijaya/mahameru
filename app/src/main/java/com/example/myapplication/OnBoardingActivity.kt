package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "onboarding_screen") {
                // Rute untuk onboarding
                composable("onboarding_screen") {
                    OnboardingScreen(navController = navController)
                }
                // Rute untuk login
                composable("login_screen") {
                    LoginScreen(navController = navController)
                }
                // Rute untuk main screen (setelah login)
                composable("main_screen") {
                    LaunchedEffect(Unit) {
                        startActivity(Intent(this@OnboardingActivity, MainActivity::class.java))
                        finish() // Tutup OnboardingActivity
                    }
                }
            }
        }
    }
}
