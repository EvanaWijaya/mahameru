package com.example.myapplication.ui.components.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.view_model.UserViewModel

@Composable
fun MenuSection(navController : NavController, viewModel: UserViewModel ){
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MenuItem(title = "Ubah Kata Sandi") {
            navController.navigate("changePassword")

        }
        MenuItem(title = "Daftar Pengunjung") {

        }
        MenuItem(title = "Pusat Bantuan") {

        }
        MenuItem(title = "Laporkan Masalah") {
            navController.navigate("problemReport")

        }
        MenuItem(title = "Keluar") {
            viewModel.logout(context = context )
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }

        }
    }
}
