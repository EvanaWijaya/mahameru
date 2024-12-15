package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.components.ActionButton
import com.example.myapplication.ui.components.CustomTopAppBar
import com.example.myapplication.ui.components.DropdownWithLabel
import com.example.myapplication.ui.components.TextFieldWithLabel
import com.example.myapplication.view_model.ReportViewModel

@Composable
fun ProblemReportScreen(
    navController: NavController, viewModel: ReportViewModel
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState)
    ) {
        CustomTopAppBar(
            title = "Laporkan Masalah",
            onBackClick = {
                navController.popBackStack()
            }
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(26.dp))

            TextFieldWithLabel(
                value = viewModel.name.value,
                onValueChange = { viewModel.name.value = it },
                label = "Nama Lengkap"
            )

            Spacer(modifier = Modifier.height(13.dp))

            TextFieldWithLabel(
                value = viewModel.email.value,
                onValueChange = { viewModel.email.value = it },
                label = "Email"
            )

            Spacer(Modifier.height(13.dp))

            DropdownWithLabel(
                value = viewModel.about.value,
                onValueChange = { viewModel.about.value = it },
                label = "Perihal",
                options = listOf(
                    "Pertanyaan dan Informasi",
                    "Permintaan",
                    "Aspirasi dan Saran",
                    "Keluhan dan Kendala"
                )
            )

            Spacer(modifier = Modifier.height(13.dp))

            TextFieldWithLabel(
                value = viewModel.message.value,
                onValueChange = { viewModel.message.value = it },
                label = "Detail Pesan",
                minLines = 5
            )

            Spacer(Modifier.height(27.dp))

            ActionButton(
                enabled = viewModel.isButtonValid(),
                modifier = Modifier
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    viewModel.createReport(context)
                    navController.popBackStack()
                },
                label = "Kirim"
            )
        }
    }
}
