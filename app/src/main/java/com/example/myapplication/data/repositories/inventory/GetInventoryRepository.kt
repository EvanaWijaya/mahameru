package com.example.myapplication.data.repositories.ticket

import com.example.myapplication.data.models.inventory.GetInventoryResponse
import com.example.myapplication.data.services.InventoryService
import com.example.myapplication.utils.ErrorParser
import com.example.myapplication.utils.LogHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetInventoryRepository(private val inventoryService : InventoryService) {
    fun getInventory(token : String , callback : (GetInventoryResponse? , String?) -> Unit) {
        inventoryService.getInventory(token).enqueue(object : Callback<GetInventoryResponse> {
            override fun onResponse(
                call : Call<GetInventoryResponse> ,
                response : Response<GetInventoryResponse>
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

            override fun onFailure(call : Call<GetInventoryResponse> , t : Throwable) {
                LogHelper.logError(t.message)

                callback(null , t.message)
            }
        })
    }
}
