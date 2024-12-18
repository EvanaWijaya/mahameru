package com.example.myapplication.data.repositories.transaction

import com.example.myapplication.data.models.transaction.PostTransactionResponse
import com.example.myapplication.data.models.transaction.PostTransactionTicketRequest
import com.example.myapplication.data.models.transaction.TransactionTicket
import com.example.myapplication.data.services.TransactionService
import com.example.myapplication.utils.ErrorParser
import com.example.myapplication.utils.LogHelper
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostTransactionTicketRepository(private val transactionService: TransactionService) {

    fun postTransactionTicket(
        token: String,
        date: String,
        tickets: List<TransactionTicket>,
        totalPrice: Int,
        visitorsId: List<Int>,
        callback: (PostTransactionResponse?, String?) -> Unit
    ) {
        val postTransactionTicketRequest = PostTransactionTicketRequest(
            date = date,
            tickets = tickets,
            totalPrice = totalPrice,
            visitorsId = visitorsId
        )

        transactionService.postTransactionTicket(token, postTransactionTicketRequest)
            .enqueue(object : Callback<PostTransactionResponse> {
                override fun onResponse(
                    call: Call<PostTransactionResponse>,
                    response: Response<PostTransactionResponse>
                ) {
                    LogHelper.logRequestDetails(
                        url = "${call.request().url}",
                        headers = "${call.request().headers}",
                        body = Gson().toJson(postTransactionTicketRequest)
                    )
                    LogHelper.logResponseDetails(response.code(), response.body()?.toString())

                    if (response.isSuccessful) {
                        callback(response.body(), null)
                    } else {
                        val errorBody = ErrorParser.parseErrorMessage(response.errorBody())
                        LogHelper.logError("$errorBody")
                        callback(null, errorBody)
                    }
                }

                override fun onFailure(call: Call<PostTransactionResponse>, t: Throwable) {
                    LogHelper.logError(t.message)
                    callback(null, t.message)
                }
            })
    }
}
