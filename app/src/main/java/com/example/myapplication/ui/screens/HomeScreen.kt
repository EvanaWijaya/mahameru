@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.components.home.BannerSection
import com.example.myapplication.ui.components.home.FacilitySection
import com.example.myapplication.ui.components.home.HomeHeader
import com.example.myapplication.ui.components.home.TicketSection
import com.example.myapplication.view_model.UserViewModel

val Poppins=FontFamily(Font(R.font.poppins_medium , FontWeight.Medium))

@Composable
fun HomeScreen(navController : NavController , userViewModel : UserViewModel) {
    LaunchedEffect(Unit) {
        userViewModel.getProfile(navController.context)
    }
    Column(
        modifier=Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF6F6F6))
    ) {
        HomeHeader(userViewModel.username.value , userViewModel.pictureUrl.value)
        Spacer(modifier = Modifier.height(20.dp))
        BannerSection()
        TicketSection()
        Spacer(modifier=Modifier.height(20.dp))
        FacilitySection(navController)
    }
}




