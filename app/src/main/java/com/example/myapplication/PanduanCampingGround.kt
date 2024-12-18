package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.PanduanWisataTheme


class PanduanCampingGround : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PanduanWisataTheme {

            }
        }
    }
}

@Composable
fun CampingGround() {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color(0xFF01714F), // Warna biru
                contentColor = Color.White, // Warna teks putih
                modifier = Modifier.height(80.dp) // Tinggi AppBar diperbesar menjadi 80 dp
            ) {
                Box( // Gunakan Box untuk memastikan konten berada di tengah
                    modifier = Modifier
                        .fillMaxSize(), // Mengisi seluruh ukuran TopAppBar
                    contentAlignment = Alignment.Center // Posisikan konten di tengah
                ) {
                    Text(
                        text = "Panduan Tempat Wisata",
                        fontSize = 20.sp, // Ukuran font
                        fontWeight = FontWeight.Bold, // Tebal teks
                        color = Color.White
                    )
                }
            }


        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Gambar
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.strawberry),
                    contentDescription = "Kebun Strawberry",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(200.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Jam Operasional
            Text("Jam Operasional Bermalam", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("1 x 24 Jam", fontSize = 12.sp, color = Color(0xFFFFA500))

            Spacer(modifier = Modifier.height(8.dp))

            // Deskripsi
            Text("Deskripsi Tempat Wisata", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Area camping Agrowisata Tepas Papandayan buka 24 jam dengan biaya mulai dari Rp65.000. Pengunjung dapat membawa tenda sendiri dengan hanya menyewa lahan camping atau memilih menyewa paket tenda yang tersedia.",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Fasilitas lain seperti area parkir, mushala, dan tentu saja kolam renang air panas 24 jam untuk pengunjung yang bermalam juga tersedia. ",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Kebijakan
            Text("Kebijakan", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Tiket Camping belum termasuk dengan tiket masuk dan tiket parkir Tepas Papandayan.",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}