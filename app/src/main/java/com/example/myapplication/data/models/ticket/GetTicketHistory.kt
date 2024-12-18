package com.example.myapplication.data.models.ticket

import com.google.gson.annotations.SerializedName

data class GetTicketHistoryResponse(
    val data: List<GetTicketHistoryData>)

data class GetTicketHistoryData(
    val name : String ,
    val price : String ,
    val description : String ,
    @SerializedName("transaction_ticket_id") val transactionTicketId : Int ,
    )
