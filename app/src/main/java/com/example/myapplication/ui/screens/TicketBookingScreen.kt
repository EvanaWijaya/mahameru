package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.components.transaction.CustomCalendarView
import com.example.myapplication.ui.components.transaction.StepIndicator
import com.example.myapplication.ui.components.transaction.TicketCounter
import com.example.myapplication.ui.components.transaction.TransactionAppBar
import com.example.myapplication.utils.formatDate
import com.example.myapplication.view_model.TicketViewModel
import com.example.myapplication.view_model.TransactionViewModel
import java.util.Calendar

@Composable
fun TicketBookingScreen(
    navController : NavController ,
    ticketViewModel : TicketViewModel ,
    transactionViewModel : TransactionViewModel
) {
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    val totalCost=ticketViewModel.totalCost.value
    val ticketsWithTiket = ticketViewModel.getTicketsWithTiket()

    val steps=listOf("Pilih Tiket" , "Isi\nData" , "Bayar Tiket" , "Tiket Online")

    Scaffold(
        topBar={
            Column(modifier=Modifier.fillMaxWidth()) {
                StepIndicator(currentStep=1 , totalSteps=4 , steps=steps)

                TransactionAppBar(
                    title="Detail Pemesanan" ,
                    onNavigationClick={
                        navController.popBackStack()
                        transactionViewModel.resetTransaction()
                        ticketViewModel.resetTicketQuantities()
                    }
                )
            }
        } ,
        bottomBar = {
            Button(
                onClick = {
                    navController.navigate("visitorDetail")
                    transactionViewModel.totalPrice.value = totalCost
                },
                enabled = totalCost > 0,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentPadding = PaddingValues(16.dp) ,
                shape = RoundedCornerShape(12.dp) ,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (totalCost > 0) Color(0xFFFF7F50) else Color.Gray
                )
            ) {
                Text(
                    text = "Lanjutkan",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = Poppins
                )
            }
        }

        ) { contentPadding ->
        LazyColumn(
            modifier=Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp) ,
            verticalArrangement=Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text="Pilih Tanggal" ,
                    fontWeight=FontWeight.Medium ,
                    fontSize=18.sp ,
                    fontFamily=Poppins
                )
                CustomCalendarView(
                    selectedDate=selectedDate ,
                    onDateSelected={ date ->
                        selectedDate=date
                        val formattedDate=formatDate(selectedDate)
                        transactionViewModel.date.value=formattedDate
                    }
                )

            }

            item {
                Text(
                    text="Jumlah Tiket" ,
                    fontWeight=FontWeight.Medium ,
                    fontSize=16.sp ,
                    fontFamily=Poppins
                )
                Text(
                    text="Anak usia < 1 tahun gratis" ,
                    fontSize=14.sp ,
                    color=Color.Gray ,
                    fontFamily=Poppins
                )
            }



            item {
                ticketsWithTiket.forEach { ticket ->
                    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                        TicketCounter(
                            label = ticket.name,
                            price = ticketViewModel.formatPriceWithoutDecimal(ticket.price),
                            detail = "(${ticket.description})",
                            count = ticketViewModel.ticketQuantities.value[ticket.name] ?: 0,
                            onCountChange = { newCount ->
                                ticketViewModel.updateTicketQuantity(ticket.name, newCount)
                                transactionViewModel.amount.value = newCount
                                transactionViewModel.updateTicketsWithQuantity(ticket.id, newCount)

                            }
                        )
                    }
                }
            }


            item {
                Divider()
            }

            item {
                Row(
                    modifier=Modifier
                        .fillMaxWidth()
                        .padding(horizontal=16.dp) ,
                    horizontalArrangement=Arrangement.SpaceBetween ,
                    verticalAlignment=Alignment.CenterVertically ,

                    ) {
                    Text(
                        text="Total Bayar" ,
                        fontWeight=FontWeight.Bold ,
                        fontSize=16.sp ,
                        fontFamily=Poppins
                    )
                    Text(
                        text="Rp $totalCost" ,
                        fontWeight=FontWeight.Bold ,
                        fontSize=16.sp ,
                        fontFamily=Poppins
                    )
                }
            }

            item {
                Divider()
            }


        }
    }

}