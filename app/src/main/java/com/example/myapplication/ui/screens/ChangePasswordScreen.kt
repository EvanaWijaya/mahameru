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
import com.example.myapplication.ui.components.TextFieldWithLabel
import com.example.myapplication.view_model.UserViewModel

@Composable
fun ChangePasswordScreen(
    navController : NavController , viewModel : UserViewModel) {
    val scrollState=rememberScrollState()
    val context=LocalContext.current

    Column(
        modifier=Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState)
    ) {
        CustomTopAppBar(
            title="Atur Ulang Kata Sandi" ,
            onBackClick={
                navController.popBackStack()
            }
        )


        Column(
            modifier=Modifier
                .padding(16.dp)
        ) {

            Spacer(modifier=Modifier.height(26.dp))
            TextFieldWithLabel(
                value=viewModel.oldPassword.value ,
                onValueChange={ viewModel.oldPassword.value=it } ,
                label="Kata Sandi Lama"
            )

            Spacer(modifier=Modifier.height(13.dp))

            TextFieldWithLabel(
                value=viewModel.newPassword.value ,
                onValueChange={ viewModel.newPassword.value=it } ,
                label="Kata Sandi Baru"
            )

            Spacer(modifier=Modifier.height(13.dp))

            TextFieldWithLabel(
                value=viewModel.confirmPassword.value ,
                onValueChange={ viewModel.confirmPassword.value=it } ,
                label="Konfirmasi Kata Sandi Baru"
            )

            Spacer(Modifier.height(27.dp))

            ActionButton(
                enabled = viewModel.isButtonUpdatePasswordValid() ,
                modifier=Modifier.width(200.dp)
                    .align(Alignment.CenterHorizontally) ,

                onClick={

                    viewModel.postUpdatePassword(context)
                    navController.popBackStack()

                } ,
                label="Simpan"
            )

        }
    }

}