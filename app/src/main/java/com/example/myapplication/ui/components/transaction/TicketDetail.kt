package com.example.myapplication.ui.components.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.screens.Poppins

@Composable
fun TicketDetail(title: String, description: String, price: String) {
    Column {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, fontFamily = Poppins, color = Color(0xFF00796B))
        Text(text = description, fontSize = 12.sp, fontFamily = Poppins,color = Color.Black.copy(alpha = 0.9f))
        Text(text = price, fontWeight = FontWeight.Bold, fontFamily = Poppins,color = Color(0xFFFF7F50))
    }
}