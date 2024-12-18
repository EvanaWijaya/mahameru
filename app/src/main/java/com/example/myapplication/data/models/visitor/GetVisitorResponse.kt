package com.example.myapplication.data.models.visitor

data class GetVisitorResponse(
    val data: List<GetVisitorResponseData>,
)

data class GetVisitorResponseData(
    val id: Int,
    val name: String,
    val nik: String,
)