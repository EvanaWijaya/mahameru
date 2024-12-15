package com.example.myapplication.data.repositories.auth

import com.example.myapplication.data.models.auth.LoginRequest
import com.example.myapplication.data.models.auth.LoginResponse
import com.example.myapplication.data.services.AuthService
import com.example.myapplication.utils.ErrorParser
import com.example.myapplication.utils.LogHelper
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(private val authService: AuthService) {
    fun login(
        username: String,
        password: String,
        callback: (LoginResponse?, String?) -> Unit
    ) {
        val loginRequest = LoginRequest(username, password)
        authService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                LogHelper.logRequestDetails(
                    url="${call.request().url}" ,
                    headers="${call.request().headers}" ,
                    body=Gson().toJson(loginRequest)
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

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                LogHelper.logError(t.message)
                callback(null, t.message)
            }
        })
    }
}
