package com.example.myapplication.ui.components.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.view_model.TransactionViewModel

@Composable
fun VisitorData(transactionViewModel: TransactionViewModel) {
    val visitors = transactionViewModel.postTransactionResponse.value?.data?.visitors.orEmpty()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Data pengunjung",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        visitors.forEachIndexed { index, visitor ->
            VisitorRow(
                index = index + 1,
                name = visitor.name,
                nik = visitor.nik
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
