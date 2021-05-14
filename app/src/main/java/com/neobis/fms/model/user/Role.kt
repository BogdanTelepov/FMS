package com.neobis.fms.model.user


import com.google.gson.annotations.SerializedName

data class Role(
    @SerializedName("id")
    val id: Int,
    @SerializedName("permissions")
    val permissions: List<String>,
    @SerializedName("role")
    val role: String
)