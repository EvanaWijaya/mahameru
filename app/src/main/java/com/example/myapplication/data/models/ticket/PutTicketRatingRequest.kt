package com.example.myapplication.data.models.ticket

import com.google.gson.annotations.SerializedName

data class PutTicketRatingRequest(
    @SerializedName("transaction_ticket_id") val transactionTicketId : Int ,
    val rating : Int ,
    val review : String
)

