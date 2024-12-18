package com.example.myapplication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repositories.auth.GetVisitorRepository
import com.example.myapplication.data.repositories.auth.PostVisitorRepository
import com.example.myapplication.data.repositories.auth.PutVisitorRepository

class VisitorViewModelFactory(
    private val postVisitorRepository : PostVisitorRepository,
    private val putVisitorRepository : PutVisitorRepository,
    private val getVisitorRepository : GetVisitorRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(VisitorViewModel::class.java)) {
            return VisitorViewModel(postVisitorRepository, putVisitorRepository, getVisitorRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
