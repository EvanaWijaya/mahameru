package com.example.myapplication.data.models.user

import com.google.gson.annotations.SerializedName

data class GetProfileResponse(
    val data: GetProfileData,
)

data class GetProfileData(
    val email: String ,
    val username: String ,
    @SerializedName("phone_number") val phoneNumber: String? ,
    @SerializedName("picture") val picture: String?
)