package com.example.myapplication.view_model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.visitor.GetVisitorResponse
import com.example.myapplication.data.models.visitor.VisitorResponse
import com.example.myapplication.data.repositories.auth.GetVisitorRepository
import com.example.myapplication.data.repositories.auth.PostVisitorRepository
import com.example.myapplication.data.repositories.auth.PutVisitorRepository
import com.example.myapplication.utils.SharedPrefs
import com.example.myapplication.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VisitorViewModel(
    private val postVisitorRepository : PostVisitorRepository ,
    private val putVisitorRepository : PutVisitorRepository ,
    private val getVisitorRepository : GetVisitorRepository ,
) : ViewModel() {


    var name=mutableStateOf("")
    var nik=mutableStateOf("")
    var isLoading=mutableStateOf(false)
    var visitorResponse=mutableStateOf<VisitorResponse?>(null)
    var getVisitorResponse=mutableStateOf<GetVisitorResponse?>(null)
    var errorMessage=mutableStateOf<String?>(null)

    var email=mutableStateOf("")

    val isFormValid=mutableStateOf(false)

    init {
        validateForm()
    }

    var isSuccess=mutableStateOf(false)


    fun validateForm() {
        isFormValid.value=name.value.isNotBlank() &&
                nik.value.isNotBlank()
    }

    fun postVisitor(context : Context) {
        isLoading.value=true
        val token=SharedPrefs.getToken(context)

        token?.let {
            viewModelScope.launch(Dispatchers.IO) {
                postVisitorRepository.postVisitor(
                    token=it ,
                    name=name.value ,
                    nik=nik.value ,

                    callback={ response , error ->
                        isLoading.value=false
                        response?.let {
                            visitorResponse.value=it
                            isSuccess.value=true
                            showToast(context , "Berhasil menambahkan data visitor")
                            name.value=""
                            nik.value=""

                        }
                        error?.let {
                            errorMessage.value=it
                            showToast(context , it)
                        }
                    }
                )
            }
        } ?: run {
            isLoading.value=false
            errorMessage.value="No token found"
            showToast(context , "Token tidak ditemukan")
        }
    }

    fun getVisitor(context : Context) {
        isLoading.value=true
        val token=SharedPrefs.getToken(context)

        token?.let {
            viewModelScope.launch(Dispatchers.IO) {
                getVisitorRepository.getVisitor(it) { response , error ->
                    isLoading.value=false
                    if (response != null) {
                        getVisitorResponse.value=response
                        errorMessage.value=null
                        Log.d("VisitorViewModel" , "getVisitor: ${response.data}")
                    } else {
                        errorMessage.value=error
                        showToast(context , error ?: "Gagal mendapatkan data visitor")
                    }
                }
            }
        } ?: run {
            isLoading.value=false
            errorMessage.value="No token found"
            showToast(context , "Token tidak ditemukan")
        }
    }

    fun putVisitor(context : Context , ticketId : String) {
        isLoading.value=true
        val token=SharedPrefs.getToken(context)

        token?.let {
            viewModelScope.launch(Dispatchers.IO) {
                putVisitorRepository.putVisitor(
                    token=it ,
                    name=name.value ,
                    nik=nik.value ,
                    ticketId=ticketId ,
                    callback={ response , error ->
                        isLoading.value=false
                        response?.let {
                            visitorResponse.value=it
                            isSuccess.value=true
                        }
                        error?.let {
                            errorMessage.value=it
                            showToast(context , it)
                        }
                    }
                )
            }
        } ?: run {
            isLoading.value=false
            errorMessage.value="No token found"
            showToast(context , "Token tidak ditemukan")
        }
    }


}