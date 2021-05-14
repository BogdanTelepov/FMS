package com.neobis.fms.model.transaction


import com.google.gson.annotations.SerializedName

data class TransactionItem(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("category")
    val category: String,
    @SerializedName("createdDate")
    val createdDate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("transactionType")
    val transactionType: String
)