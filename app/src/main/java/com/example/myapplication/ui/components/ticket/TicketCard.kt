package com.example.myapplication.ui.components.ticket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.screens.Poppins


@Composable
fun TicketCard(
    title: String,
    price: String? = null,
    features: List<String>,
    onOrderClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF00897B))
                    .padding(vertical = 10.dp, horizontal = 16.dp)

            ) {
                Row(
                    modifier=Modifier.fillMaxWidth() ,
                    horizontalArrangement=Arrangement.SpaceBetween ,
                    verticalAlignment=Alignment.CenterVertically
                ) {
                    Text(
                        text=title ,
                        color=Color.White ,
                        fontSize=16.sp ,
                        fontWeight=FontWeight.Bold ,
                        fontFamily=Poppins
                    )
                    if (price != null) {
                        Text(
                            text=price ,
                            color=Color.White ,
                            fontSize=14.sp ,
                            fontWeight=FontWeight.SemiBold ,
                            fontFamily=Poppins
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        features.forEach { feature ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "•",
                                    fontSize = 14.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(end = 8.dp),
                                            fontFamily = Poppins

                                )
                                Text(
                                    text = feature,
                                    fontSize = 14.sp,
                                    color = Color.Black,
                                            fontFamily = Poppins


                                )
                            }
                        }
                    }

                    Button(
                        onClick = onOrderClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7043)),
                        shape = RoundedCornerShape(16.dp) ,
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp) ,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text("Pesan", color = Color.White, fontSize = 14.sp,                    fontFamily = Poppins
                        )
                    }
                }
            }
        }
    }
}