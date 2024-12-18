package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.components.transaction.HelpSection
import com.example.myapplication.ui.components.transaction.TicketCard
import com.example.myapplication.ui.components.transaction.TransactionDetails
import com.example.myapplication.ui.components.transaction.VisitorData
import com.example.myapplication.view_model.TransactionViewModel

@Composable
fun TransactionDetailScreen(
    navController : NavController , transactionViewModel : TransactionViewModel
) {

    Column(
        modifier=Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F9FA))
    ) {
        Column(
            modifier=Modifier

                .padding(16.dp)
        ) {
            Row(
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(bottom=16.dp) ,
                verticalAlignment=Alignment.CenterVertically ,
                horizontalArrangement=Arrangement.Center
            ) {
                IconButton(onClick={
                    navController.navigate("tiket")
                }) {
                    Icon(
                        painter=painterResource(id=R.drawable.icon_back) ,
                        contentDescription="Back" ,
                        tint=Color.Black
                    )
                }
                Spacer(modifier=Modifier.weight(1f))
                Text(
                    text="Detail Transaksi" ,
                    fontFamily=Poppins ,
                    fontWeight=FontWeight.Bold ,
                    fontSize=20.sp ,
                    modifier=Modifier.padding(start=8.dp)
                )
            }
            Spacer(modifier=Modifier.height(16.dp))
            TransactionDetails(transactionViewModel)
            Spacer(modifier=Modifier.height(8.dp))
        }
        Box(
            modifier=Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color(0xFF00796B))
        )
        Spacer(modifier=Modifier.height(16.dp))
        TicketCard(transactionViewModel)
        Spacer(modifier=Modifier.height(16.dp))
        Box(
            modifier=Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color(0xFF00796B))
        )
        Column(
            modifier=Modifier

                .padding(16.dp)
        ) {

            Spacer(modifier=Modifier.height(16.dp))
            VisitorData(transactionViewModel)
            Spacer(modifier=Modifier.height(250.dp))
            HelpSection()
        }
    }


}



