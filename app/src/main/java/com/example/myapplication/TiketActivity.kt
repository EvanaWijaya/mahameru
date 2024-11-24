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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TicketScreen() {
    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF00897B),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp) // Ujung bawah melengkung
            ) {
                TopAppBar(
                    title = { // Menambahkan title di sini
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center // Judul di tengah
                        ) {
                            Text(
                                text = "Pilihan Tiket",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent), // Membuat latar belakang transparan
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Menambahkan scrolling
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Jam Operasional Section
            OperationalInfoCard()

            Spacer(modifier = Modifier.height(16.dp))

            // Tiket Masuk Section
            TicketActivity(title = "Tiket Masuk")
            TicketCard(
                title = "Tiket Wisata",
                price = null,
                features = listOf(
                    "Masuk ke tempat wisata",
                    "Pemandian air panas",
                    "Menikmati agrowisata",
                    "Memetik buah"
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Paket Camping Section
            TicketActivity(title = "Paket Camping")
            CampingOptions()
        }
    }
}

@Composable
fun OperationalInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFF7043)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Menambahkan ikon jam di sebelah kiri teks
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccessTime, // Ikon jam
                    contentDescription = "Jam Operasional",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp) // Ukuran ikon
                )
                Spacer(modifier = Modifier.width(8.dp)) // Spasi antara ikon dan teks
                Text("Jam Operasional Tempat Wisata", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Buka, 08.00-18.00\nCamping, 1 x 24 Jam",
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TicketActivity (title: String) {
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
    onOrderClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Bagian Header (Nama Tiket dan Harga Sejajar)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF00897B))
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    price?.let {
                        Text(
                            text = it,
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Bagian Konten
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Spasi antar elemen
            ) {
                features.forEach { feature ->
                    FeatureRow(feature = feature) // Menggunakan komponen untuk detail
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Tombol Pesan di Kanan Bawah
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        onClick = onOrderClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7043)),
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
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
        Triple("Sewa Tempat", "IDR65.000", listOf(
            "Masuk ke tempat wisata",
            "Pemandian air panas",
            "Menikmati agrowisata",
            "Memetik buah",
            "Pengunjung membawa Tenda sendiri"
        )),
        Triple("Sewa Tenda Biasa", "IDR135.000", listOf(
            "Masuk ke tempat wisata",
            "Pemandian air panas",
            "Menikmati agrowisata",
            "Memetik buah",
            "Tenda (kapasitas 3-4 orang)"
        )),
        Triple("Paket Camping Biasa", "IDR200.000", listOf(
            "Masuk ke tempat wisata",
            "Pemandian air panas",
            "Menikmati agrowisata",
            "Memetik buah",
            "Tenda (kapasitas 3-4 orang)",
            "Matras, sleeping bag"
        )),
        Triple("Paket Camping Komplet", "IDR235.000", listOf(
            "Masuk ke tempat wisata",
            "Pemandian air panas",
            "Menikmati agrowisata",
            "Memetik buah",
            "Tenda (kapasitas 3-4 orang)",
            "Matras, sleeping bag",
            "Sarapan"
        )),
        Triple("ATP Glamping", "IDR500.000", listOf(
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
        ))
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
    TicketScreen()
}
