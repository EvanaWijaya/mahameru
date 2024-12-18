package com.example.myapplication.data.models.visitor

data class VisitorResponse(
    val data: VisitorResponseData,
)

data class VisitorResponseData(
    val name: String,
    val nik: String,
)