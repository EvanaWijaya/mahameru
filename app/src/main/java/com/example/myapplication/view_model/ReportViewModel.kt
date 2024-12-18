package com.example.myapplication.view_model

import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.report.PostReportResponse
import com.example.myapplication.data.repositories.report.PostReportRepository
import com.example.myapplication.utils.SharedPrefs
import com.example.myapplication.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReportViewModel(
    private val postReportRepository : PostReportRepository
) : ViewModel() {

    var name=mutableStateOf("")
    var email=mutableStateOf("")
    var about=mutableStateOf("")
    var message=mutableStateOf("")

    private var isLoading=mutableStateOf(false)
    private var postReportResponse=mutableStateOf<PostReportResponse?>(null)
    private var errorMessage=mutableStateOf<String?>(null)

    fun isButtonValid() : Boolean {
        return name.value.isNotEmpty() && email.value.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(
            email.value
        ).matches() &&
                about.value.isNotEmpty() && message.value.isNotEmpty()
    }


    var isSuccess = mutableStateOf(false)

    fun createReport(context: Context) {
        isLoading.value = true
        val token = SharedPrefs.getToken(context)

        token?.let {
            viewModelScope.launch(Dispatchers.IO) {
                postReportRepository.updatePassword(
                    token = it,
                    name = name.value,
                    email = email.value,
                    about = about.value,
                    message = message.value,
                    callback = { response, error ->
                        isLoading.value = false
                        response?.let {
                            postReportResponse.value = it
                            isSuccess.value = true
                        }
                        error?.let {
                            errorMessage.value = it
                            showToast(context, it)
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

}
