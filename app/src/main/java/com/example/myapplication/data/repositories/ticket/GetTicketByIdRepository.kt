package com.example.myapplication.data.repositories.ticket

import com.example.myapplication.data.models.ticket.GetTicketByIdResponse
import com.example.myapplication.data.services.TicketService
import com.example.myapplication.utils.ErrorParser
import com.example.myapplication.utils.LogHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetTicketByIdRepository(private val ticketService : TicketService) {
    fun getTicketById(
        token : String ,
        ticketId : String ,
        callback : (GetTicketByIdResponse? , String?) -> Unit
    ) {
        ticketService.getTicketById(token , ticketId)
            .enqueue(object : Callback<GetTicketByIdResponse> {
                override fun onResponse(
                    call : Call<GetTicketByIdResponse> ,
                    response : Response<GetTicketByIdResponse>
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

                override fun onFailure(call : Call<GetTicketByIdResponse> , t : Throwable) {
                    LogHelper.logError(t.message)
                    callback(null , t.message)
                }
            })
    }
}
