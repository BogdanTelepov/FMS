package com.neobis.fms.model.user

import com.google.gson.annotations.SerializedName

data class UpdateUserBody(

        @SerializedName("name")
        var name: String?,
        @SerializedName("phoneNumber")
        var phoneNumber: String?,
        @SerializedName("surname")
        var surname: String?,
        @SerializedName("newPassword")
        var newPassword: String?,
        @SerializedName("oldPassword")
        var oldPassword: String?

)
