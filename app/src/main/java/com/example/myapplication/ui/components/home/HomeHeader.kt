package com.example.myapplication.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.components.ProfilePicture
import com.example.myapplication.ui.screens.Poppins

@Composable
fun HomeHeader(username : String , pictureUrl : String) {
    Box(
        modifier=Modifier
            .fillMaxSize()
            .height(180.dp)
            .background(
                color=colorResource(id=R.color.colorSecondaryVariant) ,
                shape=RoundedCornerShape(bottomEnd=20.dp , bottomStart=20.dp)
            )
            .statusBarsPadding()
    ) {
        Row(
            modifier=Modifier
                .fillMaxWidth()
                .padding(18.dp) ,
            verticalAlignment=Alignment.CenterVertically ,
            horizontalArrangement=Arrangement.SpaceBetween
        ) {
            Column(
                modifier=Modifier
                    .weight(1f)
                    .padding(8.dp) ,
                verticalArrangement=Arrangement.Center
            ) {
                Text(
                    text=username ,
                    style=TextStyle(
                        fontFamily=Poppins ,
                        fontWeight=FontWeight.Medium ,
                        fontSize=15.sp ,
                        color=Color.White
                    )
                )
                Spacer(modifier=Modifier.height(8.dp))
                Text(
                    text="Selamat Datang\nDi Tepas Papandayan" ,
                    style=TextStyle(
                        fontFamily=Poppins ,
                        fontWeight=FontWeight.Medium ,
                        fontSize=18.sp ,
                        color=Color.White
                    )
                )
                Spacer(modifier=Modifier.height(10.dp))
                Text(
                    text="Garut, Jawa Barat" ,
                    style=TextStyle(
                        fontFamily=Poppins ,
                        fontWeight=FontWeight.Medium ,
                        fontSize=12.sp ,
                        color=Color.White
                    )
                )
            }
            ProfilePicture(pictureUrl)
        }
    }
}