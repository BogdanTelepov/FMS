package com.neobis.fms.model.bankWallet


import com.google.gson.annotations.SerializedName

data class WalletResponseItem(
    @SerializedName("availableBalance")
    val availableBalance: Double?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String



)