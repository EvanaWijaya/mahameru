package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.components.CustomTopAppBar


@Composable
fun RiwayatScreen(navController: NavController ,
                  modifier: Modifier = Modifier) {
    CustomTopAppBar( onBackClick = { navController.popBackStack() }, title = "Riwayat Tiket")
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

