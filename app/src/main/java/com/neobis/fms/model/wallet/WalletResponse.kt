package com.neobis.fms.model.wallet


import com.google.gson.annotations.SerializedName

data class WalletResponse(
    @SerializedName("incomesAndExpensesHomeModel")
    val incomesAndExpensesHomeModel: IncomesAndExpensesHomeModel,
    @SerializedName("transactionModels")
    val transactionModels: List<TransactionModel>,
    @SerializedName("walletBalance")
    val walletItem: List<WalletItem>
)