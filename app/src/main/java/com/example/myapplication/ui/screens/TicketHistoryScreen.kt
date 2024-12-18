package com.example.myapplication.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.components.CustomFloatingActionButton
import com.example.myapplication.ui.components.CustomTopAppBar
import com.example.myapplication.ui.components.EmptyView
import com.example.myapplication.ui.components.transaction.ReviewItem
import com.example.myapplication.ui.components.transaction.ReviewUndone
import com.example.myapplication.view_model.TicketViewModel
import com.example.myapplication.view_model.UserViewModel


@Composable
fun TicketHistoryScreen(
    navController : NavController ,
    ticketViewModel : TicketViewModel ,
    userViewModel : UserViewModel

) {


    val context=LocalContext.current
    LaunchedEffect(Unit) {
        ticketViewModel.getReview(context)
        userViewModel.getProfile(context)
        ticketViewModel.getHistoryTicket(context)
    }
    val ticketReview=ticketViewModel.getTicketReviewResponse.value?.data ?: emptyList()
    val ticketHistory = ticketViewModel.getTicketHistoryResponse.value?.data ?: emptyList()

    val ticketReviewWithName = ticketReview.map { review ->
        val matchingTicket = ticketHistory.find { it.transactionTicketId == review.transactionTicketId }
        review.copy(name = matchingTicket?.name ?: "Unknown")
    }
    Scaffold(
        topBar={
            CustomTopAppBar(
                onBackClick={ navController.popBackStack() } ,
                title="Riwayat Tiket" ,
                showBackIcon=true
            )
        } ,
        floatingActionButton={
            CustomFloatingActionButton(
                onClick={
                    navController.navigate("review")
                } , containerColor=if (ticketHistory.isEmpty()) Color.Gray else Color(0xFF00796B)

            )
        }
    ) { paddingValues ->
        Column(
            modifier=Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .background(Color(0xFFF5F5F5))
        ) {



            if (ticketHistory.isEmpty()) {
                Text(
                    text = "Riwayat Review",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontFamily = Poppins
                )
                EmptyView(
                    title = "Belum Ada Riwayat Tiket",
                    description = "Ayo pesan segera dan nikmati liburanmu",
                    painter = painterResource(id = R.drawable.riwayat)
                )
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tiket Saya",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = Poppins,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                ReviewUndone(ticketViewModel)
                Spacer(modifier = Modifier.height(16.dp))

                if (ticketReviewWithName.isNotEmpty()) {
                    Text(
                        text = "Riwayat Ulasan",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontFamily = Poppins
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ticketReviewWithName.forEach { review ->
                        ReviewItem(
                            userName = userViewModel.username.value,
                            ticketType = review.name,
                            rating = review.rating.toInt(),
                            review = review.review,
                            pictureUrl = userViewModel.pictureUrl.value
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }


    }

}