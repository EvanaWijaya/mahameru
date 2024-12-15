package com.example.myapplication.utils


import com.google.gson.Gson
import com.google.gson.JsonElement
import okhttp3.ResponseBody

object ErrorParser {
    fun parseErrorMessage(responseBody: ResponseBody?): String? {
        return try {
            val errorElement = Gson().fromJson(responseBody?.string(), JsonElement::class.java)

            when {
                errorElement.isJsonObject -> {
                    val errorJson = errorElement.asJsonObject
                    val errorMessage = errorJson["error"]?.asString ?: "Unknown error"
                    errorMessage
                }
                errorElement.isJsonPrimitive -> errorElement.asString
                else -> "Unknown error"
            }

        } catch (e: Exception) {
            LogHelper.logErrorParsing(e.message)
            "Failed to parse error message"
        }
    }
}
