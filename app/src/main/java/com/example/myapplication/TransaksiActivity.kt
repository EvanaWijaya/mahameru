package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun TransaksiPembayaranScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Latar belakang abu-abu muda
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)) // Melengkung di bawah
                .background(Color(0xFF00897B)) // Warna hijau header
                .padding(vertical = 24.dp)
        ) {
            Text(
                text = "Transaksi Pembayaran",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 18.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tiket Saya
        Text(
            text = "Tiket Saya",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Kartu Menunggu Pembayaran
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                // Header Menunggu Pembayaran
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFD32F2F)) // Warna merah
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Menunggu Pembayaran",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "23.59.10", // Waktu
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }

                // Isi Tiket
                Column(modifier = Modifier.padding(16.dp)) {
                    TiketItem(
                        title = "Tiket Masuk Dewasa",
                        fasilitas = "Fasilitas : Pemandian Air Panas, Kebun Buah",
                        harga = "Rp. 10.000"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TiketItem(
                        title = "Tiket Masuk Anak",
                        fasilitas = "Fasilitas : Pemandian Air Panas, Kebun Buah",
                        harga = "Rp. 5.000"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Rp. 15.000",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Riwayat Pemesanan
        Text(
            text = "Riwayat Pemesanan",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Kartu Riwayat Pemesanan
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color(0xFF00897B)) // Garis hitam di tepi
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Agrowisata Tepas Papandayan",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00897B)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "434243422222",
                    fontSize = 12.sp
                )
                Text(
                    text = "Rp. 15.000",
                    fontSize = 12.sp
                )
                Text(
                    text = "23 Oct 2024 | 10.30",
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(250.dp))

        // Lihat Riwayat Tiket
        Text(
            text = "Lihat Riwayat Tiket",
            fontSize = 14.sp,
            color = Color(0xFFFF7F50),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {navController.navigate("riwayatBerhasil")}
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun TiketItem(title: String, fasilitas: String, harga: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), // Tambahkan padding vertikal untuk estetika
        verticalAlignment = Alignment.Top // Pusatkan elemen secara vertikal
    ) {
        Box(
            modifier = Modifier
                .size(10.dp) // Ukuran titik
                .background(Color(0xFFD32F2F), shape = CircleShape) // Warna merah
        )

        Spacer(modifier = Modifier.width(8.dp)) // Jarak antara titik dan teks

        // Kolom teks dan harga
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = fasilitas,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Text(
                text = harga,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTransaksiPembayaranScreen() {
    TransaksiPembayaranScreen(navController = rememberNavController())
}
