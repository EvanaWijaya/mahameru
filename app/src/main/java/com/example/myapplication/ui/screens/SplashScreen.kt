package com.example.myapplication.ui.screens

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.utils.SharedPrefs
import com.example.myapplication.view_model.OnboardingViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController, onboardingViewModel: OnboardingViewModel) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        delay(2000)

        val token = SharedPrefs.getToken(context)
        if (token != null) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)

            if (context is ComponentActivity) {
                context.finish()
            }
        } else if (onboardingViewModel.isOnboardingCompleted()) {
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("onboarding") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Box(modifier=Modifier.fillMaxSize()) {
        Box(
            modifier=Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors=listOf(
                            colorResource(id=R.color.colorPrimary) ,
                            colorResource(id=R.color.colorPrimaryVariant)
                        )
                    )
                )
        )

        Image(
            painter=painterResource(id=R.drawable.background_image) ,
            contentDescription=null ,
            contentScale=ContentScale.Crop ,
            modifier=Modifier.fillMaxSize()
        )

        Column(
            modifier=Modifier
                .align(Alignment.Center)
                .fillMaxSize() ,
            horizontalAlignment=Alignment.CenterHorizontally ,
            verticalArrangement=Arrangement.Center
        ) {
            Image(
                painter=painterResource(id=R.drawable.logo_app) ,
                contentDescription=null ,
                modifier=Modifier.size(90.dp , 97.dp)
            )

            Text(
                text=stringResource(id=R.string.app_name) ,
                style=TextStyle(
                    fontSize=50.sp ,
                    fontFamily=FontFamily(Font(R.font.averiaseriflibre_regular)) ,
                    fontWeight=FontWeight(400) ,
                    color=colorResource(id=R.color.colorBackground) ,
                    textAlign=TextAlign.Center
                )
            )

            Text(
                text=stringResource(id=R.string.subtitle) ,
                style=TextStyle(
                    fontSize=15.sp ,
                    fontFamily=FontFamily(Font(R.font.averiaseriflibre_bold)) ,
                    fontWeight=FontWeight(700) ,
                    color=colorResource(id=R.color.colorBackground) ,
                    textAlign=TextAlign.Center
                )
            )
        }
    }
}
