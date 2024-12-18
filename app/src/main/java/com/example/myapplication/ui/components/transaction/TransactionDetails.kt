package com.example.myapplication.ui.components.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.view_model.TransactionViewModel

@Composable
fun TransactionDetails(transactionViewModel: TransactionViewModel) {
    Column(
        modifier =Modifier
            .fillMaxWidth()
            .padding(bottom=16.dp)
    ) {
        StatusRow(transactionViewModel)
        Spacer(modifier = Modifier.height(8.dp))
        DetailRow(label = "No Transaksi", value = transactionViewModel.postTransactionResponse.value?.data?.transactionNumber ?: "NPM15YT")
        Spacer(modifier = Modifier.height(12.dp))
        DetailRow(label = "Kategori", value = "Tiket Masuk")
        Spacer(modifier = Modifier.height(12.dp))
        DetailRow(label = "Tanggal Transaksi", value = transactionViewModel.postTransactionResponse.value?.data?.transactionDate ?: "17 Desember 2024")
        Spacer(modifier = Modifier.height(12.dp))
        DetailRow(label = "Metode Pembayaran", value = "Transfer Bank")
        Spacer(modifier = Modifier.height(12.dp))
        DetailRow(label = "Total Bayar", value = "Rp ${transactionViewModel.postTransactionResponse.value?.data?.totalPrice.toString()}", isBold = true, color = Color(0xFFFF7043))
    }
}