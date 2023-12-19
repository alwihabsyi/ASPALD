package com.aspald.aspald.data.model

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import java.util.Date

data class GetReportResponse(
    @field:SerializedName("error")
    val error: Boolean,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("listReport")
    val listReport: List<Report>
)

data class ReportDetailResponse(
    @field:SerializedName("error")
    val error: Boolean,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("report")
    val report: Report
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
    @field:SerializedName("damageType")
    val damageType: String,
    @field:SerializedName("photoUrl")
    val photoUrl: String,
    @field:SerializedName("createdAt")
    val createdAt: Date,
    @field:SerializedName("lat")
    val lat: Double,
    @field:SerializedName("lon")
    val lon: Double
)