package com.example.myapplication.view_model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.transaction.PostTransactionResponse
import com.example.myapplication.data.models.transaction.TransactionCampingInventories
import com.example.myapplication.data.models.transaction.TransactionCampingTicket
import com.example.myapplication.data.models.transaction.TransactionTicket
import com.example.myapplication.data.repositories.transaction.PostTransactionCampingRepository
import com.example.myapplication.data.repositories.transaction.PostTransactionTicketRepository
import com.example.myapplication.utils.SharedPrefs
import com.example.myapplication.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val postTransactionTicketRepository : PostTransactionTicketRepository ,
    private val postTransactionCampingRepository : PostTransactionCampingRepository
) : ViewModel() {

    var date=mutableStateOf("2024-12-28")
    var tickets=mutableStateOf<List<TransactionTicket>>(emptyList())
    var campingTickets=mutableStateOf<List<TransactionCampingTicket>>(emptyList())
    var inventories=mutableStateOf<List<TransactionCampingInventories>>(emptyList())
    var totalPrice=mutableStateOf(0)
    var visitorsId=mutableStateOf<List<Int>>(emptyList())



    var amount=mutableStateOf(0)
    val nameTicket=mutableStateOf("")
    val ticketPrice=mutableStateOf(0)

    private var isLoading=mutableStateOf(false)
     var postTransactionResponse=mutableStateOf<PostTransactionResponse?>(null)
    private var errorMessage=mutableStateOf<String?>(null)

    var isSuccess=mutableStateOf(false)
    fun updateSelectedVisitorId(visitorId : Int) {
        if (!visitorsId.value.contains(visitorId)) {
            visitorsId.value=visitorsId.value + visitorId
        }
        Log.d("TransactionViewModel" , "updateSelectedVisitorId: $visitorsId")
    }

    fun isButtonTicketValid() : Boolean {
        return date.value.isNotEmpty() &&
                tickets.value.isNotEmpty() &&
                totalPrice.value > 0 &&
                visitorsId.value.isNotEmpty()
    }

    fun isButtonCampingValid() : Boolean {
        return date.value.isNotEmpty() &&
                campingTickets.value.isNotEmpty() &&
                inventories.value.isNotEmpty() &&
                totalPrice.value > 0 &&
                visitorsId.value.isNotEmpty()
    }

    fun updateInventoriesWithQuantity(inventoryId : Int , newCount : Int) {
        val updatedInventories=inventories.value.toMutableList()

        val inventoryIndex=updatedInventories.indexOfFirst { it.id == inventoryId }
        if (inventoryIndex >= 0) {
            updatedInventories[inventoryIndex]=updatedInventories[inventoryIndex].copy(amount=newCount)
        } else {
            updatedInventories.add(TransactionCampingInventories(id=inventoryId , amount=newCount))
        }

        inventories.value=updatedInventories.filter { it.amount > 0 }
        Log.d("TransactionViewModel" , "updateInventoriesWithQuantity: $inventories")
    }

    fun updateTicketsWithQuantity(ticketId : Int , newCount : Int) {
        val updatedTickets=tickets.value.toMutableList()

        val ticketIndex=updatedTickets.indexOfFirst { it.id == ticketId }
        if (ticketIndex >= 0) {
            updatedTickets[ticketIndex]=updatedTickets[ticketIndex].copy(amount=newCount)
        } else {
            updatedTickets.add(TransactionTicket(id=ticketId , amount=newCount))
        }

        tickets.value=updatedTickets.filter { it.amount > 0 }
        Log.d("TransactionViewModel" , "updateTicketsWithQuantity: $tickets")
    }

    fun totalTicket() : Int {
        val totalAmount=tickets.value.sumOf { it.amount ?: 0 }

        amount.value=totalAmount

        Log.d("TransactionViewModel" , "Total Tickets Amount: $totalAmount")

        return totalAmount
    }

    fun postTransactionTicket(context: Context) {
        isLoading.value = true
        val token = SharedPrefs.getToken(context)

        if (tickets.value.isNullOrEmpty()) {
            val ticketId = SharedPrefs.getTicketId(context)
            val amount =  1

            if (ticketId != null) {
                tickets.value = listOf(TransactionTicket(id = ticketId.toInt(), amount = amount))
            } else {
                isLoading.value = false
                errorMessage.value = "No ticketId found"
                showToast(context, "Ticket ID tidak ditemukan")
                return
            }
        }

        token?.let {
            viewModelScope.launch(Dispatchers.IO) {
                postTransactionTicketRepository.postTransactionTicket(
                    token = it,
                    date = date.value,
                    tickets = tickets.value,
                    totalPrice = totalPrice.value,
                    visitorsId = visitorsId.value,
                    callback = { response, error ->

                        isLoading.value = false
                        handleTransactionResponse(context, response, error)
                    }
                )
            }
        } ?: run {
            isLoading.value = false
            errorMessage.value = "No token found"
            showToast(context, "Token tidak ditemukan")
        }
    }


    fun postTransactionCamping(context: Context) {
        isLoading.value = true
        val token = SharedPrefs.getToken(context)
        val ticketId = SharedPrefs.getTicketId(context)

        if (ticketId != null) {
            val existingTicketIndex = campingTickets.value.indexOfFirst { it.id == ticketId.toInt() }
            val updatedCampingTickets = campingTickets.value.toMutableList()

            if (existingTicketIndex >= 0) {
                updatedCampingTickets[existingTicketIndex] =
                    updatedCampingTickets[existingTicketIndex].copy(amount = 1)
            } else {
                updatedCampingTickets.add(TransactionCampingTicket(id = ticketId.toInt(), amount = 1))
            }

            campingTickets.value = updatedCampingTickets
        } else {
            isLoading.value = false
            errorMessage.value = "No ticketId found"
            showToast(context, "Ticket ID tidak ditemukan")
            return
        }

        token?.let {
            viewModelScope.launch(Dispatchers.IO) {
                postTransactionCampingRepository.postTransactionCamping(
                    token = it,
                    date = date.value,
                    tickets = campingTickets.value,
                    inventories = inventories.value,
                    totalPrice = totalPrice.value,
                    visitorsId = visitorsId.value,
                    callback = { response, error ->
                        isLoading.value = false
                        handleTransactionResponse(context, response, error)
                    }
                )
            }
        } ?: run {
            isLoading.value = false
            errorMessage.value = "No token found"
            showToast(context, "Token tidak ditemukan")
        }
    }


    fun resetTransaction() {
        date.value=""
        tickets.value=emptyList()
        campingTickets.value=emptyList()
        inventories.value=emptyList()
        totalPrice.value=0
        visitorsId.value=emptyList()
        isSuccess.value=false
    }

    private fun handleTransactionResponse(
        context : Context ,
        response : PostTransactionResponse? ,
        error : String?
    ) {
        response?.let {
            postTransactionResponse.value=it
            isSuccess.value=true
            showToast(context , "Transaksi berhasil")
        }
        error?.let {
            errorMessage.value=it
            showToast(context , it)
        }
    }
}
