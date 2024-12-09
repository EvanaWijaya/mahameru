package com.example.myapplication

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val poppinsFontFamily = FontFamily(
    Font(R.font.poppins_regular),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

@Composable
fun DetailTransaksiScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F9FA)) // Warna latar belakang
            .padding(16.dp) // Padding utama untuk memberi jarak antar elemen
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = { /* Navigasi ke halaman sebelumnya */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.weight(1f)) // Mengambil ruang di antara button dan teks
            Text(
                text = "Detail Transaksi",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Bagian status dan detail transaksi tanpa kotak
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Status", fontWeight = FontWeight.Bold)
                Text(
                    "Berhasil",
                    color = Color(0xFF00796B),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(
                            color = Color(0xFF00796B).copy(alpha = 0.5f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            DetailRow(label = "No Transaksi", value = "NPM15YT")
            Spacer(modifier = Modifier.height(12.dp)) // Menambah jarak antar detail
            DetailRow(label = "Kategori", value = "Tiket Masuk")
            Spacer(modifier = Modifier.height(12.dp)) // Menambah jarak antar detail
            DetailRow(label = "Tanggal Transaksi", value = "7 November 2024 – 12.39 WIB")
            Spacer(modifier = Modifier.height(12.dp)) // Menambah jarak antar detail
            DetailRow(label = "Metode Pembayaran", value = "Transfer Bank")
            Spacer(modifier = Modifier.height(12.dp)) // Menambah jarak antar detail
            DetailRow(label = "Total Bayar", value = "Rp15.000", isBold = true, color = Color(0xFFFF7043))
        }

        // Garis hijau sebagai pemisah dengan panjang penuh
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth() // Panjang garis penuh hingga ujung layar
                .height(8.dp)
                .background(Color(0xFF00796B)) // Warna hijau
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), // Menambahkan padding horizontal agar tidak terlalu dekat dengan tepi layar
            shape = RoundedCornerShape(10.dp),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Header berwarna oranye
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFF7043)) // Warna oranye
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Tiket Masuk",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }

                // Isi tiket
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "TEPAS PAPANDAYAN – (2 Tiket Masuk)",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Min, 10 November 2024 (08.00 - 18.00)",
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.qr_code),
                            contentDescription = "QR Code",
                            modifier = Modifier.size(40.dp),
                            tint = Color.Unspecified // Agar warna asli QR Code tidak berubah
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Kode Pesanan",
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                            Text(
                                text = "J9K2B7U1",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }

        // Garis hijau sebagai pemisah dengan panjang penuh
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth() // Panjang garis penuh hingga ujung layar
                .height(8.dp)
                .background(Color(0xFF00796B)) // Warna hijau
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Bagian data pengunjung
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp) // Padding horizontal untuk data pengunjung
        ) {
            Text(
                text = "Data pengunjung",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Pengunjung pertama
            PengunjungRow(index = 1, name = "AYUMI NINGSIH", nik = "1902617748910003")
            Spacer(modifier = Modifier.height(4.dp))

            // Pengunjung kedua
            PengunjungRow(index = 2, name = "MICHAEL WICAKSONO", nik = "1908617948910001")
        }

        Spacer(modifier = Modifier.height(250.dp))

        // Bagian bantuan
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Butuh bantuan? Kamu bisa hubungi ")
            Text(
                text = "Verdant Care",
                color = Color(0xFF00796B),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { /* Navigasi ke halaman bantuan */ }
            )
        }
    }
}


@Composable
fun DetailRow(label: String, value: String, isBold: Boolean = false, color: Color = Color.Black) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray)
        Text(value, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal, color = color)
    }
}

@Composable
fun PengunjungRow(index: Int, name: String, nik: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$index.",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 8.dp) // Agar ada jarak antara nomor urut dan nama
        )
        Column {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "NIK. $nik",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DetailTransaksiPreview() {
        DetailTransaksiScreen()
}