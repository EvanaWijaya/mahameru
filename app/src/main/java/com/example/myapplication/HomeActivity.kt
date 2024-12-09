@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

val Poppins = FontFamily(Font(R.font.poppins_medium, FontWeight.Medium))

@Composable
fun HomeScreen(navController: NavController) {
        // Tambahkan verticalScroll di sini
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF6F6F6))
        ) {
            // Custom Top Section
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(180.dp)
                    .background(
                        color = Color(0xFF00796B),
                        shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
                    )
                    .statusBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Hi Emely Boston!",
                            style = TextStyle(
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Selamat Datang\nDi Tepas Papandayan",
                            style = TextStyle(
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Medium,
                                fontSize = 18.sp,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Garut, Jawa Barat",
                            style = TextStyle(
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(40.dp))
                    )
                }
            }
            // Banner Section
            BannerSection()

            // Tiket Section
            TicketSection()

            // Fasilitas Section
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Daftar Fasilitas Wisata",
                style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = Color.Black
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    4) { index ->
                    FacilityCard(index = index, navController = navController)
                }
            }
        }
}

@Composable
fun BannerSection() {
    Spacer(modifier = Modifier.height(20.dp))
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

@Composable
fun BannerCard(title: String, subtitle: String, price: String, icon: Int, backgroundColor: Color) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(362.dp)
            .height(169.dp)
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Image on the left side
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "Banner Icon",
                    modifier = Modifier
                        .width(65.dp)
                        .height(96.dp)
                        .offset(y = 25.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp)) // Space between image and text

                // Column to hold title and subtitle
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        color = Color.White.copy(alpha = 0.8f),
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp
                        )
                    )
                }
            }

            // Price info on the bottom left corner
            Box(
                modifier = Modifier
                    .width(92.dp)
                    .height(50.dp)
                    .align(Alignment.TopStart)
                    .padding(start = 0.dp, top = 0.dp)
                    .clip(
                        RoundedCornerShape(
                            topEnd = 20.dp, // Create curved corner at the top-right
                            bottomEnd = 20.dp
                        )
                    )
                    .background(Color(0xFFE4A70A)) // Set background color for price area
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
            ) {
                Text(
                    text = price,
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}

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
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
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
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.barcode), // Ikon layar penuh
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Isi Tiket
        Card(
            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
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
                    fontFamily = Poppins,
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
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color(0xFF00C853) // Warna hijau untuk waktu
                ),
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        // Detail Tiket
        Text(
            text = details,
            style = TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color.Black
            ),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun FacilityCard(index: Int, navController: NavController) {
    val facilityName = when (index) {
        0 -> "Kolam Rendam Air Panas"
        1 -> "Camping Ground"
        2 -> "Kebun Strawberry"
        else -> "Gazebo"
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(183.dp)
            .height(281.dp)
            .clickable {
                // Navigasi ke layar panduan
                navController.navigate("guide/$facilityName")
            }
    ) {
        Box( // Gunakan Box untuk menumpuk elemen
            modifier = Modifier.fillMaxSize()
        ) {
            // Gambar sebagai latar belakang
            Image(
                painter = painterResource(id = when (index) {
                    0 -> R.drawable.kolam_air_panas
                    1 -> R.drawable.kemah
                    2 -> R.drawable.kebun_buah
                    else -> R.drawable.gazebo
                }),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .shadow(elevation = 4.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000))
                    .width(183.dp)
                    .height(281.dp)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart) // Posisi di kiri bawah
                    .padding(start = 16.dp, bottom = 16.dp) // Padding untuk jarak dari tepi
            ) {
                // Shadow Teks
                Text(
                    text = when (index) {
                        0 -> "Kolam Rendam Air Panas"
                        1 -> "Camping Ground"
                        2 -> "Kebun Strawberry"
                        else -> "Gazebo"
                    },
                    color = Color.Black.copy(alpha = 0.5f), // Warna bayangan dengan transparansi
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterStart) // Rata kiri
                        .offset(x = 1.dp, y = 1.dp) // Posisi bayangan
                )

                // Teks Utama
                Text(
                    text = when (index) {
                        0 -> "Kolam Rendam Air Panas"
                        1 -> "Camping Ground"
                        2 -> "Kebun Strawberry"
                        else -> "Gazebo"
                    },
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.align(Alignment.CenterStart) // Rata kiri
                )
            }
        }
    }
}
