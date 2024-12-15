package com.example.myapplication.data.repositories.user

import com.example.myapplication.data.models.user.PutProfilePictureRequest
import com.example.myapplication.data.models.user.PutProfilePictureResponse
import com.example.myapplication.data.services.UserService
import com.example.myapplication.utils.ErrorParser
import com.example.myapplication.utils.LogHelper
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PutProfilePictureRepository(private val userService: UserService) {

    fun putProfilePicture(
        token: String,
        picture: MultipartBody.Part,
        callback: (PutProfilePictureResponse?, String?) -> Unit
    ) {
        val putProfilePictureRequest = PutProfilePictureRequest(picture)
        userService.putProfilePicture(token, picture)
            .enqueue(object : Callback<PutProfilePictureResponse> {
                override fun onResponse(
                    call: Call<PutProfilePictureResponse>,
                    response: Response<PutProfilePictureResponse>
                ) {
                    LogHelper.logRequestDetails(
                        url = "${call.request().url}",
                        headers = "${call.request().headers}",
                        body = "File image uploaded (not logged for security)"
                    )
                    LogHelper.logResponseDetails(response.code(), response.body()?.toString())

                    if (response.isSuccessful) {
                        callback(response.body(), null)
                    } else {
                        val errorBody = ErrorParser.parseErrorMessage(response.errorBody())
                        LogHelper.logError("$errorBody")
                        callback(null, errorBody)
                    }
                }

                override fun onFailure(call: Call<PutProfilePictureResponse>, t: Throwable) {
                    LogHelper.logError(t.message)
                    callback(null, t.message)
                }
            })
    }
}
