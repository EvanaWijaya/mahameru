package com.example.myapplication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repositories.report.PostReportRepository

class ReportViewModelFactory(
    private val postReportRepository : PostReportRepository ,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(ReportViewModel::class.java)) {
            return ReportViewModel(postReportRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
