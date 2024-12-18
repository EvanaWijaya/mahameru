@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.components.CustomTopAppBar
import com.example.myapplication.ui.components.ticket.CustomSectionTitle
import com.example.myapplication.ui.components.ticket.OperationalInfoCard
import com.example.myapplication.ui.components.ticket.TicketCard
import com.example.myapplication.utils.SharedPrefs
import com.example.myapplication.view_model.TicketViewModel

@Composable
fun TicketScreen(navController: NavController, viewModel: TicketViewModel) {
    LaunchedEffect(Unit) {
        viewModel.getTicket(navController.context)
    }
    val ticketsWithTiket = viewModel.getTicketsWithTiket()
    val ticketsWithoutTiket = viewModel.getTicketsWithoutTiket()
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        CustomTopAppBar(title = "Pilihan Tiket", showBackIcon = false)

        if (isLoading) {
            LoadingIndicator()
        } else {
            if (ticketsWithTiket.isEmpty() && ticketsWithoutTiket.isEmpty()) {
                HandleEmptyResponse(errorMessage ?: "Tidak ada tiket yang tersedia")
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    OperationalInfoCard()

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomSectionTitle(title = "Tiket Masuk")
                    TicketCard(
                        title = "Tiket Wisata",
                        price = "",
                        features = viewModel.processDescription("Masuk ke tempat wisata, Pemandian air panas, Menikmati argowisata, Menikmati buah"),
                        onOrderClick = {
                            navController.navigate("ticketBookingScreen")
                        }


                    )

                    CustomSectionTitle(title = "Tiket Camping")
                    ticketsWithoutTiket.forEach { ticket ->
                        TicketCard(
                            title = ticket.name,
                            price = viewModel.processPrice(ticket.price),
                            features = viewModel.processDescription(ticket.description),
                            onOrderClick = {
                                viewModel.getTicketById(navController.context, ticket.id.toString())
                                navController.navigate("campingBookingScreen/${ticket.id}")
                                viewModel.ticketId.value = ticket.id
                                SharedPrefs.saveTicketId(navController.context, ticket.id.toString())

                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingIndicator() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun HandleEmptyResponse(
    message : String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, style = MaterialTheme.typography.bodyMedium)
    }
}
