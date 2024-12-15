package com.example.myapplication.data.repositories.user

import com.example.myapplication.data.models.user.GetProfileResponse
import com.example.myapplication.data.services.UserService
import com.example.myapplication.utils.ErrorParser
import com.example.myapplication.utils.LogHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetProfileRepository(private val userService : UserService) {
    fun getProfile(token : String , callback : (GetProfileResponse? , String?) -> Unit) {
        userService.getProfile(token).enqueue(object : Callback<GetProfileResponse> {
            override fun onResponse(
                call : Call<GetProfileResponse> ,
                response : Response<GetProfileResponse>
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

            override fun onFailure(call : Call<GetProfileResponse> , t : Throwable) {
                LogHelper.logError(t.message)

                callback(null , t.message)
            }
        })
    }

}
