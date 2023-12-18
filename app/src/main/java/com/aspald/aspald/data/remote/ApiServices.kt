package com.aspald.aspald.data.remote

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
    suspend fun getReport(): Response<GetReportResponse>

    @GET("getReportById")
    suspend fun getReportById(userId: String): Response<GetReportResponse>

    @POST("api/v1/auth/login")
    suspend fun signIn(@Body request: SignInRequest) : Response<SignInResponse>

    @POST("api/v1/auth/register")
    suspend fun signUp(@Body request: SignUpRequest) : Response<String>

    @POST("postReport")
    fun postReport(@Body request: ReportRequest): Response<PostResponse>
}