package com.example.myapplication.data.repositories.ticket

import com.example.myapplication.data.models.ticket.PutTicketRatingRequest
import com.example.myapplication.data.models.ticket.PutTicketRatingResponse
import com.example.myapplication.data.services.TicketService
import com.example.myapplication.utils.ErrorParser
import com.example.myapplication.utils.LogHelper
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PutTicketReviewRepository(private val ticketService : TicketService) {

    fun putTicket(
        token : String ,
       transactionTicketId: Int,
        rating: Int ,
        review: String ,
        callback : (PutTicketRatingResponse? , String?) -> Unit
    ) {
        val putTicketReviewRequest=PutTicketRatingRequest(
          transactionTicketId = transactionTicketId,  rating=rating ,
            review=review
        )
        ticketService.putTicketRating(token , putTicketReviewRequest)
            .enqueue(object : Callback<PutTicketRatingResponse> {
                override fun onResponse(
                    call : Call<PutTicketRatingResponse> ,
                    response : Response<PutTicketRatingResponse>
                ) {
                    LogHelper.logRequestDetails(
                        url="${call.request().url}" ,
                        headers="${call.request().headers}" ,
                        body=Gson().toJson(putTicketReviewRequest)
                    )
                    LogHelper.logResponseDetails(response.code() , response.body()?.toString())

                    if (response.isSuccessful) {
                        callback(response.body() , null)
                    } else {
                        val errorBody=ErrorParser.parseErrorMessage(response.errorBody())
                        LogHelper.logError("$errorBody")
                        callback(null , errorBody)
                    }
                }
                override fun onFailure(call : Call<PutTicketRatingResponse> , t : Throwable) {
                    LogHelper.logError(t.message)
                    callback(null , t.message)
                }
            })
    }

}
