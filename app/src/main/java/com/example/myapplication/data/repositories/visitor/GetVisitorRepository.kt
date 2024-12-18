package com.example.myapplication.data.repositories.auth

import com.example.myapplication.data.models.visitor.GetVisitorResponse
import com.example.myapplication.data.services.VisitorService
import com.example.myapplication.utils.ErrorParser
import com.example.myapplication.utils.LogHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetVisitorRepository(private val visiorService: VisitorService) {
    fun getVisitor(
        token : String ,
        callback: (GetVisitorResponse? , String?) -> Unit
    ) {
        visiorService.getVisitor(token).enqueue(object : Callback<GetVisitorResponse> {
            override fun onResponse(call: Call<GetVisitorResponse>, response: Response<GetVisitorResponse>) {
                LogHelper.logRequestDetails(
                    url="${call.request().url}" ,
                    headers="${call.request().headers}" ,
                    body=""
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

            override fun onFailure(call: Call<GetVisitorResponse>, t: Throwable) {
                LogHelper.logError(t.message)
                callback(null, t.message)
            }
        })
    }
}
