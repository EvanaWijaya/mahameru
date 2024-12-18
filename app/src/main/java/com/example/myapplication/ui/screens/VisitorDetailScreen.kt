package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.components.ConfirmDialog
import com.example.myapplication.ui.components.transaction.StepIndicator
import com.example.myapplication.ui.components.transaction.TicketDetailBox
import com.example.myapplication.ui.components.transaction.TransactionAppBar
import com.example.myapplication.ui.components.transaction.VisitorDialog
import com.example.myapplication.ui.components.transaction.VisitorInputForm
import com.example.myapplication.ui.components.transaction.VisitorItem
import com.example.myapplication.view_model.TransactionViewModel
import com.example.myapplication.view_model.VisitorViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VisitorDetailScreen(
    navController : NavController ,
    transactionViewModel : TransactionViewModel ,
    visitorViewModel : VisitorViewModel
) {
    val steps=listOf("Pilih Tiket" , "Isi\nData" , "Bayar Tiket" , "Tiket Online")
    val ticketAmount=transactionViewModel.totalTicket()
    val ticketDate=transactionViewModel.date.value
    val bottomSheetState=rememberModalBottomSheetState(initialValue=ModalBottomSheetValue.Hidden)
    val scope=rememberCoroutineScope()
    var selectedVisitor by remember { mutableStateOf(-1) }
    var selectedItems by remember { mutableStateOf<Map<Int , String>>(emptyMap()) }
    var expanded by remember { mutableStateOf(false) }
    val getVisitorResponse=visitorViewModel.getVisitorResponse.value
    val items=getVisitorResponse?.data?.map { it.name to it.id } ?: listOf()
    var showVisitorForm by remember { mutableStateOf(false) }

    var showConfirmDialog by remember { mutableStateOf(false) }


    LaunchedEffect(true) {
        visitorViewModel.getVisitor(navController.context)
    }

    Scaffold(
        topBar={
            Column(modifier=Modifier.fillMaxWidth()) {
                StepIndicator(currentStep=2 , totalSteps=4 , steps=steps)
                TransactionAppBar(
                    title="Detail Pengunjung" ,
                    onNavigationClick={ navController.popBackStack() })
            }
        } ,
        bottomBar={
            if (!bottomSheetState.isVisible) {
                Button(
                    onClick = {
                        showConfirmDialog=true


                    },
                    enabled = selectedItems.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentPadding = PaddingValues(16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (selectedItems.isNotEmpty()) Color(0xFFFF7F50) else Color.Gray
                    )
                ) {
                    Text(
                        text = "Pesan",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = Poppins
                    )
                }
            }
        }
    ) { contentPadding ->
        ModalBottomSheetLayout(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            sheetState = bottomSheetState,
            sheetContent = {
                if (showVisitorForm) {
                    VisitorInputForm(
                        name = visitorViewModel.name.value,
                        nik = visitorViewModel.nik.value,
                        onNameChange = { visitorViewModel.name.value = it },
                        onNikChange = { visitorViewModel.nik.value = it },
                        onPostVisitor = {
                            visitorViewModel.postVisitor(navController.context)
                            showVisitorForm = false
                            visitorViewModel.getVisitor(navController.context)
                        },
                        onClick = {
                            showVisitorForm = false
                        }
                    )
                } else {
                    VisitorDialog(
                        bottomSheetState = bottomSheetState,
                        scope = scope,
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        selectedItem = selectedItems[selectedVisitor] ?: "",
                        onItemSelected = { selectedItem ->
                            selectedItems = selectedItems.toMutableMap().apply {
                                this[selectedVisitor] = selectedItem
                            }
                            val selectedVisitorId = items[selectedVisitor].second
                            transactionViewModel.updateSelectedVisitorId(selectedVisitorId)
                        },
                        items = items.map {
                            "${it.first}\nNIK: ${getVisitorResponse?.data?.find { visitor -> visitor.id == it.second }?.nik}"
                        },
                        onAddVisitor = {
                            scope.launch {
                                bottomSheetState.hide()
                                showVisitorForm = true
                                bottomSheetState.show()
                            }
                        }
                    )
                }
            }
        ) {
            LazyColumn(
                modifier=Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .padding(16.dp) ,
                verticalArrangement=Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Spacer(modifier=Modifier.height(16.dp))

                    TicketDetailBox(ticketAmount=ticketAmount , ticketDate=ticketDate, name = "Tiket Masuk")
                }
                item {
                    Spacer(modifier=Modifier.height(16.dp))
                    Text(
                        text="Data Pengunjung" ,
                        fontSize=16.sp ,
                        fontWeight=FontWeight.Bold ,
                        modifier=Modifier.padding(vertical=8.dp)
                    )
                }
                items(ticketAmount) { index ->
                    VisitorItem(
                        index=index ,
                        selectedItems=selectedItems ,
                        onClick={
                            selectedVisitor=index
                            scope.launch { bottomSheetState.show() }
                        }
                    )
                }

            }
        }
    }

    if (showConfirmDialog) {
        ConfirmDialog(
            title="Apakah data sudah benar?" ,
            subTitle="Pastikan tidak ada yang salah karena kamu tidak bisa mengubahnya setelah melanjutkan ke tahap pembayaran" ,
            onDismiss={ showConfirmDialog=false } ,
            onSave={
                navController.navigate("payment")

            }
        )
    }
}

