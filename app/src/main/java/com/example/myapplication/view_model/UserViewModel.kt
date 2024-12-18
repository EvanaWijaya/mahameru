package com.example.myapplication.view_model

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.user.GetProfileResponse
import com.example.myapplication.data.models.user.PatchProfileResponse
import com.example.myapplication.data.models.user.PostUpdatePasswordResponse
import com.example.myapplication.data.models.user.PutProfilePictureResponse
import com.example.myapplication.data.repositories.user.GetProfileRepository
import com.example.myapplication.data.repositories.user.PatchProfileRepository
import com.example.myapplication.data.repositories.user.PostUpdatePasswordRepository
import com.example.myapplication.data.repositories.user.PutProfilePictureRepository
import com.example.myapplication.utils.SharedPrefs
import com.example.myapplication.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UserViewModel(
    private val getProfileRepository: GetProfileRepository,
    private val patchProfileRepository: PatchProfileRepository,
    private val putProfilePictureRepository: PutProfilePictureRepository,
    private val postUpdatePasswordRepository : PostUpdatePasswordRepository
) : ViewModel() {

    var username = mutableStateOf("")
    var email = mutableStateOf("")
    var phoneNumber = mutableStateOf("")
    var pictureUrl = mutableStateOf("")

    var oldPassword = mutableStateOf("")
    var newPassword = mutableStateOf("")
    var confirmPassword = mutableStateOf("")

    private var initialUsername = ""
    private var initialEmail = ""
    private var initialPhoneNumber = ""
    private var initialPictureUrl = ""

    private var isLoading = mutableStateOf(false)
    private var getProfileResponse = mutableStateOf<GetProfileResponse?>(null)
    private var patchProfileResponse = mutableStateOf<PatchProfileResponse?>(null)
    private var putProfilePictureResponse = mutableStateOf<PutProfilePictureResponse?>(null)
    private var postUpdatePasswordResponse = mutableStateOf<PostUpdatePasswordResponse?>(null)
    private var errorMessage = mutableStateOf<String?>(null)

    fun isButtonUpdatePasswordValid(): Boolean {
        return oldPassword.value.isNotEmpty() && newPassword.value.isNotEmpty() && confirmPassword.value.isNotEmpty() && newPassword.value == confirmPassword.value
    }

    fun createMultipartFromUri(context: Context, uri: Uri): MultipartBody.Part {
        val file = File(getRealPathFromURI(context, uri))
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("picture", file.name, requestFile)
    }

    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        val proj = arrayOf(android.provider.MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(contentUri, proj, null, null, null)
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA)
        val filePath = cursor?.getString(columnIndex ?: 0)
        cursor?.close()
        return filePath ?: ""
    }

    fun getProfile(context: Context) {
        isLoading.value = true
        val token = SharedPrefs.getToken(context)

        token?.let {
            viewModelScope.launch(Dispatchers.IO) {
                getProfileRepository.getProfile(it) { response, error ->
                    isLoading.value = false
                    if (response != null) {
                        getProfileResponse.value = response
                        errorMessage.value = null
                        username.value = response.data.username
                        email.value = response.data.email
                        phoneNumber.value = response.data.phoneNumber ?: ""
                        pictureUrl.value = response.data.picture ?: ""

                        initialUsername = response.data.username
                        initialEmail = response.data.email
                        initialPhoneNumber = response.data.phoneNumber ?: ""
                        initialPictureUrl = response.data.picture ?: ""
                    } else {
                        errorMessage.value = error
                        showToast(context, error ?: "Gagal mendapatkan data profile")
                    }
                }
            }
        } ?: run {
            isLoading.value = false
            errorMessage.value = "No token found"
            showToast(context, "Token tidak ditemukan")
        }
    }

    fun patchProfile(context: Context) {
        isLoading.value = true
        val token = SharedPrefs.getToken(context)

        token?.let {
            val updatedUsername = if (username.value != initialUsername) username.value else null
            val updatedEmail = if (email.value != initialEmail) email.value else null
            val updatedPhoneNumber = if (phoneNumber.value != initialPhoneNumber) phoneNumber.value else null

            if (updatedUsername == null && updatedEmail == null && updatedPhoneNumber == null) {
                isLoading.value = false
                showToast(context, "Tidak ada perubahan yang perlu disimpan")
                return
            }

            viewModelScope.launch(Dispatchers.IO) {
                patchProfileRepository.patchProfile(
                    token = it,
                    username = updatedUsername,
                    email = updatedEmail,
                    phoneNumber = updatedPhoneNumber,
                    callback = { response, error ->
                        isLoading.value = false
                        if (response != null) {
                            patchProfileResponse.value = response
                            response.data.token.let { newToken ->
                                SharedPrefs.saveToken(context, newToken)
                            }
                            errorMessage.value = null
                            username.value = response.data.user.username
                            email.value = response.data.user.email
                            phoneNumber.value = response.data.user.phoneNumber ?: ""

                            initialUsername = username.value
                            initialEmail = email.value
                            initialPhoneNumber = phoneNumber.value
                            initialPictureUrl = pictureUrl.value

                            showToast(context, "Profil berhasil diperbarui")
                        } else {
                            errorMessage.value = error
                            showToast(context, error ?: "Gagal memperbarui profil")
                        }
                    }
                )
            }
        } ?: run {
            isLoading.value = false
            errorMessage.value = "No token found"
            showToast(context, "Token tidak ditemukan")
        }
    }

    fun putProfilePicture(context: Context, picture: MultipartBody.Part) {
        isLoading.value = true
        val token = SharedPrefs.getToken(context)

        token?.let {
            viewModelScope.launch(Dispatchers.IO) {
                putProfilePictureRepository.putProfilePicture(
                    token = it,
                    picture = picture,
                    callback = { response, error ->
                        isLoading.value = false
                        if (response != null) {
                            putProfilePictureResponse.value = response
                            errorMessage.value = null
                            getProfile(context)
                            showToast(context, response.data.message)
                        } else {
                            errorMessage.value = error
                            showToast(context, error ?: "Gagal memperbarui foto profil")
                        }
                    }
                )
            }
        } ?: run {
            isLoading.value = false
            errorMessage.value = "No token found"
            showToast(context, "Token tidak ditemukan")
        }
    }

    fun postUpdatePassword(context: Context) {
        isLoading.value = true
        val token = SharedPrefs.getToken(context)

        token?.let {
            viewModelScope.launch(Dispatchers.IO) {
                postUpdatePasswordRepository.updatePassword(
                    token = it,
                    newPassword = newPassword.value,
                    confirmPassword = confirmPassword.value,
                    oldPassword = oldPassword.value,
                    callback = { response, error ->
                        isLoading.value = false
                        if (response != null) {
                            postUpdatePasswordResponse.value = response
                            errorMessage.value = null
                            showToast(context, response.data.message)
                        } else {
                            errorMessage.value = error
                            showToast(context, error ?: "Gagal memperbarui password")
                        }
                    }
                )
            }
        } ?: run {
            isLoading.value = false
            errorMessage.value = "No token found"
            showToast(context, "Token tidak ditemukan")
        }
    }


    fun logout (context : Context) {
        SharedPrefs.clearToken(context)
    }
}
