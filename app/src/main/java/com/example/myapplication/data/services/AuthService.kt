package com.example.myapplication.data.services

import com.example.myapplication.data.models.auth.LoginRequest
import com.example.myapplication.data.models.auth.LoginResponse
import com.example.myapplication.data.models.auth.RegisterRequest
import com.example.myapplication.data.models.auth.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
    @POST("register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>
}