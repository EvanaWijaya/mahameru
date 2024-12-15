package com.example.myapplication.data.repositories.report

import com.example.myapplication.data.models.report.PostReportRequest
import com.example.myapplication.data.models.report.PostReportResponse
import com.example.myapplication.data.services.ReportService
import com.example.myapplication.utils.ErrorParser
import com.example.myapplication.utils.LogHelper
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostReportRepository(private val reportService : ReportService) {

    fun updatePassword(
        token : String ,
        name : String ,
        email : String ,
        about : String ,
        message : String ,
        callback : (PostReportResponse? , String?) -> Unit
    ) {
        val postReportRequest=PostReportRequest(
            name=name ,
            email=email ,
            about=about ,
            message=message
        )
        reportService.createReport(token , postReportRequest)
            .enqueue(object : Callback<PostReportResponse> {
                override fun onResponse(
                    call : Call<PostReportResponse> ,
                    response : Response<PostReportResponse>
                ) {
                    LogHelper.logRequestDetails(
                        url="${call.request().url}" ,
                        headers="${call.request().headers}" ,
                        body=Gson().toJson(postReportRequest)
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

                override fun onFailure(call : Call<PostReportResponse> , t : Throwable) {
                    LogHelper.logError(t.message)
                    callback(null , t.message)
                }
            })
    }

}
