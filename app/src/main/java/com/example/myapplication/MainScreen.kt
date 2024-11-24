@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            val currentRoute by navController.currentBackStackEntryAsState()
            val currentDestination = currentRoute?.destination?.route
            TopBar(title = currentDestination?.capitalize() ?: "Aplikasi")
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "beranda",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("beranda") { BerandaScreen() }
            composable("tiket") { TiketScreen() }
            composable("transaksi") { TransaksiScreen() }
            composable("profil") { ProfilScreen() }
        }
    }
}

@Composable
fun TopBar(title: String) {
    TopAppBar(
        title = { Text(text = title, fontWeight = FontWeight.Bold, color = Color.White) },
        modifier = Modifier.statusBarsPadding(),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFF00BCD4))
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentRoute by navController.currentBackStackEntryAsState()
    val currentDestination = currentRoute?.destination?.route

    NavigationBar(containerColor = Color.White) {
        val items = listOf(
            BottomNavItem("Beranda", "beranda", R.drawable.icon_home),
            BottomNavItem("Tiket", "tiket", R.drawable.icon_tiket),
            BottomNavItem("Transaksi", "transaksi", R.drawable.icon_transaksi),
            BottomNavItem("Profil", "profil", R.drawable.icon_profile)
        )
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        tint = if (currentDestination == item.route) Color(0xFF00796B) else Color(0xFFCBD5E1) // Ikon aktif dengan warna hijau penuh
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (currentDestination == item.route) Color(0xFF00796B) else Color(0xFFCBD5E1) // Label aktif dengan warna hijau
                    )
                },
                selected = currentDestination == item.route,
                onClick = {
                    if (currentDestination != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent // Tidak ada latar belakang abu-abu
                )
            )
        }
    }
}

@Composable
fun BerandaScreen() {
    Text(text = "Beranda", modifier = Modifier.padding(16.dp))
}

@Composable
fun TiketScreen() {
    Text(text = "Tiket", modifier = Modifier.padding(16.dp))
}

@Composable
fun TransaksiScreen() {
    Text(text = "Transaksi", modifier = Modifier.padding(16.dp))
}

@Composable
fun ProfilScreen() {
    Text(text = "Profil", modifier = Modifier.padding(16.dp))
}

data class BottomNavItem(val label: String, val route: String, val icon: Int)
