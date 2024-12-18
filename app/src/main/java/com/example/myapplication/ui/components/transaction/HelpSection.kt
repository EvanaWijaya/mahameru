package com.example.myapplication.ui.components.transaction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.myapplication.ui.screens.Poppins

@Composable
fun HelpSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Butuh bantuan? Kamu bisa hubungi ",
            fontFamily = Poppins,
        )
        Text(
            text = "Verdant Care",
            color = Color(0xFF00796B),
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins,
            modifier = Modifier.clickable { }
        )
    }
}
