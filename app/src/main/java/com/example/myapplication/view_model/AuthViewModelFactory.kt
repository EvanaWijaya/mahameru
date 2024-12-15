package com.example.myapplication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repositories.auth.LoginRepository
import com.example.myapplication.data.repositories.auth.RegisterRepository

class AuthViewModelFactory(
    private val loginRepository : LoginRepository ,
    private val registerRepository : RegisterRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(loginRepository , registerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
