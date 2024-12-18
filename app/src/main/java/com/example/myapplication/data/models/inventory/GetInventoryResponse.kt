package com.example.myapplication.data.models.inventory

data class GetInventoryResponse(
    val data: List<GetInventoryData>)

data class GetInventoryData(
    val id : Int ,
    val code : String ,
    val name : String ,
    val price : String
)
