package com.example.myapplication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repositories.ticket.GetInventoryRepository

class InventoryViewModelFactory(
    private val getInventoryRepository : GetInventoryRepository ,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            return InventoryViewModel(getInventoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
