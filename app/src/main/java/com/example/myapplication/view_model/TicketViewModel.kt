package com.example.myapplication.view_model

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.ticket.GetTicketData
import com.example.myapplication.data.models.ticket.GetTicketResponse
import com.example.myapplication.data.repositories.ticket.GetTicketRepository
import com.example.myapplication.utils.SharedPrefs
import com.example.myapplication.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TicketViewModel(
    private val getTicketRepository: GetTicketRepository,
) : ViewModel() {

    private var isLoading = mutableStateOf(false)
    private var getTicketResponse = mutableStateOf<GetTicketResponse?>(null)
    private var errorMessage = mutableStateOf<String?>(null)

    private var ticketsWithTiket = mutableStateOf<List<GetTicketData>>(emptyList())
    private var ticketsWithoutTiket = mutableStateOf<List<GetTicketData>>(emptyList())

    fun getTicket(context: Context) {
        isLoading.value = true
        val token = SharedPrefs.getToken(context)

        token?.let {
            viewModelScope.launch(Dispatchers.IO) {
                getTicketRepository.getTicket(it) { response, error ->
                    isLoading.value = false
                    if (response != null) {
                        getTicketResponse.value = response
                        errorMessage.value = null

                        val tickets = response.data
                        ticketsWithTiket.value = tickets.filter { it.name.contains("tiket", ignoreCase = true) }
                        ticketsWithoutTiket.value = tickets.filter { !it.name.contains("tiket", ignoreCase = true) }

                    } else {
                        errorMessage.value = error
                        showToast(context, error ?: "Gagal mendapatkan data tiket")
                    }
                }
            }
        } ?: run {
            isLoading.value = false
            errorMessage.value = "No token found"
            showToast(context, "Token tidak ditemukan")
        }
    }

    fun getTicketsWithTiket() = ticketsWithTiket.value

    fun getTicketsWithoutTiket() = ticketsWithoutTiket.value

    fun processDescription(description: String): List<String> {
        val sentences = description.split(Regex("[.,]+"))
            .filter { it.isNotEmpty() }
            .map { it.trim() }

        return sentences
    }
}

