package com.aspald.aspald.data.model

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class SignUpRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,
)