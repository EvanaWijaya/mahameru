package com.example.myapplication.data.models.user

import com.google.gson.annotations.SerializedName

data class PostUpdatePasswordRequest(
    @SerializedName("new_password") val newPassword : String? = null ,
    @SerializedName("confirm_password") val confirmPassword : String? = null ,
    @SerializedName("old_password") val oldPassword : String? = null ,
)
