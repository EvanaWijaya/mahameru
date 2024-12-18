package com.example.myapplication.ui.components.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VisitorRow(index: Int, name: String, nik: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$index.",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 8.dp) // Agar ada jarak antara nomor urut dan nama
        )
        Column {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "NIK. $nik",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}