package com.example.myapplication.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.screens.Poppins
import com.example.myapplication.R


@Composable
fun FacilityCard(index: Int, navController: NavController) {
    val facilityName = when (index) {
        0 -> "Kolam Rendam Air Panas"
        1 -> "Camping Ground"
        2 -> "Kebun Strawberry"
        else -> "Gazebo"
    }

    Card(
        shape = RoundedCornerShape(8.dp) ,
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
                }) ,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .shadow(elevation = 4.dp, spotColor = Color(0x40000000) , ambientColor = Color(0x40000000))
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
                        fontFamily = Poppins ,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ) ,
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
                        fontFamily = Poppins ,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ) ,
                    modifier = Modifier.align(Alignment.CenterStart) // Rata kiri
                )
            }
        }
    }
}
