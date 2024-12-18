package com.example.myapplication.data.models.transaction

import com.google.gson.annotations.SerializedName

data class PostTransactionTicketRequest(
    val date: String ,
    val tickets: List<TransactionTicket> ,
    @SerializedName("total_price") val totalPrice : Int ,
    @SerializedName("visitors_id") val visitorsId : List<Int> ,
)

data class TransactionTicket(
    val id: Int,
    val amount: Int
)
