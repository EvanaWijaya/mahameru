package com.example.myapplication.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun BannerSection() {
    val listState = rememberLazyListState() // State untuk memantau posisi scroll

    Column(modifier = Modifier.fillMaxWidth()) {
        LazyRow(
            state = listState,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            items(3) { index ->
                when (index) {
                    0 -> BannerCard(
                        title = "Pesan Tiket Jadi Lebih Mudah",
                        subtitle = "Dapatkan harga menarik dengan pesan tiket online",
                        price = "Mulai dari 5.000 aja!",
                        icon = R.drawable.megaphone,
                        backgroundColor = Color(0xFF9C2D1B)
                    )
                    1 -> BannerCard(
                        title = "Paket Camping Komplit",
                        subtitle = "Kemah dengan alat yang lengkap + sarapan gratis",
                        price = "Cuma 250.000 aja!",
                        icon = R.drawable.camping,
                        backgroundColor = Color(0xFF9C2D1B)
                    )
                    2 -> BannerCard(
                        title = "ATP Glamping",
                        subtitle = "Mau liburan bareng keluarga dengan nyaman dan lengkap?\nYuk coba paket ini",
                        price = "Cuma 500.000 aja!",
                        icon = R.drawable.camping,
                        backgroundColor = Color(0xFF9C2D1B)
                    )
                }
            }
        }

        // Indikator Dots
        val visibleItemIndex = listState.firstVisibleItemIndex // Index item aktif
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == visibleItemIndex) Color(0xFFE4A70A) else Color(0xFFBDBDBD)
                        )
                        .padding(horizontal = 4.dp)
                )
            }
        }
    }
}
