package com.example.myapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myapplication.Poppins

val AppTypography = Typography(
    titleMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    )
)
