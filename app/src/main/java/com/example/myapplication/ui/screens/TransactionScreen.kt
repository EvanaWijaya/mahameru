package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.components.CustomTopAppBar
import com.example.myapplication.ui.components.EmptyView
import com.example.myapplication.ui.components.transaction.TicketSummaryCard
import com.example.myapplication.view_model.TicketViewModel

@Composable
fun TransactionScreen(navController : NavController , ticketViewModel : TicketViewModel) {

    val ticketHistory = ticketViewModel.getTicketHistoryResponse.value?.data ?: emptyList()
    LaunchedEffect(Unit) {
        ticketViewModel.getHistoryTicket(navController.context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        CustomTopAppBar(title = "Transaksi Pembayaran", showBackIcon = false)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tiket Saya",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp),
            fontFamily = Poppins
        )

        if (ticketHistory.isEmpty()) {
            EmptyView(
                title = "Belum Ada tiket Yang Dipesan",
                description = "Ayo pesan segera, tiket mu akan \n ditampilkan disini",
                painter = painterResource(id = R.drawable.tiket_kosong)
            )
        } else {
            Spacer(modifier = Modifier.height(8.dp))
            TicketSummaryCard(ticketViewModel)
            Spacer(modifier = Modifier.height(250.dp))

            Text(
                text = "Lihat Riwayat Tiket",
                fontSize = 14.sp,
                color = Color(0xFFFF7F50),
                fontFamily = Poppins,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { navController.navigate("ticketHistory")
                    }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
