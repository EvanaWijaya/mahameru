@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
fun TicketScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Latar belakang abu-abu muda
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        bottomStart = 20.dp,
                        bottomEnd = 20.dp
                    )
                ) // Melengkung di bawah
                .background(Color(0xFF00897B)) // Warna hijau header
                .padding(vertical = 24.dp)
        ) {
            androidx.compose.material.Text(
                text = "Pilihan Tiket",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 18.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Menambahkan scrolling
                .padding(16.dp)
        ) {
            // Jam Operasional Section
            OperationalInfoCard()

            Spacer(modifier = Modifier.height(16.dp))

            // Tiket Masuk Section
            CustomSectionTitle(title = "Tiket Masuk")
            TicketCard(
                title = "Tiket Wisata",
                price = null,
                features = listOf(
                    "Masuk ke tempat wisata",
                    "Pemandian air panas",
                    "Menikmati agrowisata",
                    "Memetik buah"
                ),
                onOrderClick = {navController.navigate("ticketBookingScreen")}
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Paket Camping Section
            CustomSectionTitle(title = "Paket Camping")
            CampingOptions()
        }
    }
}

@Composable
fun OperationalInfoCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFF7043)),
        shape = RoundedCornerShape(12.dp),
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

@Composable
    fun CustomSectionTitle(title: String) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }

@Composable
fun TicketCard(
    title: String,
    price: String? = null,
    features: List<String>,
    onOrderClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Bagian Header (Background Hijau dengan Judul Tiket)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF00897B)) // Warna hijau
                    .padding(vertical = 10.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Bagian Konten
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top, // Menyelaraskan tombol dan teks di bagian atas
                    horizontalArrangement = Arrangement.SpaceBetween // Memberi jarak antara teks fitur dan tombol
                ) {
                    // Daftar Fitur
                    Column(
                        modifier = Modifier.weight(1f), // Membuat fitur menyesuaikan ruang yang tersedia
                        verticalArrangement = Arrangement.spacedBy(8.dp) // Menambahkan jarak antar fitur
                    ) {
                        features.forEach { feature ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "•",
                                    fontSize = 14.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(end = 8.dp) // Spasi setelah bullet point
                                )
                                Text(
                                    text = feature,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }

                    // Tombol Pesan
                    Button(
                        onClick = onOrderClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7043)), // Warna tombol
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.padding(start = 8.dp) // Memberi jarak tombol dengan fitur
                    ) {
                        Text("Pesan", color = Color.White, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

    @Composable
    fun FeatureRow(feature: String) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ikon Bullet Point
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color(0xFF00897B), shape = RoundedCornerShape(50))
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Teks Fitur
            Text(
                text = feature,
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 20.sp // Menambahkan jarak antar baris
            )
        }
    }

    @Composable
    fun CampingOptions() {
        val campingPackages = listOf(
            Triple(
                "Sewa Tempat", "IDR65.000", listOf(
                    "Masuk ke tempat wisata",
                    "Pemandian air panas",
                    "Menikmati agrowisata",
                    "Memetik buah",
                    "Pengunjung membawa Tenda sendiri"
                )
            ),
            Triple(
                "Sewa Tenda Biasa", "IDR135.000", listOf(
                    "Masuk ke tempat wisata",
                    "Pemandian air panas",
                    "Menikmati agrowisata",
                    "Memetik buah",
                    "Tenda (kapasitas 3-4 orang)"
                )
            ),
            Triple(
                "Paket Camping Biasa", "IDR200.000", listOf(
                    "Masuk ke tempat wisata",
                    "Pemandian air panas",
                    "Menikmati agrowisata",
                    "Memetik buah",
                    "Tenda (kapasitas 3-4 orang)",
                    "Matras, sleeping bag"
                )
            ),
            Triple(
                "Paket Camping Komplet", "IDR235.000", listOf(
                    "Masuk ke tempat wisata",
                    "Pemandian air panas",
                    "Menikmati agrowisata",
                    "Memetik buah",
                    "Tenda (kapasitas 3-4 orang)",
                    "Matras, sleeping bag",
                    "Sarapan"
                )
            ),
            Triple(
                "ATP Glamping", "IDR500.000", listOf(
                    "Masuk ke tempat wisata",
                    "Pemandian air panas",
                    "Menikmati agrowisata",
                    "Memetik buah",
                    "Tenda (kapasitas 4 orang)",
                    "Alas tenda",
                    "1 Set kasur dan bed cover",
                    "4 Bantal dan selimut",
                    "1 Meja lipat dan 2 kursi",
                    "1 stop kontak dan lampu tenda",
                    "1 ikat kayu bakar",
                    "4 porsi sarapan dan 4 hydro coco"
                )
            )
        )

        campingPackages.forEach { (title, price, features) ->
            Spacer(modifier = Modifier.height(8.dp))
            TicketCard(
                title = title,
                price = price,
                features = features,
                onOrderClick = {}
            )
        }
    }

@Preview(showBackground = true)
@Composable
fun TicketScreenPreview() {
    TicketScreen(navController = rememberNavController())
}
