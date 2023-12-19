package com.aspald.aspald.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class UserResponse(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val address: String? = null,
    val dob: String? = null,
    val phone: String? = null
)

data class UpdateUserResponse(
    @field:SerializedName("error")
    val error: String? = null,
    @field:SerializedName("message")
    val message: String? = null
)

data class UserRequest(
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("address")
    val address: String? = null,
    @field:SerializedName("dob")
    val dateOfBirth: Date? = null,
    @field:SerializedName("phone")
    val phone: String? = null
)