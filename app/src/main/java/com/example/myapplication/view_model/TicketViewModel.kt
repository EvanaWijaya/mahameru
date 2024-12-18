package com.example.myapplication.view_model

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.ticket.GetTicketByIdResponse
import com.example.myapplication.data.models.ticket.GetTicketData
import com.example.myapplication.data.models.ticket.GetTicketHistoryResponse
import com.example.myapplication.data.models.ticket.GetTicketResponse
import com.example.myapplication.data.models.ticket.GetTicketReviewResponse
import com.example.myapplication.data.repositories.ticket.GetTicketByIdRepository
import com.example.myapplication.data.repositories.ticket.GetTicketHistoryRepository
import com.example.myapplication.data.repositories.ticket.GetTicketRepository
import com.example.myapplication.data.repositories.ticket.GetTicketReviewRepository
import com.example.myapplication.data.repositories.ticket.PutTicketReviewRepository
import com.example.myapplication.utils.SharedPrefs
import com.example.myapplication.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TicketViewModel(
    private val getTicketRepository : GetTicketRepository ,
    private val getTicketByIdRepository : GetTicketByIdRepository ,
    private val putTicketReviewRepository : PutTicketReviewRepository,
    private val getTicketHistoryRepository : GetTicketHistoryRepository,
    private val getTicketReviewRepository : GetTicketReviewRepository
) : ViewModel() {

    var isLoading=mutableStateOf(false)
     var getTicketResponse=mutableStateOf<GetTicketResponse?>(null)
    var errorMessage=mutableStateOf<String?>(null)
    var ticketId=mutableStateOf(0)
    var ticketQuantity=mutableStateOf(0)
    var totalCost=mutableStateOf(0)
    var getTicketByIdResponse=mutableStateOf<GetTicketByIdResponse?>(null)
    var getTicketHistoryResponse=mutableStateOf<GetTicketHistoryResponse?>(null)
    var getTicketReviewResponse=mutableStateOf<GetTicketReviewResponse?>(null)

    var transactionTicketId = mutableStateOf(0)
    var rating = mutableStateOf(0)
    var review = mutableStateOf("")

    private var ticketsWithTiket=mutableStateOf<List<GetTicketData>>(emptyList())
    private var ticketsWithoutTiket=mutableStateOf<List<GetTicketData>>(emptyList())


    var ticketQuantities=mutableStateOf<Map<String , Int>>(emptyMap())

    fun calculateTotalCost() {
        var newTotalCost=0
        ticketsWithTiket.value.forEach { ticket ->
            val ticketPrice=parsePriceWithoutDecimal(ticket.price)
            val quantity=ticketQuantities.value[ticket.name] ?: 0
            newTotalCost+=ticketPrice * quantity
        }
        totalCost.value=newTotalCost
    }

    private fun parsePriceWithoutDecimal(price : String?) : Int {
        return price?.split(".")?.get(0)?.toIntOrNull() ?: 0
    }

    fun updateTicketQuantity(ticketName : String , quantity : Int) {
        ticketQuantities.value=ticketQuantities.value.toMutableMap().apply {
            this[ticketName]=quantity
        }
        calculateTotalCost()
    }

    fun resetTicketQuantities() {
        ticketQuantities.value = emptyMap()
        ticketQuantity.value = 0
        totalCost.value = 0
    }


    fun formatPriceWithoutDecimal(price : String?) : String {
        return price?.split(".")?.get(0) ?: "0"
    }


    fun getTicket(context : Context) {
        isLoading.value=true
        val token=SharedPrefs.getToken(context)

        token?.let {
            viewModelScope.launch(Dispatchers.IO) {
                getTicketRepository.getTicket(it) { response , error ->
                    isLoading.value=false
                    if (response != null) {
                        getTicketResponse.value=response
                        errorMessage.value=null

                        val tickets=response.data
                        ticketsWithTiket.value=
                            tickets.filter { it.name.contains("tiket" , ignoreCase=true) }
                        ticketsWithoutTiket.value=
                            tickets.filter { !it.name.contains("tiket" , ignoreCase=true) }

                    } else {
                        errorMessage.value=error
                        showToast(context , error ?: "Gagal mendapatkan data tiket")
                    }
                }
            }
        } ?: run {
            isLoading.value=false
            errorMessage.value="No token found"
            showToast(context , "Token tidak ditemukan")
        }
    }

    fun getTicketById(context : Context , ticketId : String) {
        isLoading.value=true
        val token=SharedPrefs.getToken(context)

        token?.let {
            viewModelScope.launch(Dispatchers.IO) {
                getTicketByIdRepository.getTicketById(it , ticketId) { response , error ->
                    isLoading.value=false
                    if (response != null) {
                        getTicketByIdResponse.value=response
                        errorMessage.value=null
                        SharedPrefs.saveTicketId(context , ticketId)
                        calculateTotalCost()
                    } else {
                        errorMessage.value=error
                        showToast(context , error ?: "Gagal mendapatkan data tiket berdasarkan ID")
                    }
                }
            }
        } ?: run {
            isLoading.value=false
            errorMessage.value="No token found"
            showToast(context , "Token tidak ditemukan")
        }
    }

    fun getHistoryTicket (context : Context) {
        isLoading.value=true
        val token=SharedPrefs.getToken(context)

        token?.let {
            viewModelScope.launch(Dispatchers.IO) {
                getTicketHistoryRepository.getHistory(it) { response , error ->
                    isLoading.value=false
                    if (response != null) {
                        getTicketHistoryResponse.value= response
                        errorMessage.value=null

                    } else {
                        errorMessage.value=error
                        showToast(context , error ?: "Gagal mendapatkan data tiket")
                    }
                }
            }
        } ?: run {
            isLoading.value=false
            errorMessage.value="No token found"
            showToast(context , "Token tidak ditemukan")
        }
    }

    fun clearForm () {
        transactionTicketId.value = 0
        rating.value = 0
        review.value = ""
    }

    var isSuccess = mutableStateOf(false)

    fun putTicketRating(context : Context) {
        isLoading.value=true
        val token=SharedPrefs.getToken(context)
        val transactionTicketId = transactionTicketId.value
        val rating = rating.value
        val review = review.value

        token?.let {
            viewModelScope.launch(Dispatchers.IO) {
                putTicketReviewRepository.putTicket(it, transactionTicketId, rating, review) { response , error ->
                    isLoading.value=false
                    if (response != null) {
                        errorMessage.value=null
                        showToast(context , "Berhasil memberikan rating")
                        isSuccess.value = true
                        clearForm()

                    } else {
                        errorMessage.value=error
                        showToast(context , error ?: "Gagal memberikan rating")
                    }
                }
            }
        } ?: run {
            isLoading.value=false
            errorMessage.value="No token found"
            showToast(context , "Token tidak ditemukan")
        }
    }

    fun getReview(context : Context) {
        isLoading.value=true
        val token=SharedPrefs.getToken(context)

        token?.let {
            viewModelScope.launch(Dispatchers.IO) {
                getTicketReviewRepository.getReview(it) { response , error ->
                    isLoading.value=false
                    if (response != null) {
                        getTicketReviewResponse.value= response
                        errorMessage.value=null

                    } else {
                        errorMessage.value=error
                        showToast(context , error ?: "Gagal mendapatkan data review")
                    }
                }
            }
        } ?: run {
            isLoading.value=false
            errorMessage.value="No token found"
            showToast(context , "Token tidak ditemukan")
        }
    }




    fun getTicketsWithTiket()=ticketsWithTiket.value

    fun getTicketsWithoutTiket()=ticketsWithoutTiket.value

    fun processDescription(description : String) : List<String> {
        val sentences=description.split(Regex("[.,]+"))
            .filter { it.isNotEmpty() }
            .map { it.trim() }

        return sentences
    }

    fun processPrice(price : String?) : String {
        val priceWithoutDecimal=price?.split(".")?.get(0) ?: "0"
        return "Rp ${priceWithoutDecimal.replace(Regex("(\\d)(?=(\\d{3})+(?!\\d))") , "$1.")}"
    }


}
