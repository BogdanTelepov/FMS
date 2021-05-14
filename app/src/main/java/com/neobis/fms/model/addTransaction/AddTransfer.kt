package com.neobis.fms.model.addTransaction

data class AddTransfer(
    val amount: Int,
    val comment: String,
    val walletFromId: Int,
    val walletToId: Int
)