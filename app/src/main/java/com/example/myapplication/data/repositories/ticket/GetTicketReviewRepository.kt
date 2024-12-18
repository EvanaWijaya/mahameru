package com.example.myapplication.data.repositories.ticket

import com.example.myapplication.data.models.ticket.GetTicketReviewResponse
import com.example.myapplication.data.services.TicketService
import com.example.myapplication.utils.ErrorParser
import com.example.myapplication.utils.LogHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetTicketReviewRepository(private val ticketService : TicketService) {
    fun getReview(token : String , callback : (GetTicketReviewResponse? , String?) -> Unit) {
        ticketService.getReview(token).enqueue(object : Callback<GetTicketReviewResponse> {
            override fun onResponse(
                call : Call<GetTicketReviewResponse> ,
                response : Response<GetTicketReviewResponse>
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

            override fun onFailure(call : Call<GetTicketReviewResponse> , t : Throwable) {
                LogHelper.logError(t.message)

                callback(null , t.message)
            }
        })
    }
}
