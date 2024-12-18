package com.example.myapplication.data.models.transaction

import com.google.gson.annotations.SerializedName

data class PostTransactionCampingRequest(
    val date: String ,
    val tickets: List<TransactionCampingTicket> ,
    val inventories: List<TransactionCampingInventories> ,
    @SerializedName("total_price") val totalPrice : Int ,
    @SerializedName("visitors_id") val visitorsId : List<Int> ,
)

data class TransactionCampingTicket(
    val id: Int,
    val amount: Int
)

data class TransactionCampingInventories(
    val id: Int,
    val amount: Int
)