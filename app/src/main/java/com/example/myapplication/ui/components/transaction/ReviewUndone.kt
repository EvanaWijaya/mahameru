package com.example.myapplication.ui.components.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.screens.Poppins
import com.example.myapplication.view_model.TicketViewModel

@Composable
fun ReviewUndone(ticketViewModel : TicketViewModel) {
    val ticketHistory = ticketViewModel.getTicketHistoryResponse.value?.data ?: emptyList()
    val ticketReview = ticketViewModel.getTicketReviewResponse.value?.data ?: emptyList()

    val filteredTicketHistory = ticketHistory.filter { history ->
        ticketReview.none { review -> review.transactionTicketId == history.transactionTicketId }
    }
    Box(
        modifier=Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
    ) {
        Column {
            Box(
                modifier=Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart=10.dp , topEnd=10.dp))
                    .background(Color(0xFFFF7F50))
                    .padding(12.dp)
            ) {
                Text(
                    text="Belum di Beri Ulasan" ,
                    fontSize=16.sp ,
                    fontWeight=FontWeight.Bold ,
                    color=Color.White,
                    fontFamily = Poppins
                )
            }

            Spacer(modifier=Modifier.height(6.dp))

            Column(
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                filteredTicketHistory.forEach { ticket ->
                    val price = ticket.price.toDouble().toInt()
                    TicketDetail(
                        title = ticket.name,
                        description = "Fasilitas: ${ticket.description}",
                        price = "Rp $price"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}