package com.example.myapplication.data.repositories.auth

import com.example.myapplication.data.models.auth.RegisterRequest
import com.example.myapplication.data.models.auth.RegisterResponse
import com.example.myapplication.data.services.AuthService
import com.example.myapplication.utils.ErrorParser
import com.example.myapplication.utils.LogHelper
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRepository(private val authService: AuthService) {
    fun register(
        username: String,
        email: String,
        password: String,
        callback: (RegisterResponse?, String?) -> Unit
    ) {
        val registerRequest = RegisterRequest(username, email, password)
        authService.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                LogHelper.logRequestDetails(
                    url="${call.request().url}" ,
                    headers="${call.request().headers}" ,
                    body=Gson().toJson(registerRequest)
                )
                LogHelper.logResponseDetails(response.code() , response.body()?.toString())

                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    val errorBody=ErrorParser.parseErrorMessage(response.errorBody())
                    LogHelper.logError("$errorBody")
                    callback(null, errorBody)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                LogHelper.logError(t.message)
                callback(null, t.message)
            }
        })
    }
}
