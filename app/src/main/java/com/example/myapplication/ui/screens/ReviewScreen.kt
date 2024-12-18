package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.components.CustomTopAppBar
import com.example.myapplication.ui.components.ticket.RatingBar
import com.example.myapplication.view_model.TicketViewModel


@Composable
fun ReviewScreen(ticketViewModel : TicketViewModel, navController : NavController) {
    val context =   LocalContext.current

    var selectedTicket by remember { mutableStateOf("Pilih dulu...") }
    var showDialog by remember { mutableStateOf(false) }

    val ticketHistory = ticketViewModel.getTicketHistoryResponse.value?.data ?: emptyList()
    val ticketReview = ticketViewModel.getTicketReviewResponse.value?.data ?: emptyList()

    val filteredTicketHistory = ticketHistory.filter { history ->
        ticketReview.none { review -> review.transactionTicketId == history.transactionTicketId }
    }
    LaunchedEffect(Unit) {
        ticketViewModel.getHistoryTicket(navController.context)
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(title = "Nilai dan Ulasan", showBackIcon = true, onBackClick = {
                navController.popBackStack()
            })

        }

    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(18.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            Text("Beri Nilai dan Ulasan", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color(0xFFFF7F50)),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Jenis Tiket",
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable { showDialog = true },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedTicket,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = "Panah",
                            tint = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            RatingBar(
                rating = ticketViewModel.rating.value,
                onRatingChanged = { newRating -> ticketViewModel.rating.value = newRating }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TextField(
                        value = ticketViewModel.review.value,
                        onValueChange = { ticketViewModel.review.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        placeholder = { Text("Pesan dan kesan mu") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Button(
                            onClick = { ticketViewModel.putTicketRating(context)
                                      navController.popBackStack()
                                ticketViewModel.getHistoryTicket(context)
                                ticketViewModel.getReview(context)
                                      },
                            modifier = Modifier.height(40.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent,
                                contentColor = Color.White
                            ),
                            elevation = ButtonDefaults.elevation(0.dp)
                        ) {
                            Text("Kirim", color = Color(0xFF01714F))
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(top = 8.dp)
                    .background(Color.LightGray)
            )
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        "Pilih Jenis Tiket",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        color = Color(0xFF01714F)
                    )
                },
                buttons = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider(color = Color.Black, thickness = 0.5.dp)
                        filteredTicketHistory.forEach { ticket ->
                            Button(
                                onClick = {
                                    selectedTicket = ticket.name
                                    ticketViewModel.transactionTicketId.value = ticket.transactionTicketId
                                    showDialog = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.White,
                                    contentColor = Color.Black
                                )
                            ) {
                                Text(ticket.name, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = { showDialog = false },
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .weight(0.8f),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Transparent,
                                    contentColor = Color.Black
                                )
                            ) {
                                Text("Batal", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                            }

                            Spacer(modifier = Modifier.width(2.dp))

                            Button(
                                onClick = { showDialog = false },
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Transparent,
                                    contentColor = Color(0xFF006D60)
                                )
                            ) {
                                Text("Simpan", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                            }
                        }
                    }
                },
                modifier = Modifier.clip(RoundedCornerShape(22.dp))
            )
        }
    }
}

