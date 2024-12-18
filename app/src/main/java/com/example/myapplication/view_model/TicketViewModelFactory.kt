package com.example.myapplication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repositories.ticket.GetTicketByIdRepository
import com.example.myapplication.data.repositories.ticket.GetTicketHistoryRepository
import com.example.myapplication.data.repositories.ticket.GetTicketRepository
import com.example.myapplication.data.repositories.ticket.GetTicketReviewRepository
import com.example.myapplication.data.repositories.ticket.PutTicketReviewRepository

class TicketViewModelFactory(
    private val getTicketRepository : GetTicketRepository ,
    private val getTicketByIdRepository : GetTicketByIdRepository,
    private val putTicketReviewRepository : PutTicketReviewRepository,
    private val getTicketHistoryRepository : GetTicketHistoryRepository,
    private val getTicketReviewRepository : GetTicketReviewRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(TicketViewModel::class.java)) {
            return TicketViewModel(getTicketRepository, getTicketByIdRepository, putTicketReviewRepository, getTicketHistoryRepository, getTicketReviewRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
