package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.PanduanWisataTheme
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward

class Ulasan_Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PanduanWisataTheme {
                PanduanWisataScreen()
            }
        }
    }
}

@Composable
fun PanduanWisataScreen() {
    var selectedTicket by remember { mutableStateOf("Paket Camping Biasa") }
    var showDialog by remember { mutableStateOf(false) }
    var userRating by remember { mutableStateOf(0) } // Menyimpan nilai rating pengguna
    var userComment by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color(0xFF01714F),
                contentColor = Color.White,
                modifier = Modifier.height(80.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nilai dan Ulasan",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
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
                .padding(18.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            Text("Beri Nilai dan Ulasan", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            // Komponen kartu untuk memilih jenis tiket
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color(0xFFFFA500)),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Jenis Tiket",
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable { showDialog = true },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedTicket,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = "Panah",
                            tint = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Fitur Rating

            RatingBar(
                rating = userRating,
                onRatingChanged = { newRating -> userRating = newRating }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp) // Memberikan ruang dalam kotak komentar
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Tempat untuk menulis komentar
                    TextField(
                        value = userComment,
                        onValueChange = { userComment = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f), // Mengambil sisa ruang vertikal dalam kotak
                        placeholder = { Text("Pesan dan kesan mu") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent, // Tanpa latar belakang tambahan
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    // Tombol "Kirim" di pojok kanan bawah
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Button(
                            onClick = { /* Aksi untuk mengirim komentar */ },
                            modifier = Modifier.height(40.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent, // Latar belakang transparan
                                contentColor = Color.White
                            ),
                            elevation = ButtonDefaults.elevation(0.dp)
                        ) {
                            Text("Kirim", color = Color(0xFF01714F))
                        }
                    }
                }
            }

            Box( // space buat icon navigasi
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(top = 8.dp)
                    .background(Color.LightGray)
            )
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    // Menggunakan Modifier.fillMaxWidth() untuk memastikan judul sejajar dengan konten lain
                    Text(
                        "Pilih Jenis Tiket",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        color = Color(0xFF01714F)
                    )
                },
                buttons = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider(color = Color.Black, thickness = 0.5.dp)
                        Button(
                            onClick = {
                                selectedTicket = "Tiket Masuk"
                                showDialog = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp), // Memberikan sedikit padding di sekitar tombol
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White, // Latar belakang putih
                                contentColor = Color.Black // Teks berwarna hitam
                            )
                        ) {
                            Text("Tiket Masuk", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                            // Teks "Tiket Masuk" diatur ke kiri
                        }

                        Button(
                            onClick = {
                                selectedTicket = "Tiket Paket Camping"
                                showDialog = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp), // Memberikan sedikit padding di sekitar tombol
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White, // Latar belakang putih
                                contentColor = Color.Black // Teks berwarna hitam
                            )
                        ) {
                            Text("Tiket Paket Camping", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                            // Teks "Tiket Paket Camping" diatur ke kiri
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.weight(2f))
                            Button(
                                onClick = { showDialog = false },
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .weight(0.8f),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.White,
                                    contentColor = Color.Black
                                )
                            ) {
                                Text(
                                    "Batal",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontSize = 8.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(2.dp))

                            Button(
                                onClick = { showDialog = false },
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.White,
                                    contentColor = Color.Black,

                                    )
                            ) {
                                Text(
                                    "Simpan", modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontSize = 8.sp,
                                    color = Color(0xFF01714F)
                                )
                            }
                        }
                    }
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(22.dp)
                    )

            )
        }
    }
}

@Composable
fun RatingBar(
    rating: Int,
    onRatingChanged: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= rating) Icons.Filled.Star else Icons.Filled.StarBorder,
                contentDescription = "Star",
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onRatingChanged(i) },
                tint = if (i <= rating) Color(0xFFFFD700) else Color.Gray // Warna bintang emas atau abu-abu
            )
        }
    }
}






