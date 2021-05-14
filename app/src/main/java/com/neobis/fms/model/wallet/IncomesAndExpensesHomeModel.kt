package com.neobis.fms.model.wallet


import com.google.gson.annotations.SerializedName

data class IncomesAndExpensesHomeModel(
    @SerializedName("expense")
    val expense: Double,
    @SerializedName("income")
    val income: Double
)