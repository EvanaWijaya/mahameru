package com.example.myapplication.data.services

import com.example.myapplication.data.models.user.GetProfileResponse
import com.example.myapplication.data.models.user.PatchProfileRequest
import com.example.myapplication.data.models.user.PatchProfileResponse
import com.example.myapplication.data.models.user.PostUpdatePasswordRequest
import com.example.myapplication.data.models.user.PostUpdatePasswordResponse
import com.example.myapplication.data.models.user.PutProfilePictureResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface UserService {
    @GET("user")
    fun getProfile(@Header("Authorization") token: String): Call<GetProfileResponse>

    @PATCH("user")
    fun patchProfile(
        @Header("Authorization") token: String,
        @Body request: PatchProfileRequest
    ): Call<PatchProfileResponse>

    @Multipart
    @PUT("user/picture")
    fun putProfilePicture(
        @Header("Authorization") token: String,
        @Part picture: MultipartBody.Part
    ): Call<PutProfilePictureResponse>

    @POST("new_password")
    fun updatePassword(
        @Header("Authorization") token: String,
        @Body request: PostUpdatePasswordRequest
    ): Call<PostUpdatePasswordResponse>
}
