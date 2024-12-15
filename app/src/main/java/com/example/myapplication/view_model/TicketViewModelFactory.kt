package com.example.myapplication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repositories.ticket.GetTicketRepository

class TicketViewModelFactory(
    private val getTicketRepository : GetTicketRepository ,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(TicketViewModel::class.java)) {
            return TicketViewModel(getTicketRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
