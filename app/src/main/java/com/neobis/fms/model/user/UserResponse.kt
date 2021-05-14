package com.neobis.fms.model.user


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("groups")
    val groups: List<Group>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("role")
    val role: Role,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("userStatus")
    val userStatus: String
)