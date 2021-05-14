package com.neobis.fms.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("jwt")
    val token: String,
)
