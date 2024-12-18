package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.myapplication.view_model.InventoryViewModel
import com.example.myapplication.view_model.TicketViewModel
import com.example.myapplication.view_model.TransactionViewModel
import java.util.Calendar


@Composable
fun TicketCampingBookingScreen(
    navController : NavController ,
    ticketViewModel : TicketViewModel ,
    transactionViewModel : TransactionViewModel ,
    inventoryViewModel : InventoryViewModel ,
    ticketId : String
) {

    LaunchedEffect(Unit) {
        inventoryViewModel.getInventory(navController.context)
    }

    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
//    val ticketData=ticketViewModel.getTicketByIdResponse()
    val totalCost=inventoryViewModel.totalCost.value
    val inventories=inventoryViewModel.getInventorys()?.data ?: emptyList()
    val features=ticketViewModel.getTicketByIdResponse.value?.data?.description
    val price=ticketViewModel.getTicketByIdResponse.value?.data?.price
    val name=ticketViewModel.getTicketByIdResponse.value?.data?.name
    fun formatDescription(description : String?) : List<String> {
        return description?.split(".")?.map { it.trim() }?.filter { it.isNotEmpty() } ?: emptyList()
    }

    val formattedPrice=inventoryViewModel.formatPriceWithoutDecimal(price)
    val finalCost=totalCost + formattedPrice.toInt()


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
                        inventoryViewModel.resetInventoryQuantities()
                    }
                )
            }
        } ,
        bottomBar={
            Button(
                onClick={
                    navController.navigate("visitorCampingDetail")
                    transactionViewModel.totalPrice.value=finalCost
                    transactionViewModel.nameTicket.value=name ?: ""
                    transactionViewModel.ticketPrice.value=formattedPrice.toInt()


                } ,
                enabled=finalCost > 0 ,
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(32.dp) ,
                contentPadding=PaddingValues(16.dp) ,
                shape=RoundedCornerShape(12.dp) ,
                colors=ButtonDefaults.buttonColors(
                    backgroundColor=if (finalCost > 0) Color(0xFFFF7F50) else Color.Gray
                )
            ) {
                Text(
                    text="Lanjutkan" ,
                    color=Color.White ,
                    fontSize=16.sp ,
                    fontFamily=Poppins
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
                Divider(
                    color=Color(0xFFE0E0E0) ,
                )

                Spacer(modifier=Modifier.height(8.dp))
                Text(
                    text="Pesanan" ,
                    fontWeight=FontWeight.Bold ,
                    fontSize=16.sp ,
                    fontFamily=Poppins
                )
                Spacer(modifier=Modifier.padding(2.dp))
                Row(
                    modifier=Modifier.padding(horizontal=16.dp) ,
                    verticalAlignment=Alignment.CenterVertically ,
                    horizontalArrangement=Arrangement.Start
                ) {
                    Text(
                        text=name ?: "" ,
                        fontWeight=FontWeight.Medium ,
                        fontSize=14.sp ,
                        fontFamily=Poppins
                    )
                    Spacer(modifier=Modifier.weight(1f))
                    Text(
                        text="Rp $formattedPrice" ,
                        fontWeight=FontWeight.Normal ,
                        fontSize=14.sp ,
                        fontFamily=Poppins ,
                        color=Color(0xFFFF7F50) ,
                        textAlign=androidx.compose.ui.text.style.TextAlign.End

                    )

                }

                Column(
                    modifier=Modifier.padding(horizontal=16.dp) ,

                    ) {
                    Spacer(modifier=Modifier.height(8.dp))

                    Text(
                        text="Fasilitas: " ,
                        fontWeight=FontWeight.Medium ,
                        fontSize=14.sp ,
                        fontFamily=Poppins
                    )

                    val formattedFeatures=formatDescription(features)

                    formattedFeatures.forEach { feature ->
                        Text(
                            text="• $feature" ,
                            fontSize=12.sp ,
                            fontFamily=Poppins ,
                            fontWeight=FontWeight.Normal ,

                            )
                    }

                }




                Spacer(modifier=Modifier.height(8.dp))

                Divider(
                    color=Color(0xFFE0E0E0) ,
                )

                Spacer(modifier=Modifier.height(8.dp))
            }



            item {
                Text(
                    text="Tambahan" ,
                    fontWeight=FontWeight.Bold ,
                    fontSize=16.sp ,
                    fontFamily=Poppins
                )

                inventories.forEach { inventory ->
                    Row(modifier=Modifier.padding(horizontal=16.dp)) {
                        TicketCounter(
                            label=inventory.name ,
                            price=ticketViewModel.formatPriceWithoutDecimal(inventory.price) ,
                            detail="" ,
                            count=inventoryViewModel.inventoryQuantities.value[inventory.name]
                                ?: 0 ,
                            onCountChange={ newCount ->
                                inventoryViewModel.updateInventoryQuantity(
                                    inventory.name ,
                                    newCount
                                )
                                transactionViewModel.amount.value=newCount
                                transactionViewModel.updateInventoriesWithQuantity(
                                    inventory.id ,
                                    newCount
                                )

                            }
                        )
                    }
                }
            }

            item {
                Divider()
                Spacer(modifier=Modifier.height(8.dp))
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
                        text="Rp $finalCost" ,
                        fontWeight=FontWeight.Bold ,
                        fontSize=16.sp ,
                        fontFamily=Poppins
                    )

                }
                Spacer(modifier=Modifier.height(8.dp))

                Divider()
                Spacer(modifier=Modifier.height(16.dp))

            }
        }
    }

}