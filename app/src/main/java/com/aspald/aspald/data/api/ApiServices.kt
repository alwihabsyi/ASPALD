package com.aspald.aspald.data.api

import com.aspald.aspald.data.model.GetReportResponse
import com.aspald.aspald.data.model.SignInRequest
import com.aspald.aspald.data.model.SignInResponse
import com.aspald.aspald.data.model.SignUpRequest
import com.aspald.aspald.data.model.PostResponse
import com.aspald.aspald.data.model.ReportRequest
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {

    @GET("getReport")
    fun getReport(): Response<GetReportResponse>

    @GET("getReportById")
    fun getReportById(userId: String): Response<GetReportResponse>

    @POST("login")
    suspend fun signIn(@Body request: SignInRequest) : Response<SignInResponse>

    @POST("register")
    fun signUp(@Body request: SignUpRequest) : Response<PostResponse>

    @POST("postReport")
    fun postReport(@Body request: ReportRequest): Response<PostResponse>
}