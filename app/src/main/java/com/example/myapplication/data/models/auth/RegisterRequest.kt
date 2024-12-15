package com.example.myapplication.data.models.auth

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)