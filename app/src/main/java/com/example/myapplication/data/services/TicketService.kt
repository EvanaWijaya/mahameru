package com.example.myapplication.data.services

import com.example.myapplication.data.models.ticket.GetTicketResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface TicketService {
    @GET("ticket")
    fun getTicket(@Header("Authorization") token: String): Call<GetTicketResponse>
}
