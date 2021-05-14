package com.neobis.fms.model.wallet


import com.google.gson.annotations.SerializedName

data class TransactionModel(
    @SerializedName("accountantName")
    val accountantName: String,
    @SerializedName("accountantSurname")
    val accountantSurname: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("categoryName")
    val categoryName: String,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("counterpartyName")
    val counterpartyName: String,
    @SerializedName("counterpartySurname")
    val counterpartySurname: Any,
    @SerializedName("createdDate")
    val createdDate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("neoSection")
    val neoSection: String,
    @SerializedName("transactionType")
    val transactionType: String,
    @SerializedName("transferWalletId")
    val transferWalletId: Any,
    @SerializedName("transferWalletName")
    val transferWalletName: Any,
    @SerializedName("walletId")
    val walletId: Int,
    @SerializedName("walletName")
    val walletName: String
)