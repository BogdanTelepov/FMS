package com.neobis.fms.model.user


import com.google.gson.annotations.SerializedName

data class UpdateUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("groupIds")
    val groupIds: List<Int>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("userStatus")
    val userStatus: String
)