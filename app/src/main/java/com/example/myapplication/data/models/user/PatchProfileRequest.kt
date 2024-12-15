package com.example.myapplication.data.models.user

import com.google.gson.annotations.SerializedName

data class PatchProfileRequest(
    val email : String? = null ,
    val username : String? = null ,
    @SerializedName("phone_number") val phoneNumber : String? = null ,
)
