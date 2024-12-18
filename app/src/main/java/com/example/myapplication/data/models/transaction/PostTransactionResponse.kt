package com.example.myapplication.data.models.transaction

import com.google.gson.annotations.SerializedName

data class PostTransactionResponse(
    @SerializedName("data") val data: PostTransactionData
)

data class PostTransactionData(
    val status: String,
    @SerializedName("transaction_number") val transactionNumber: String,
    val kategori: String,
    @SerializedName("transaction_date") val transactionDate: String,
    @SerializedName("payment_method") val paymentMethod: String,
    @SerializedName("total_price") val totalPrice: Int,
    val username: String,
    @SerializedName("visitor_amount") val visitorAmount: Int,
    val date: String,
    @SerializedName("transaction_code") val transactionCode: String,
    val visitors: List<VisitorTransaction>
)

data class VisitorTransaction(
    val id: Int,
    val name: String,
    val nik: String
)
