package com.example.myapplication.ui.components.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.screens.Poppins


@Composable
fun TicketCounter(label: String, price: String, count: Int, detail: String, onCountChange: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = "$label $detail", fontSize = 14.sp, color = Color.Black, fontFamily = Poppins)
            Text(text = "IDR $price", fontSize = 14.sp, color = Color.Gray)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { if (count > 0) onCountChange(count - 1) }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_minus) ,
                    contentDescription = "Decrease",
                    tint = Color(0xFF0B746D)
                )
            }
            Text(text = "$count", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = { onCountChange(count + 1) }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_plus) ,
                    contentDescription = "Increase",
                    tint = Color(0xFF0B746D)
                )
            }
        }
    }
}
