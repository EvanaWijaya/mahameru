package com.example.myapplication.ui.components.ticket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OperationalInfoCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFF7043)),
        shape = RoundedCornerShape(12.dp) ,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ikon di sebelah kiri
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = "Jam Operasional",
                tint = Color.White,
                modifier = Modifier
                    .size(60.dp) // Ukuran ikon lebih besar
            )
            Spacer(modifier = Modifier.width(16.dp)) // Spasi antara ikon dan teks

            // Teks di sebelah kanan
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Jam Operasional Tempat Wisata",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp)) // Jarak kecil antar teks
                Text(
                    text = "Buka, 08.00-18.00\nCamping, 1 x 24 Jam",
                    color = Color.White,
                    fontSize = 14.sp,
                    lineHeight = 20.sp // Jarak antar baris teks
                )
            }
        }
    }
}
