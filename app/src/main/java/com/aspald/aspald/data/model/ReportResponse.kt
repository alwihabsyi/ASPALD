package com.aspald.aspald.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class GetReportResponse(
    @field:SerializedName("error")
    val error: Boolean,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("listReport")
    val listRepost: List<Report>
)

data class Report(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("photoUrl")
    val photoUrl: String,
    @field:SerializedName("createdAt")
    val createdAt: Date,
    @field:SerializedName("lat")
    val lat: Float,
    @field:SerializedName("lon")
    val lon: Float
)

data class ReportRequest(
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("tipe_kerusakan")
    val tipeKerusakan: String,
    @field:SerializedName("photoUrl")
    val photoUrl: String,
    @field:SerializedName("lat")
    val lat: Float,
    @field:SerializedName("lon")
    val lon: Float
)