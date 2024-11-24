package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

class RiwayatBerhasil : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Scaffold(
                    topBar = { TopBar() },
                    floatingActionButton = { FloatingPencilButton2() }
                ) { innerPadding ->
                    RiwayatSuksesScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TopBar(onBackClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                )
            )
            .background(Color(0xFF00796B))
    ) {
        // Tombol back
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(35.dp)
            )
        }

        // Judul
        Text(
            text = "Riwayat Tiket",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun RiwayatSuksesScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Tiket Saya",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Box untuk "Belum di Beri Ulasan" dan daftar tiket
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(0.dp)
        ) {
            Column {
                // Header "Belum di Beri Ulasan"
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                        .background(Color(0xFFFF7F50))
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Belum di Beri Ulasan",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White

                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Kolom untuk daftar tiket
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {

                    TicketDetails(
                        title = "Tiket Masuk Dewasa",
                        description = "Fasilitas : Pemandian Air Panas, Kebun Buah",
                        price = "Rp. 10.000"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    TicketDetails(
                        title = "Tiket Masuk Anak",
                        description = "Fasilitas : Pemandian Air Panas, Kebun Buah",
                        price = "Rp. 5.000"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bagian "Riwayat Tiket"
        SectionTitle(
            text = "Riwayat Tiket",
            backgroundColor = Color.Transparent,
            textColor = Color.Black

        )

        Spacer(modifier = Modifier.height(8.dp))

        // Daftar ulasan tiket
        ReviewItem(
            userName = "Emely Boston",
            ticketType = "Tiket Masuk",
            rating = 5,
            review = "Karena pemandangannya dekat dengan kawah gunung, suasana sangat indah."
        )

        Spacer(modifier = Modifier.height(8.dp))

        ReviewItem(
            userName = "Emely Boston",
            ticketType = "Paket Camping",
            rating = 4,
            review = "Bermalam di sini sungguh nyaman. Anak-anak juga sangat menikmati."
        )
    }
}

@Composable
fun SectionTitle(text: String, backgroundColor: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(8.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ReviewItem(userName: String, ticketType: String, rating: Int, review: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile), // Sesuaikan dengan ikon profil
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(50))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = userName, fontWeight = FontWeight.Bold)
            Text(
                text = "Jenis Tiket: $ticketType",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF00796B)
            )
            Row {
                repeat(rating) {
                    Icon(
                        painter = painterResource(id = R.drawable.star), // Ganti ikon bintang
                        contentDescription = "Star",
                        tint = Color(0xFFFFA726),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Text(text = review, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
fun TicketDetails(title: String, description: String, price: String) {
    Column {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF00796B))
        Text(text = description, fontSize = 12.sp, color = Color.Black.copy(alpha = 0.9f))
        Text(text = price, fontWeight = FontWeight.Bold, color = Color(0xFFFF7F50))
    }
}

@Composable
fun FloatingPencilButton2(onClick: () -> Unit = {}) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = Color(0xFF00796B),
        contentColor = Color.White,
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.edit), // Ganti dengan ikon pensil
            contentDescription = "Edit",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRiwayatSuksesScreen() {
    MaterialTheme {
        Scaffold(
            topBar = { TopBar() },
            floatingActionButton = { FloatingPencilButton2() }
        ) {
            RiwayatSuksesScreen(modifier = Modifier.padding(it))
        }
    }
}
