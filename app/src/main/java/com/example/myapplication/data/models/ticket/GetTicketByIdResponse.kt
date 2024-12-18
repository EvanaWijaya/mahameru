package com.example.myapplication.data.models.ticket

import com.google.gson.annotations.SerializedName

data class GetTicketByIdResponse(
    val data: GetTicketByIdData
)

data class GetTicketByIdData(
    val name : String ,
    val price : String ,
    @SerializedName("stock_quantity") val stockQuantity : Int ,
    @SerializedName("visitor_quantity") val visitorQuantity : Int ,
    val description : String
)