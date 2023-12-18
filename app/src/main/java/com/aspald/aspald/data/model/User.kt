package com.aspald.aspald.data.model

import java.util.Date

data class User(
    val name: String? = null,
    val email: String? = null,
    val address: String? = null,
    val dateOfBirth: Date? = null,
    val phone: Int? = null
)
