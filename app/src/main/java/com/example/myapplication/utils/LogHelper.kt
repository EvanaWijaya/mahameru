package com.example.myapplication.utils

import android.util.Log

object LogHelper {
    private const val TAG = "AppLog"

    fun logRequestDetails(url: String, headers: String, body: String) {
        Log.d(TAG, "Request URL: $url")
        Log.d(TAG, "Request Headers: $headers")
        Log.d(TAG, "Request Body: $body")
    }

    fun logResponseDetails(code: Int, body: String?) {
        Log.d(TAG, "HTTP Code: $code")
        Log.d(TAG, "Response Body: $body")
    }

    fun logError(message: String?) {
        Log.e(TAG, "Error: $message")
    }

    fun logErrorParsing(message: String?) {
        Log.e(TAG, "Error Parsing Error Body: $message")
    }
}
