package com.example.myapplication.data.models.user

data class PatchProfileResponse(
    val data : PatchProfileData,
)

data class PatchProfileData(
    val user : GetProfileData,
    val token : String
)