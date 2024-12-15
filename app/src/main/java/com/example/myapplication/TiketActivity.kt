@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.components.CustomTopAppBar
import com.example.myapplication.ui.components.ticket.CustomSectionTitle
import com.example.myapplication.ui.components.ticket.OperationalInfoCard
import com.example.myapplication.ui.components.ticket.TicketCard
import com.example.myapplication.view_model.TicketViewModel

@Composable
fun TicketScreen(navController: NavController, viewModel: TicketViewModel) {
    LaunchedEffect(Unit) {
        viewModel.getTicket(navController.context)
    }
    val ticketsWithTiket = viewModel.getTicketsWithTiket()
    val ticketsWithoutTiket = viewModel.getTicketsWithoutTiket()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Latar belakang abu-abu muda
    ) {
        CustomTopAppBar(title = "Pilihan Tiket", showBackIcon = false)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Menambahkan scrolling
                .padding(16.dp)
        ) {
            OperationalInfoCard()

            Spacer(modifier = Modifier.height(16.dp))

            CustomSectionTitle(title = "Tiket Masuk")
            ticketsWithTiket.forEach { ticket ->
                TicketCard(
                    title = ticket.name,
                    price = ticket.price,
                    features = viewModel.processDescription(ticket.description),
                    onOrderClick = {  }
                )
            }

            CustomSectionTitle(title = "Tiket Camping")
            ticketsWithoutTiket.forEach { ticket ->
                TicketCard(
                    title = ticket.name,
                    price = ticket.price,
                    features = viewModel.processDescription(ticket.description),
                    onOrderClick = { }
                )
            }

        }
    }
}




    @Composable
    fun FeatureRow(feature: String) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ikon Bullet Point
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color(0xFF00897B), shape = RoundedCornerShape(50))
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Teks Fitur
            Text(
                text = feature,
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 20.sp // Menambahkan jarak antar baris
            )
        }
    }

