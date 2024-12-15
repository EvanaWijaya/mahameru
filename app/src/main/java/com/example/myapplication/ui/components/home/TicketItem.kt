package com.example.myapplication.ui.components.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.screens.Poppins

@Composable
fun TicketItem(title: String, details: String, time: String?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Bullet Point
            Canvas(modifier = Modifier.size(8.dp)) {
                drawCircle(color = Color(0xFFFF7043)) // Warna oranye untuk bullet
            }
            Spacer(modifier = Modifier.width(8.dp))
            // Judul Tiket
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = Poppins ,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Waktu (jika ada)
        if (time != null) {
            Text(
                text = time,
                style = TextStyle(
                    fontFamily = Poppins ,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color(0xFF00C853) // Warna hijau untuk waktu
                ) ,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        // Detail Tiket
        Text(
            text = details,
            style = TextStyle(
                fontFamily = Poppins ,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color.Black
            ) ,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}