package com.example.myapplication.ui.components.transaction

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.screens.Poppins

@Composable
fun WeekdayHeader() {
    val weekdays = listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")
    Row(modifier = Modifier.fillMaxWidth()) {
        weekdays.forEach {
            Text(
                text = it,
                modifier = Modifier.weight(1f),
                fontFamily = Poppins ,
                fontSize = 11.sp,
                color = Color.Black,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
