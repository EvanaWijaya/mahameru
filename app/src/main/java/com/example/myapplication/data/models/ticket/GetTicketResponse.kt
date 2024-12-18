package com.example.myapplication.data.models.ticket

import com.google.gson.annotations.SerializedName

data class GetTicketResponse(
    val data: List<GetTicketData>)

data class GetTicketData(
    val id : Int ,
    val name : String ,
    val price : String ,
    @SerializedName("visitor_quantity") val visitorQuantity : Int ,
    val description : String
)
