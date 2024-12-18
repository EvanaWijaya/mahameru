package com.example.myapplication.data.services

import com.example.myapplication.data.models.ticket.GetTicketByIdResponse
import com.example.myapplication.data.models.ticket.GetTicketHistoryResponse
import com.example.myapplication.data.models.ticket.GetTicketResponse
import com.example.myapplication.data.models.ticket.GetTicketReviewResponse
import com.example.myapplication.data.models.ticket.PutTicketRatingRequest
import com.example.myapplication.data.models.ticket.PutTicketRatingResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface TicketService {
    @GET("ticket")
    fun getTicket(@Header("Authorization") token: String): Call<GetTicketResponse>

    @GET("ticket/{id}")
    fun getTicketById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<GetTicketByIdResponse>

    @PUT("ticket/review_rating")
    fun putTicketRating(
        @Header("Authorization") token: String,
        @Body request: PutTicketRatingRequest
    ): Call<PutTicketRatingResponse>

    @GET("ticket_history")
    fun getHistory(@Header("Authorization") token: String): Call<GetTicketHistoryResponse>
    @GET("ticket_review_rating")
    fun getReview(@Header("Authorization") token: String): Call<GetTicketReviewResponse>
}
