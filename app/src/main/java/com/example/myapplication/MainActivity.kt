package com.example.myapplication

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
import com.example.myapplication.data.repositories.auth.GetVisitorRepository
import com.example.myapplication.data.repositories.auth.LoginRepository
import com.example.myapplication.data.repositories.auth.PostVisitorRepository
import com.example.myapplication.data.repositories.auth.PutVisitorRepository
import com.example.myapplication.data.repositories.auth.RegisterRepository
import com.example.myapplication.data.repositories.report.PostReportRepository
import com.example.myapplication.data.repositories.ticket.GetInventoryRepository
import com.example.myapplication.data.repositories.ticket.GetTicketByIdRepository
import com.example.myapplication.data.repositories.ticket.GetTicketHistoryRepository
import com.example.myapplication.data.repositories.ticket.GetTicketRepository
import com.example.myapplication.data.repositories.ticket.GetTicketReviewRepository
import com.example.myapplication.data.repositories.ticket.PutTicketReviewRepository
import com.example.myapplication.data.repositories.transaction.PostTransactionCampingRepository
import com.example.myapplication.data.repositories.transaction.PostTransactionTicketRepository
import com.example.myapplication.data.repositories.user.GetProfileRepository
import com.example.myapplication.data.repositories.user.PatchProfileRepository
import com.example.myapplication.data.repositories.user.PostUpdatePasswordRepository
import com.example.myapplication.data.repositories.user.PutProfilePictureRepository
import com.example.myapplication.data.services.ApiClient
import com.example.myapplication.data.services.InventoryService
import com.example.myapplication.data.services.ReportService
import com.example.myapplication.data.services.TicketService
import com.example.myapplication.data.services.TransactionService
import com.example.myapplication.data.services.UserService
import com.example.myapplication.data.services.VisitorService
import com.example.myapplication.ui.components.BottomNavigationBar
import com.example.myapplication.ui.screens.ChangePasswordScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.PaymentCampingScreen
import com.example.myapplication.ui.screens.PaymentScreen
import com.example.myapplication.ui.screens.ProblemReportScreen
import com.example.myapplication.ui.screens.ProfileScreen
import com.example.myapplication.ui.screens.ReviewScreen
import com.example.myapplication.ui.screens.TicketBookingScreen
import com.example.myapplication.ui.screens.TicketCampingBookingScreen
import com.example.myapplication.ui.screens.TicketHistoryScreen
import com.example.myapplication.ui.screens.TicketScreen
import com.example.myapplication.ui.screens.TransactionDetailScreen
import com.example.myapplication.ui.screens.TransactionScreen
import com.example.myapplication.ui.screens.VisitorCampingDetailScreen
import com.example.myapplication.ui.screens.VisitorDetailScreen
import com.example.myapplication.utils.SharedPrefs
import com.example.myapplication.view_model.AuthViewModel
import com.example.myapplication.view_model.AuthViewModelFactory
import com.example.myapplication.view_model.InventoryViewModel
import com.example.myapplication.view_model.InventoryViewModelFactory
import com.example.myapplication.view_model.ReportViewModel
import com.example.myapplication.view_model.ReportViewModelFactory
import com.example.myapplication.view_model.TicketViewModel
import com.example.myapplication.view_model.TicketViewModelFactory
import com.example.myapplication.view_model.TransactionViewModel
import com.example.myapplication.view_model.TransactionViewModelFactory
import com.example.myapplication.view_model.UserViewModel
import com.example.myapplication.view_model.UserViewModelFactory
import com.example.myapplication.view_model.VisitorViewModel
import com.example.myapplication.view_model.VisitorViewModelFactory
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

    val transactionService=token?.let {
        ApiClient.genericService(
            serviceClass=TransactionService::class.java ,
            token=it
        )
    }

    val visitorService=token?.let {
        ApiClient.genericService(
            serviceClass=VisitorService::class.java ,
            token=it
        )
    }
    val inventoryService=token?.let {
        ApiClient.genericService(
            serviceClass=InventoryService::class.java ,
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
    val getTicketByIdRepository=GetTicketByIdRepository(ticketService)
    val putTicketReviewRepository=PutTicketReviewRepository(ticketService)
    val getTicketHistoryRepository=GetTicketHistoryRepository(ticketService)
    val getTicketReviewRepository=GetTicketReviewRepository(ticketService)
    val ticketViewModelFactory=TicketViewModelFactory(getTicketRepository , getTicketByIdRepository, putTicketReviewRepository, getTicketHistoryRepository, getTicketReviewRepository)
    val ticketViewModel=viewModel<TicketViewModel>(factory=ticketViewModelFactory)

    val postTransactionTicketRepository=PostTransactionTicketRepository(transactionService!!)
    val postTransactionCampingRepository=PostTransactionCampingRepository(transactionService)
    val transactionViewModelFactory=TransactionViewModelFactory(
        postTransactionTicketRepository ,
        postTransactionCampingRepository
    )
    val transactionViewModel=viewModel<TransactionViewModel>(factory=transactionViewModelFactory)

    val postVisitorRepository=PostVisitorRepository(visitorService!!)
    val putVisitorRepository=PutVisitorRepository(visitorService)
    val getVisitorRepository=GetVisitorRepository(visitorService)
    val visitorViewModelFactory=
        VisitorViewModelFactory(postVisitorRepository , putVisitorRepository , getVisitorRepository)
    val visitorViewModel=viewModel<VisitorViewModel>(factory=visitorViewModelFactory)

    val getInventoryRepository=GetInventoryRepository(inventoryService!!)
    val inventoryViewModelFactory=InventoryViewModelFactory(getInventoryRepository)
    val inventoryViewModel=viewModel<InventoryViewModel>(factory=inventoryViewModelFactory)

    // Use ProvideWindowInsets to manage window insets (status bar, navigation bar)
    ProvideWindowInsets {
        Scaffold(
            bottomBar={
                val currentRoute by navController.currentBackStackEntryAsState()
                val currentDestination=currentRoute?.destination?.route

                // Tampilkan Bottom Navigation Bar kecuali di halaman tertentu
                if (currentDestination != "ticketBookingScreen" && currentDestination != "ticketHistory" && currentDestination != "problemReport" && currentDestination != "changePassword" && currentDestination != "login" && currentDestination != "visitorDetail" && currentDestination != "payment" && currentDestination != "transactionDetail" && currentDestination != "campingBookingScreen/{ticketId}" && currentDestination != "visitorCampingDetail" && currentDestination != "paymentCamping" && currentDestination != "review") {
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
                composable("beranda") { HomeScreen(navController , userViewModel, ticketViewModel) }
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
                composable("tiket") { TicketScreen(navController , ticketViewModel) }


                composable("ticketBookingScreen") {
                    TicketBookingScreen(navController , ticketViewModel , transactionViewModel)
                }
                composable("transaction") { TransactionScreen(navController, ticketViewModel) }


                composable("ticketHistory") {
                    TicketHistoryScreen(navController,  ticketViewModel, userViewModel)
                }
                composable("profil") { ProfileScreen(navController , userViewModel) }
                composable("changePassword") {
                    ChangePasswordScreen(navController , userViewModel)
                }
                composable("problemReport") {
                    ProblemReportScreen(navController , reportViewModel)
                }
                composable("login") {
                    LoginScreen(navController=navController , authViewModel)
                }
                composable("visitorDetail") {
                    VisitorDetailScreen(
                        navController=navController ,
                        transactionViewModel ,
                        visitorViewModel
                    )
                }
                composable("review") {
                    ReviewScreen(
                        ticketViewModel,
                        navController
                    )
                }
                composable("visitorCampingDetail") {
                    VisitorCampingDetailScreen(
                        navController=navController ,
                        transactionViewModel ,
                        visitorViewModel
                    )
                }
                composable("payment") {
                    PaymentScreen(
                        navController ,
                        userViewModel ,
                        transactionViewModel ,
                        ticketViewModel
                    )
                }
                composable("paymentCamping") {
                    PaymentCampingScreen(
                        navController ,
                        userViewModel ,
                        transactionViewModel ,
                        ticketViewModel,
                        inventoryViewModel
                    )
                }
                composable("transactionDetail") {
                    TransactionDetailScreen(navController , transactionViewModel)
                }

                composable(
                    "campingBookingScreen/{ticketId}" ,
                    arguments=listOf(navArgument("ticketId") { type=NavType.StringType })
                ) { backStackEntry ->
                    val ticketId=backStackEntry.arguments?.getString("ticketId")
                    TicketCampingBookingScreen(
                        navController ,
                        ticketViewModel ,
                        transactionViewModel ,
                        inventoryViewModel ,
                        ticketId ?: ""
                    )
                }


            }
        }
    }
}