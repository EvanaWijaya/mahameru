package com.example.myapplication.data.services


import com.example.myapplication.data.models.visitor.GetVisitorResponse
import com.example.myapplication.data.models.visitor.VisitorRequest
import com.example.myapplication.data.models.visitor.VisitorResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VisitorService {
    @POST("visitor")
    fun postVisitor(
        @Header("Authorization") token: String ,
        @Body request : VisitorRequest) : Call<VisitorResponse>

    @PUT("visitor/{id}")
    fun putVisitor(
        @Header("Authorization") token : String ,
        @Path("id") id : String,
        @Body request : VisitorRequest) : Call<VisitorResponse>

    @GET("visitor")
    fun getVisitor(
        @Header("Authorization") token : String) : Call<GetVisitorResponse>
}