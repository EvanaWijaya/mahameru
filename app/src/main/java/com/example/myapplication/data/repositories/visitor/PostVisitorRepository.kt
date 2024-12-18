package com.example.myapplication.data.repositories.auth

import com.example.myapplication.data.models.visitor.VisitorRequest
import com.example.myapplication.data.models.visitor.VisitorResponse
import com.example.myapplication.data.services.VisitorService
import com.example.myapplication.utils.ErrorParser
import com.example.myapplication.utils.LogHelper
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostVisitorRepository(private val visiorService: VisitorService) {
    fun postVisitor(
        token : String ,
        name: String,
        nik: String,
        callback: (VisitorResponse? , String?) -> Unit
    ) {
        val visitorRequest = VisitorRequest(name, nik)
        visiorService.postVisitor(token, visitorRequest).enqueue(object : Callback<VisitorResponse> {
            override fun onResponse(call: Call<VisitorResponse>, response: Response<VisitorResponse>) {
                LogHelper.logRequestDetails(
                    url="${call.request().url}" ,
                    headers="${call.request().headers}" ,
                    body=Gson().toJson(visitorRequest)
                )
                LogHelper.logResponseDetails(response.code() , response.body()?.toString())

                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    val errorBody=ErrorParser.parseErrorMessage(response.errorBody())
                    LogHelper.logError("$errorBody")
                    callback(null, errorBody)
                }
            }

            override fun onFailure(call: Call<VisitorResponse>, t: Throwable) {
                LogHelper.logError(t.message)
                callback(null, t.message)
            }
        })
    }
}
