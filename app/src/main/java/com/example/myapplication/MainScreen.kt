@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication

import DetailPengunjungScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    // Use ProvideWindowInsets to manage window insets (status bar, navigation bar)
    ProvideWindowInsets {
        Scaffold(
            bottomBar = {
                val currentRoute by navController.currentBackStackEntryAsState()
                val currentDestination = currentRoute?.destination?.route

                // Tampilkan Bottom Navigation Bar kecuali di halaman tertentu
                if (currentDestination != "ticketBookingScreen" && currentDestination != "riwayatBerhasil") {
                    BottomNavigationBar(navController)
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "beranda",
                modifier = Modifier
                    .padding(innerPadding) // Apply padding from window insets
            ) {
                composable("beranda") { HomeScreen(navController) }
                composable(
                    "guide/{facilityName}",
                    arguments = listOf(navArgument("facilityName") { type = NavType.StringType })
                ) { backStackEntry ->
                    val facilityName = backStackEntry.arguments?.getString("facilityName") ?: "Unknown Facility"
                    TourismGuideScreen(
                        facilityName = facilityName,
                        onBackClick = { navController.popBackStack() }
                    )
                }
                composable("tiket") { TicketScreen(navController) }
                composable("ticketBookingScreen") {
                    // Halaman TicketBookingScreen tanpa bottom bar
                    TicketBookingScreen(navController)
                }
                composable("transaksi") { TransaksiPembayaranScreen(navController) }
                composable("riwayatBerhasil") {
                    RiwayatSuksesScreen(onBackClick = { navController.popBackStack() })
                }
                composable("profil") { ProfileScreen() }
                composable("detailPengunjung") {
                    DetailPengunjungScreen(navController)
                }
            }
        }
    }
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
                        tint = if (currentDestination == item.route) Color(0xFF00796B) else Color(0xFFCBD5E1)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (currentDestination == item.route) Color(0xFF00796B) else Color(0xFFCBD5E1)
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
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

data class BottomNavItem(val label: String, val route: String, val icon: Int)
