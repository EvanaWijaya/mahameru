package com.example.myapplication.data.repositories.user

import com.example.myapplication.data.models.user.PostUpdatePasswordRequest
import com.example.myapplication.data.models.user.PostUpdatePasswordResponse
import com.example.myapplication.data.services.UserService
import com.example.myapplication.utils.ErrorParser
import com.example.myapplication.utils.LogHelper
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostUpdatePasswordRepository(private val userService : UserService) {

    fun updatePassword(
        token : String ,
        newPassword : String?=null ,
        confirmPassword : String?=null ,
        oldPassword : String?=null ,
        callback : (PostUpdatePasswordResponse? , String?) -> Unit
    ) {
        val postUpdatePasswordRequest=PostUpdatePasswordRequest(
            newPassword=if (newPassword.isNullOrEmpty()) null else newPassword ,
            confirmPassword=if (confirmPassword.isNullOrEmpty()) null else confirmPassword ,
            oldPassword=if (oldPassword.isNullOrEmpty()) null else oldPassword
        )
        userService.updatePassword(token , postUpdatePasswordRequest)
            .enqueue(object : Callback<PostUpdatePasswordResponse> {
                override fun onResponse(
                    call : Call<PostUpdatePasswordResponse> ,
                    response : Response<PostUpdatePasswordResponse>
                ) {
                    LogHelper.logRequestDetails(
                        url="${call.request().url}" ,
                        headers="${call.request().headers}" ,
                        body=Gson().toJson(postUpdatePasswordRequest)
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
                override fun onFailure(call : Call<PostUpdatePasswordResponse> , t : Throwable) {
                    LogHelper.logError(t.message)
                    callback(null , t.message)
                }
            })
    }

}
