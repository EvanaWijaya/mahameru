package com.example.myapplication.ui.components.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.screens.Poppins


@Composable
fun TicketDetailBox(ticketAmount: Int, ticketDate: String, name: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$ticketAmount $name",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    fontFamily = Poppins
                )
                Text(
                    text = formatDate(ticketDate) ,
                    fontSize = 14.sp,
                    color = Color(0xFFFF6D00),
                    fontFamily = Poppins
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Uncomment if additional text is needed
            // Text(
            //     text = "1 Dewasa, 1 Anak-anak",
            //     fontSize = 14.sp,
            //     color = Color.Gray
            // )
        }
    }
}