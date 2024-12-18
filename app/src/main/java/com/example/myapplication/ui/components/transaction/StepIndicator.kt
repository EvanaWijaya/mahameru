package com.example.myapplication.ui.components.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun StepIndicator(currentStep: Int, totalSteps: Int, steps: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFF0B746D) ,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
            .padding(horizontal = 16.dp, vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Progress bar with steps and lines
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..totalSteps) {
                // Column untuk Lingkaran dan Teks
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f) // Ruang merata untuk setiap langkah
                ) {
                    // Lingkaran
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = if (i <= currentStep) Color(0xFFFF7F50) else Color(0x80FF7F50) ,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$i",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Deskripsi di Bawah Lingkaran
                    Text(
                        text = steps[i - 1],
                        color = if (i <= currentStep) Color(0xFFFFffff) else Color.Gray,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .width(60.dp), // Lebar tetap untuk konsistensi
                        maxLines = 2 // Batasi hingga 2 baris
                    )
                }

                // Garis Antar Lingkaran
                if (i < totalSteps) {
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .weight(1f) // Membagi sisa ruang antar lingkaran
                            .background(if (i <= currentStep) Color(0xFFFF7F50) else Color.LightGray)
                    )
                }
            }
        }
    }
}
