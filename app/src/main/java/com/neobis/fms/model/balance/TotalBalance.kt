package com.neobis.fms.model.balance


import com.google.gson.annotations.SerializedName

data class TotalBalance(
    @SerializedName("totalBalance")
    val totalBalance: Double
)