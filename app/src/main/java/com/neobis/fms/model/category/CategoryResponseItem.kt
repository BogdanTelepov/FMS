package com.neobis.fms.model.category


import com.google.gson.annotations.SerializedName

data class CategoryResponseItem(
    val categoryStatus: String,
    val id: Int,
    val name: String,
    val neoSection: String,
    val transactionType: String
)