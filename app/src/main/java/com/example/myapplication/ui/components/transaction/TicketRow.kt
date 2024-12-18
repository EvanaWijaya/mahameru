package com.example.myapplication.ui.components.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun TicketRow(label: String, price: String) {
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            val poppinsFontFamily = FontFamily(
                Font(R.font.poppins_regular, FontWeight.Normal) ,
                Font(R.font.poppins_bold, FontWeight.Bold)
            )

            val parts = label.split("(", limit = 2)
            val description = parts[0].trim()
            val quantity = if (parts.size > 1) "(${parts[1]}" else ""

            Text(
                text = description,
                fontWeight = FontWeight.Medium,
                fontFamily = poppinsFontFamily
            )
            Text(
                text = " $quantity", // spasi agar terlihat terpisah
                fontWeight = FontWeight.Normal,
                fontFamily = poppinsFontFamily
            )
        }
        Text(
            text = price,
            color = Color(0xFFFF6F3C) ,
            fontFamily = FontFamily(Font(R.font.poppins_regular))
        )
    }
}


