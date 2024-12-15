package com.example.myapplication.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.screens.Poppins

@Composable
fun FacilitySection(navController: NavController) {
    Text(
        text="Daftar Fasilitas Wisata" ,
        style=TextStyle(
            fontFamily=Poppins ,
            fontWeight=FontWeight.Medium ,
            fontSize=20.sp ,
            color=Color.Black
        ) ,
        modifier=Modifier.padding(horizontal=16.dp , vertical=8.dp)
    )
    LazyRow(
        modifier=Modifier.padding(horizontal=16.dp) ,
        horizontalArrangement=Arrangement.spacedBy(16.dp)
    ) {
        items(
            4
        ) { index ->
            FacilityCard(index=index , navController=navController)
        }
    }
}