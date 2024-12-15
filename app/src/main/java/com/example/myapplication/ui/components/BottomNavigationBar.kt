package com.example.myapplication.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.R
import com.example.myapplication.data.models.BottomNavItem

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentRoute by navController.currentBackStackEntryAsState()
    val currentDestination = currentRoute?.destination?.route

    NavigationBar(containerColor = Color.White) {
        val items = listOf(
            BottomNavItem("Beranda", "beranda", R.drawable.icon_home) ,
            BottomNavItem("Tiket", "tiket", R.drawable.icon_tiket) ,
            BottomNavItem("Transaksi", "transaksi", R.drawable.icon_transaksi) ,
            BottomNavItem("Profil", "profil", R.drawable.icon_profile)
        )
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon) ,
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