package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.components.profile.MenuSection
import com.example.myapplication.ui.components.profile.ProfileHeader
import com.example.myapplication.view_model.UserViewModel

@Composable
fun ProfileScreen(navController : NavController , userViewModel : UserViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        ProfileHeader(userViewModel)
        Spacer(modifier = Modifier.height(16.dp))

        MenuSection(navController, userViewModel)

    }
}

