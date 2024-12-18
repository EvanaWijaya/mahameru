package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.components.CustomSuccessDialog
import com.example.myapplication.ui.components.transaction.ButtonTransaction
import com.example.myapplication.ui.components.transaction.StepIndicator
import com.example.myapplication.ui.components.transaction.TicketRow
import com.example.myapplication.view_model.InventoryViewModel
import com.example.myapplication.view_model.TicketViewModel
import com.example.myapplication.view_model.TransactionViewModel
import com.example.myapplication.view_model.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PaymentCampingScreen(
    navController : NavController ,
    userViewModel : UserViewModel ,
    transactionViewModel : TransactionViewModel ,
    ticketViewModel : TicketViewModel,
    inventoryViewModel : InventoryViewModel
) {
    val steps=listOf("Pilih Tiket" , "Isi\nData" , "Bayar Tiket" , "Tiket Online")
    val poppinsFontFamily=FontFamily(
        Font(R.font.poppins_regular , FontWeight.Normal) ,
        Font(R.font.poppins_medium , FontWeight.Medium) ,
        Font(R.font.poppins_semibold , FontWeight.SemiBold) ,
        Font(R.font.poppins_bold , FontWeight.Bold)
    )

    val inventories = inventoryViewModel.getInventorys()?.data ?: emptyList()


    if (transactionViewModel.isSuccess.value) {
        CustomSuccessDialog(
            image=painterResource(id=R.drawable.success_icon) ,
            title="Pembayaran Sukses" ,
            subtitle="Tiket kamu berhasil di proses\nSelamat bersenang-senang!" ,
            onDismiss={ transactionViewModel.isSuccess.value=false }
        )
    }

    val finalCost=transactionViewModel.totalPrice.value

    Scaffold(
        bottomBar={
            BottomAppBar(
                contentPadding=PaddingValues(16.dp) ,
                containerColor=Color.White
            ) {
                ButtonTransaction(
                    text="Bayar" ,
                    onClick={
                        transactionViewModel.viewModelScope.launch {
                            val hasInventories = inventoryViewModel.getInventorys()?.data
                                ?.any { (inventoryViewModel.inventoryQuantities.value[it.name] ?: 0) > 0 } == true

                            if (hasInventories) {
                                transactionViewModel.postTransactionCamping(context=navController.context)
                            } else {
                                transactionViewModel.postTransactionTicket(context=navController.context)
                            }

                            delay(2000)
                            if (transactionViewModel.isSuccess.value) {
                                navController.navigate("transactionDetail")

                            }
                            delay(2000)
                            if (transactionViewModel.isSuccess.value) {
                                transactionViewModel.resetTransaction()

                            }

                        }
                    } ,
                    enabled=finalCost > 0 ,
                    backgroundColor=Color(0xFFFF7F50) ,
                    fontFamily=poppinsFontFamily
                )

            }
        }
    ) { paddingValues ->
        Column(
            modifier=Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
        ) {
            StepIndicator(currentStep=3 , totalSteps=4 , steps=steps)

            Column(
                modifier=Modifier
                    .fillMaxSize()
                    .padding(horizontal=16.dp , vertical=8.dp)
            ) {
                Text(
                    text="Data pemesan" ,
                    fontFamily=poppinsFontFamily ,
                    fontWeight=FontWeight.Bold ,
                    fontSize=20.sp ,
                    modifier=Modifier.padding(top=16.dp)
                )
                Text(
                    text="Informasi tiket akan dikirimkan ke kontak pemesan" ,
                    fontFamily=poppinsFontFamily ,
                    fontWeight=FontWeight.Normal ,
                    fontSize=14.sp ,
                    color=Color.Gray ,
                    modifier=Modifier.padding(bottom=8.dp)
                )

                Box(
                    modifier=Modifier
                        .fillMaxWidth()
                        .border(2.dp , Color(0xFF00796B) , RoundedCornerShape(8.dp))
                        .background(Color.White , shape=RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Column(verticalArrangement=Arrangement.spacedBy(8.dp)) {
                        Text(
                            text="Nama Lengkap" ,
                            fontWeight=FontWeight.Bold ,
                            fontFamily=poppinsFontFamily
                        )
                        Text(text=userViewModel.username.value , fontFamily=poppinsFontFamily)
                        Text(
                            text="Alamat Email" ,
                            fontWeight=FontWeight.Bold ,
                            fontFamily=poppinsFontFamily
                        )
                        Text(text=userViewModel.email.value , fontFamily=poppinsFontFamily)
                        Text(
                            text="Nomor Telepon" ,
                            fontWeight=FontWeight.Bold ,
                            fontFamily=poppinsFontFamily
                        )
                        Text(text=userViewModel.phoneNumber.value , fontFamily=poppinsFontFamily)
                    }
                }

                TicketRow(
                    label= transactionViewModel.nameTicket.value,
                    price=ticketViewModel.processPrice(transactionViewModel.ticketPrice.value.toString())
                )

                inventories.forEach { inventories ->
                    val quantity=inventoryViewModel.inventoryQuantities.value[inventories.name] ?: 0
                    if (quantity > 0) {
                        TicketRow(
                            label="${inventories.name} (x$quantity)" ,
                            price=ticketViewModel.processPrice(inventories.price)
                        )
                    }
                }

                Text(
                    text="Metode Pembayaran" ,
                    fontWeight=FontWeight.SemiBold ,
                    fontSize=16.sp ,
                    fontFamily=poppinsFontFamily ,
                    modifier=Modifier.padding(top=16.dp)
                )
                Text(
                    text="Transfer Bank" ,
                    fontSize=14.sp ,
                    color=Color.Gray ,
                    fontFamily=poppinsFontFamily
                )

                Spacer(modifier=Modifier.weight(1f))

                Divider(
                    color=Color.Gray ,
                    thickness=1.dp ,
                    modifier=Modifier.fillMaxWidth()
                )
                Row(
                    modifier=Modifier
                        .fillMaxWidth()
                        .padding(vertical=10.dp) ,
                    horizontalArrangement=Arrangement.SpaceBetween
                ) {
                    Text(
                        text="Total Bayar" ,
                        fontWeight=FontWeight.Bold ,
                        fontFamily=poppinsFontFamily
                    )
                    Text(
                        text="Rp $finalCost" ,
                        fontWeight=FontWeight.Bold ,
                        color=Color.Black ,
                        fontFamily=poppinsFontFamily
                    )
                }
                Divider(
                    color=Color.Gray ,
                    thickness=1.dp ,
                    modifier=Modifier.fillMaxWidth()
                )
            }
        }
    }
}
