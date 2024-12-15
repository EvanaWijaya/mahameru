package com.example.myapplication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repositories.user.GetProfileRepository
import com.example.myapplication.data.repositories.user.PatchProfileRepository
import com.example.myapplication.data.repositories.user.PostUpdatePasswordRepository
import com.example.myapplication.data.repositories.user.PutProfilePictureRepository

class UserViewModelFactory(
    private val getProfileRepository : GetProfileRepository ,
    private val patchProfileRepository : PatchProfileRepository ,
    private val putProfilePictureRepository : PutProfilePictureRepository ,
    private val postUpdatePasswordRepository : PostUpdatePasswordRepository ,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(getProfileRepository, patchProfileRepository, putProfilePictureRepository, postUpdatePasswordRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
