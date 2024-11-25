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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

class Riwayat : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { CustomTopAppBar() },
                    floatingActionButton = { FloatingPencilButton() }
                ) { innerPadding ->
                    RiwayatScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CustomTopAppBar(onBackClick: () -> Unit = {}) {
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
fun RiwayatScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Ikon di tengah
        Image(
            painter = painterResource(id = R.drawable.riwayat), // Ganti dengan ikon yang sesuai
            contentDescription = "Ticket Icon",
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Teks utama
        Text(
            text = "Belum Ada Ulasan Tiket",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Teks deskripsi
        Text(
            text = "Ayo pesan segera dan berikan ulasan yang menarik tentang liburanmu",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}

@Composable
fun FloatingPencilButton(onClick: () -> Unit = {}) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = Color.White,
        contentColor = Color(0xFFD3D3D3),
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.edit), // Tambahkan ikon pensil
            contentDescription = "Edit",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RiwayatScreenPreview() {
    MaterialTheme {
        Scaffold(
            topBar = { CustomTopAppBar() },
            floatingActionButton = { FloatingPencilButton() }
        ) {
            RiwayatScreen(modifier = Modifier.padding(it))
        }
    }
}
