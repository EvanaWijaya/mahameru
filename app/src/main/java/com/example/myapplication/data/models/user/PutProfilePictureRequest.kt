package com.example.myapplication.data.models.user

import okhttp3.MultipartBody

data class PutProfilePictureRequest(
    val picture: MultipartBody.Part
)
