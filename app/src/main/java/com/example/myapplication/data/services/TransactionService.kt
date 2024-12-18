package com.example.myapplication.data.services

import com.example.myapplication.data.models.transaction.PostTransactionCampingRequest
import com.example.myapplication.data.models.transaction.PostTransactionResponse
import com.example.myapplication.data.models.transaction.PostTransactionTicketRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TransactionService {
    @POST("transaction")
    fun postTransactionTicket(
        @Header("Authorization") token: String,
        @Body request: PostTransactionTicketRequest
    ): Call<PostTransactionResponse>

    @POST("transaction")
    fun postTransactionCamping(
        @Header("Authorization") token: String,
        @Body request: PostTransactionCampingRequest
    ): Call<PostTransactionResponse>
}
