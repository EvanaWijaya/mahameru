package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.repositories.auth.LoginRepository
import com.example.myapplication.data.repositories.auth.RegisterRepository
import com.example.myapplication.data.services.ApiClient
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.OnboardingScreen
import com.example.myapplication.ui.screens.RegisterScreen
import com.example.myapplication.ui.screens.SplashScreen
import com.example.myapplication.ui.screens.WelcomeScreen
import com.example.myapplication.view_model.AuthViewModel
import com.example.myapplication.view_model.AuthViewModelFactory
import com.example.myapplication.view_model.OnboardingViewModel
import com.example.myapplication.view_model.OnboardingViewModelFactory

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val onboardingViewModel: OnboardingViewModel = viewModel(
        factory = OnboardingViewModelFactory(LocalContext.current)
    )
    val loginRepository = LoginRepository(ApiClient.authService)
    val registerRepository = RegisterRepository(ApiClient.authService)

    val authViewModelFactory = AuthViewModelFactory(loginRepository, registerRepository)
    val authViewModel = viewModel<AuthViewModel>(factory = authViewModelFactory)

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController = navController, onboardingViewModel = onboardingViewModel)
        }
        composable("onboarding") {
            OnboardingScreen(navController = navController, setOnboardingCompleted = { onboardingViewModel.setOnboardingCompleted() })
        }
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController = navController, authViewModel)
        }
        composable("login") {
            LoginScreen(navController = navController, authViewModel)
        }

    }
}