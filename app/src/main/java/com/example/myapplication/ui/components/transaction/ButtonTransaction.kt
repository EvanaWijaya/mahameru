package com.example.myapplication.ui.components.transaction

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonTransaction(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    backgroundColor: Color = Color(0xFFFF7F50),
    textColor: Color = Color.White,
    fontFamily: FontFamily? = null
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) backgroundColor else Color.Gray
        )
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 16.sp,
            fontFamily = fontFamily
        )
    }
}
