package com.example.myapplication.data.services

import com.example.myapplication.data.models.report.PostReportRequest
import com.example.myapplication.data.models.report.PostReportResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ReportService {
    @POST("problem_report")

    fun createReport(
        @Header("Authorization") token: String ,
        @Body request: PostReportRequest): Call<PostReportResponse>
}