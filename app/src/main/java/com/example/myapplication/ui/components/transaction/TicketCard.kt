package com.example.myapplication.ui.components.transaction

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.screens.Poppins
import com.example.myapplication.view_model.TransactionViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID"))
    val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "ID"))
    val date = inputFormat.parse(dateString)
    return outputFormat.format(date)
}

@Composable
fun TicketCard(transactionViewModel: TransactionViewModel) {
    val date = transactionViewModel.postTransactionResponse.value?.data?.date.toString()
    val formattedDate = formatDate(date)
    var showDialog by remember { mutableStateOf(false) }

    val qrCodeBitmap = generateQRCode(transactionViewModel.postTransactionResponse.value?.data?.transactionCode.toString())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFF7043))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Tiket Masuk",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "TEPAS PAPANDAYAN – (${transactionViewModel.postTransactionResponse.value?.data?.visitorAmount.toString()} Tiket Masuk)",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "$formattedDate (08:00 - 18:00)",
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clickable { showDialog = true }
                    ) {
                        Image(
                            bitmap = qrCodeBitmap.asImageBitmap(),
                            contentDescription = "QR Code",
                            modifier = Modifier.size(60.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Kode Pesanan",
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                        Text(
                            text = transactionViewModel.postTransactionResponse.value?.data?.transactionCode.toString(),
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            containerColor = Color(0xFFF5F5F5),

            onDismissRequest = { showDialog = false },
            confirmButton = {

            },
            text = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        androidx.compose.material3.Text(
                            text="Kode Pesanan" ,
                            fontSize=16.sp ,
                            fontWeight=FontWeight.Bold ,
                            textAlign=TextAlign.Center,
                            fontFamily = Poppins

                        )
                        androidx.compose.material3.Text(
                            text=transactionViewModel.postTransactionResponse.value?.data?.transactionCode.toString() ,
                            fontSize=14.sp ,
                            textAlign=TextAlign.Center,
                            fontFamily = Poppins

                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Image(
                            bitmap = qrCodeBitmap.asImageBitmap(),
                            contentDescription = "QR Code",
                            modifier = Modifier.size(200.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        androidx.compose.material3.Text(
                            text="Pindai Tiket Anda" ,
                            fontSize=14.sp ,
                            textAlign=TextAlign.Center,
                            fontFamily = Poppins,
                            color = Color(0xFFFF7F50)

                        )

                    }
                }
            }
        )
    }
}

fun generateQRCode(orderCode: String): Bitmap {
    val width = 512
    val height = 512
    val bitMatrix: BitMatrix = MultiFormatWriter().encode(
        orderCode, BarcodeFormat.QR_CODE, width, height
    )

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    for (x in 0 until width) {
        for (y in 0 until height) {
            bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.Black.hashCode() else Color.White.hashCode())
        }
    }
    return bitmap
}
