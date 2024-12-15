package com.example.myapplication.ui.components.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.screens.Poppins
import com.example.myapplication.R

@Composable
fun TicketSection() {
    // Container untuk keseluruhan bagian
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Judul "Reservasi Tiket"
        Card(
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp) ,
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFF7043)), // Warna oranye header
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reservasi Tiket",
                    style = TextStyle(
                        fontFamily = Poppins ,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    ) ,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.barcode) , // Ikon layar penuh
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Isi Tiket
        Card(
            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp) ,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Tiket Masuk Dewasa
                TicketItem(
                    title = "Tiket Masuk Dewasa",
                    details = "Fasilitas: Pemandian Air Panas, Kebun Buah",
                    time = null // Tidak ada waktu untuk tiket ini
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Paket Camping Biasa
                TicketItem(
                    title = "Paket Camping Biasa",
                    details = "Fasilitas: Tenda, Matras, Sleeping Bag, Pemandian Air Panas, Kebun Buah",
                    time = "00.00.00"
                )
            }
        }
    }
}

