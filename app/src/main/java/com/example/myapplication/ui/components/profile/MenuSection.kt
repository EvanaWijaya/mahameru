package com.example.myapplication.ui.components.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.components.ConfirmDialog
import com.example.myapplication.view_model.UserViewModel

@Composable
fun MenuSection(navController : NavController , viewModel : UserViewModel) {
    val context=LocalContext.current

    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier=Modifier
            .fillMaxWidth()
            .padding(horizontal=16.dp) ,
        verticalArrangement=Arrangement.spacedBy(8.dp)
    ) {
        MenuItem(title="Ubah Kata Sandi") {
            navController.navigate("changePassword")

        }
        MenuItem(title="Daftar Pengunjung") {

        }
        MenuItem(title="Pusat Bantuan") {

        }
        MenuItem(title="Laporkan Masalah") {
            navController.navigate("problemReport")

        }
        MenuItem(title="Keluar") {
            showLogoutDialog=true
        }

    }

    if (showLogoutDialog) {
        ConfirmDialog(
            title="Pemberitahuan" ,
            subTitle="Apakah Anda yakin ingin keluar?" ,
            onDismiss={ showLogoutDialog=false } ,
            onSave={
                viewModel.logout(context = context )
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
    }

}
