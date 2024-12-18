package com.example.myapplication.data.services

import com.example.myapplication.data.models.inventory.GetInventoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface InventoryService {
    @GET("inventory")
    fun getInventory(@Header("Authorization") token: String): Call<GetInventoryResponse>
}
