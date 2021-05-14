package com.neobis.fms.model.wallet


import com.google.gson.annotations.SerializedName

data class WalletItem(
    val availableBalance: Double,
    val id: Int,
    val name: String,
    val status: String
)