package com.example.myapplication.view_model

import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.MainActivity
import com.example.myapplication.data.models.auth.LoginResponse
import com.example.myapplication.data.models.auth.RegisterResponse
import com.example.myapplication.data.repositories.auth.LoginRepository
import com.example.myapplication.data.repositories.auth.RegisterRepository
import com.example.myapplication.utils.SharedPrefs
import com.example.myapplication.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginRepository : LoginRepository ,
    private val registerRepository : RegisterRepository
) : ViewModel() {


    var username=mutableStateOf("")
    var password=mutableStateOf("")
    var isLoading=mutableStateOf(false)
    var loginResponse=mutableStateOf<LoginResponse?>(null)
    var errorMessage=mutableStateOf<String?>(null)

    var confirmPassword=mutableStateOf("")
    var email=mutableStateOf("")
    var registerResponse=mutableStateOf<RegisterResponse?>(null)

    val isFormValid=mutableStateOf(false)

    init {
        validateForm()
    }


    fun validateForm() {
        isFormValid.value=username.value.isNotBlank() &&
                email.value.isNotBlank() &&
                Patterns.EMAIL_ADDRESS.matcher(email.value).matches() &&
                password.value.isNotBlank() &&
                password.value == confirmPassword.value
    }

    fun login(context: Context) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.login(username.value, password.value) { response, error ->
                isLoading.value = false
                Log.d("AuthViewModel", "username:${username.value}, password: ${password.value}")

                if (response != null) {
                    loginResponse.value = response
                    errorMessage.value = null
                    response.data.token.let {
                        SharedPrefs.saveToken(context, it)
                    }

                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)

                    if (context is ComponentActivity) {
                        context.finish()
                    }

                    clearForm()

                } else {
                    errorMessage.value = error
                    showToast(context, error ?: "Login failed. Please check your credentials.")
                }
            }
        }
    }

    fun clearForm() {
        username.value = ""
        password.value = ""
        confirmPassword.value = ""
        email.value = ""
    }

    fun register(context: Context, navController: NavController) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            registerRepository.register(username.value, email.value, password.value) { response, error ->
                isLoading.value = false

                if (response != null) {
                    registerResponse.value = response
                    errorMessage.value = null
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                    clearForm()
                } else {
                    errorMessage.value = error
                    showToast(context, error ?: "Register failed. Please try again.")
                }
            }
        }
    }
}