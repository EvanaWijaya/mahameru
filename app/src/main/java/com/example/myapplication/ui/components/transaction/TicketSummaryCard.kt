package com.example.myapplication.ui.components.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.screens.Poppins
import com.example.myapplication.view_model.TicketViewModel

@Composable
fun TicketSummaryCard(ticketViewModel : TicketViewModel) {
    val ticketHistory=ticketViewModel.getTicketHistoryResponse.value?.data ?: emptyList()
    val totalPrice=ticketHistory.sumOf { ticket ->
        ticket.price.toDouble().toInt()
    }
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.background(Color.White)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF00796B))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Pembayaran Berhasil",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                ticketHistory.forEach { ticket ->
                    val price = ticket.price.toDouble().toInt()
                    TicketItem(
                        title = ticket.name,
                        facility = "Fasilitas: ${ticket.description}",
                        price = "Rp$price"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        fontFamily = Poppins
                    )
                    Text(
                        text = "Rp$totalPrice",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        fontFamily = Poppins
                    )
                }
            }
        }
    }
}