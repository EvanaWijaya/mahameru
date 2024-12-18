@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.components.home.BannerSection
import com.example.myapplication.ui.components.home.FacilitySection
import com.example.myapplication.ui.components.home.HomeHeader
import com.example.myapplication.ui.components.home.TicketSection
import com.example.myapplication.view_model.TicketViewModel
import com.example.myapplication.view_model.UserViewModel

val Poppins=FontFamily(Font(R.font.poppins_medium , FontWeight.Medium))

@Composable
fun HomeScreen(
    navController : NavController ,
    userViewModel : UserViewModel ,
    ticketViewModel : TicketViewModel
) {
    val ticketHistoryResponse=ticketViewModel.getTicketHistoryResponse.value?.data ?: emptyList()
    val isLoading=ticketViewModel.isLoading.value

    LaunchedEffect(Unit) {
        userViewModel.getProfile(navController.context)
        ticketViewModel.getHistoryTicket(navController.context)
    }
    Column(
        modifier=Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF6F6F6))
    ) {
        HomeHeader(userViewModel.username.value , userViewModel.pictureUrl.value)
        Spacer(modifier=Modifier.height(20.dp))
        BannerSection()
        when {
            isLoading -> {
                androidx.compose.material3.CircularProgressIndicator(
                    modifier=Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)

                )
            }

            ticketHistoryResponse.isEmpty() -> {
                androidx.compose.foundation.layout.Box(
                    modifier=Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                }
            }

            else -> {
                Text(
                    text="Tiket Saya" ,
                    style=TextStyle(
                        fontFamily=Poppins ,
                        fontWeight=FontWeight.Medium ,
                        fontSize=20.sp ,
                        color=Color.Black
                    ) ,
                    modifier=Modifier.padding(horizontal=16.dp , vertical=8.dp)
                )
                Spacer(modifier=Modifier.height(8.dp))
                TicketSection(ticketHistoryList=ticketHistoryResponse)
                Spacer(modifier=Modifier.height(20.dp))

            }
        }
        FacilitySection(navController)
    }
}




