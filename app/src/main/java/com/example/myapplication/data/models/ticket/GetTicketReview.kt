package com.example.myapplication.data.models.ticket

import com.google.gson.annotations.SerializedName

data class GetTicketReviewResponse(
    val data : List<GetTicketReviewData>
)

data class GetTicketReviewData(
    @SerializedName("transaction_ticket_id") val transactionTicketId : Int ,
    val review : String ,
    val rating : String ,

    var name: String = ""
)
