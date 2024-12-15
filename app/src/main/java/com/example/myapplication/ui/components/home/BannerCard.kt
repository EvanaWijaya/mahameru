package com.example.myapplication.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.screens.Poppins

@Composable
fun BannerCard(title: String, subtitle: String, price: String, icon: Int, backgroundColor: Color) {
    Card(
        shape = RoundedCornerShape(20.dp) ,
        modifier = Modifier
            .width(362.dp)
            .height(169.dp)
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Image on the left side
                Image(
                    painter = painterResource(id = icon) ,
                    contentDescription = "Banner Icon",
                    modifier = Modifier
                        .width(65.dp)
                        .height(96.dp)
                        .offset(y = 25.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp)) // Space between image and text

                // Column to hold title and subtitle
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        style = TextStyle(
                            fontFamily = Poppins ,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        color = Color.White.copy(alpha = 0.8f),
                        style = TextStyle(
                            fontFamily = Poppins ,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp
                        )
                    )
                }
            }

            // Price info on the bottom left corner
            Box(
                modifier = Modifier
                    .width(92.dp)
                    .height(50.dp)
                    .align(Alignment.TopStart)
                    .padding(start = 0.dp, top = 0.dp)
                    .clip(
                        RoundedCornerShape(
                            topEnd = 20.dp, // Create curved corner at the top-right
                            bottomEnd = 20.dp
                        )
                    )
                    .background(Color(0xFFE4A70A)) // Set background color for price area
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = price,
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = Poppins ,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}
