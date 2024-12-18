package com.example.myapplication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repositories.transaction.PostTransactionCampingRepository
import com.example.myapplication.data.repositories.transaction.PostTransactionTicketRepository

class TransactionViewModelFactory(
    private val postTransactionTicketRepository : PostTransactionTicketRepository,
    private val postTransactionCampingRepository : PostTransactionCampingRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            return TransactionViewModel(postTransactionTicketRepository, postTransactionCampingRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
