package com.example.myapplication

import DetailPengunjungScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.data.repositories.auth.LoginRepository
import com.example.myapplication.data.repositories.auth.RegisterRepository
import com.example.myapplication.data.repositories.report.PostReportRepository
import com.example.myapplication.data.repositories.ticket.GetTicketRepository
import com.example.myapplication.data.repositories.user.GetProfileRepository
import com.example.myapplication.data.repositories.user.PatchProfileRepository
import com.example.myapplication.data.repositories.user.PostUpdatePasswordRepository
import com.example.myapplication.data.repositories.user.PutProfilePictureRepository
import com.example.myapplication.data.services.ApiClient
import com.example.myapplication.data.services.ReportService
import com.example.myapplication.data.services.TicketService
import com.example.myapplication.data.services.UserService
import com.example.myapplication.ui.components.BottomNavigationBar
import com.example.myapplication.ui.screens.ChangePasswordScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.ProblemReportScreen
import com.example.myapplication.ui.screens.ProfileScreen
import com.example.myapplication.utils.SharedPrefs
import com.example.myapplication.view_model.AuthViewModel
import com.example.myapplication.view_model.AuthViewModelFactory
import com.example.myapplication.view_model.ReportViewModel
import com.example.myapplication.view_model.ReportViewModelFactory
import com.example.myapplication.view_model.TicketViewModel
import com.example.myapplication.view_model.TicketViewModelFactory
import com.example.myapplication.view_model.UserViewModel
import com.example.myapplication.view_model.UserViewModelFactory
import com.google.accompanist.insets.ProvideWindowInsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp() {
    val navController=rememberNavController()
    val token=SharedPrefs.getToken(context=LocalContext.current)
    val userService=token?.let {
        ApiClient.genericService(
            serviceClass=UserService::class.java ,
            token=it
        )
    }

    val reportService=token?.let {
        ApiClient.genericService(
            serviceClass=ReportService::class.java ,
            token=it
        )
    }

    val ticketService=token?.let {
        ApiClient.genericService(
            serviceClass=TicketService::class.java ,
            token=it
        )
    }
    val getProfileRepository=GetProfileRepository(userService!!)
    val patchProfileRepository=PatchProfileRepository(userService)
    val putProfilePictureRepository=PutProfilePictureRepository(userService)
    val postUpdatePasswordRepository=PostUpdatePasswordRepository(userService)
    val userViewModelFactory=UserViewModelFactory(
        getProfileRepository ,
        patchProfileRepository ,
        putProfilePictureRepository ,
        postUpdatePasswordRepository
    )
    val userViewModel=viewModel<UserViewModel>(factory=userViewModelFactory)

    val postReportRepository=PostReportRepository(reportService!!)
    val reportViewModelFactory=ReportViewModelFactory(postReportRepository)
    val reportViewModel=viewModel<ReportViewModel>(factory=reportViewModelFactory)

    val loginRepository=LoginRepository(ApiClient.authService)
    val registerRepository=RegisterRepository(ApiClient.authService)

    val authViewModelFactory=AuthViewModelFactory(loginRepository , registerRepository)
    val authViewModel=viewModel<AuthViewModel>(factory=authViewModelFactory)

    val getTicketRepository=GetTicketRepository(ticketService!!)
    val ticketViewModelFactory=TicketViewModelFactory(getTicketRepository)
    val ticketViewModel=viewModel<TicketViewModel>(factory=ticketViewModelFactory)
    // Use ProvideWindowInsets to manage window insets (status bar, navigation bar)
    ProvideWindowInsets {
        Scaffold(
            bottomBar={
                val currentRoute by navController.currentBackStackEntryAsState()
                val currentDestination=currentRoute?.destination?.route

                // Tampilkan Bottom Navigation Bar kecuali di halaman tertentu
                if (currentDestination != "ticketBookingScreen" && currentDestination != "riwayatBerhasil" && currentDestination != "problemReport" && currentDestination != "changePassword" && currentDestination != "login") {
                    BottomNavigationBar(navController)
                }
            }

        )


        { innerPadding ->
            NavHost(
                navController=navController ,

                startDestination="beranda" ,
                modifier=Modifier
                    .padding(innerPadding) // Apply padding from window insets
            ) {
                composable("beranda") { HomeScreen(navController , userViewModel) }
                composable(
                    "guide/{facilityName}" ,
                    arguments=listOf(navArgument("facilityName") { type=NavType.StringType })
                ) { backStackEntry ->
                    val facilityName=
                        backStackEntry.arguments?.getString("facilityName") ?: "Unknown Facility"
                    TourismGuideScreen(
                        facilityName=facilityName ,
                        onBackClick={ navController.popBackStack() }
                    )
                }
                composable("tiket") { TicketScreen(navController, ticketViewModel) }
                composable("ticketBookingScreen") {
                    // Halaman TicketBookingScreen tanpa bottom bar
                    TicketBookingScreen(navController)
                }
                composable("transaksi") { TransaksiPembayaranScreen(navController) }
                composable("riwayatBerhasil") {
                    RiwayatSuksesScreen(onBackClick={ navController.popBackStack() })
                }
                composable("profil") { ProfileScreen(navController , userViewModel) }
                composable("detailPengunjung") {
                    DetailPengunjungScreen(navController)
                }
                composable("changePassword") {
                    ChangePasswordScreen(navController , userViewModel)
                }
                composable("problemReport") {
                    ProblemReportScreen(navController , reportViewModel)
                }
                composable("login") {
                    LoginScreen(navController=navController , authViewModel)
                }
            }
        }
    }
}