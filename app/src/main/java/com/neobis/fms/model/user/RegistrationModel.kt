package com.neobis.fms.model.user


import com.google.gson.annotations.SerializedName

data class RegistrationModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("group_ids")
    val groupIds: List<Int>,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("surname")
    val surname: String
)