package com.example.myapplication.data.repositories.user

import com.example.myapplication.data.models.user.PatchProfileRequest
import com.example.myapplication.data.models.user.PatchProfileResponse
import com.example.myapplication.data.services.UserService
import com.example.myapplication.utils.ErrorParser
import com.example.myapplication.utils.LogHelper
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PatchProfileRepository(private val userService : UserService) {

    fun patchProfile(
        token : String ,
        username : String?=null ,
        email : String?=null ,
        phoneNumber : String?=null ,
        callback : (PatchProfileResponse? , String?) -> Unit
    ) {
        val patchProfileRequest=PatchProfileRequest(
            username=if (username.isNullOrEmpty()) null else username ,
            email=if (email.isNullOrEmpty()) null else email ,
            phoneNumber=if (phoneNumber.isNullOrEmpty()) null else phoneNumber
        )
        userService.patchProfile(token , patchProfileRequest)
            .enqueue(object : Callback<PatchProfileResponse> {
                override fun onResponse(
                    call : Call<PatchProfileResponse> ,
                    response : Response<PatchProfileResponse>
                ) {
                    LogHelper.logRequestDetails(
                        url="${call.request().url}" ,
                        headers="${call.request().headers}" ,
                        body=Gson().toJson(patchProfileRequest)
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
                override fun onFailure(call : Call<PatchProfileResponse> , t : Throwable) {
                    LogHelper.logError(t.message)
                    callback(null , t.message)
                }
            })
    }

}
