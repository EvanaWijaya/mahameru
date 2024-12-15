package com.example.myapplication.data.repositories.ticket

import com.example.myapplication.data.models.ticket.GetTicketResponse
import com.example.myapplication.data.services.TicketService
import com.example.myapplication.utils.ErrorParser
import com.example.myapplication.utils.LogHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetTicketRepository(private val ticketService : TicketService) {
    fun getTicket(token : String , callback : (GetTicketResponse? , String?) -> Unit) {
        ticketService.getTicket(token).enqueue(object : Callback<GetTicketResponse> {
            override fun onResponse(
                call : Call<GetTicketResponse> ,
                response : Response<GetTicketResponse>
            ) {
                LogHelper.logRequestDetails(
                    url="${call.request().url}" ,
                    headers="${call.request().headers}" ,
                    body=""
                )
                LogHelper.logResponseDetails(response.code() , response.body()?.toString())


                if (response.isSuccessful) {
                    callback(response.body() , null)
                } else {
                    val errorBody=ErrorParser.parseErrorMessage(response.errorBody())

                    callback(null , errorBody)
                }
            }

            override fun onFailure(call : Call<GetTicketResponse> , t : Throwable) {
                LogHelper.logError(t.message)

                callback(null , t.message)
            }
        })
    }
}
