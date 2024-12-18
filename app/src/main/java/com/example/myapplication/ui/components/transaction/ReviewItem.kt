package com.example.myapplication.ui.components.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.components.ProfilePicture
import com.example.myapplication.ui.screens.Poppins

@Composable
fun ReviewItem(
    userName : String ,
    ticketType : String ,
    rating : Int ,
    review : String ,
    pictureUrl : String
) {
    Row(
        modifier=Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF5F5F5))
            .padding(16.dp) ,
        verticalAlignment=Alignment.Top
    ) {
        ProfilePicture(
            pictureUrl=pictureUrl ,
            size=40.dp
        )

        Spacer(modifier=Modifier.width(16.dp))

        Column {
            Text(text=userName , fontWeight=FontWeight.Bold)
            Text(
                text="Jenis Tiket: $ticketType" ,
                fontSize=14.sp ,
                fontWeight=FontWeight.SemiBold ,
                color=Color(0xFF00796B) ,
                fontFamily=Poppins
            )
            Row {
                repeat(rating) {
                    Icon(
                        painter=painterResource(id=R.drawable.star) ,
                        contentDescription="Star" ,
                        tint=Color(0xFFFFA726) ,
                        modifier=Modifier.size(16.dp)
                    )
                }
            }
            Text(
                text=review , fontSize=14.sp , color=Color.Gray , fontFamily=Poppins
            )
        }
    }
}