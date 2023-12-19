package com.aspald.aspald.data.remote

import com.aspald.aspald.data.model.GetReportResponse
import com.aspald.aspald.data.model.SignInRequest
import com.aspald.aspald.data.model.SignInResponse
import com.aspald.aspald.data.model.SignUpRequest
import com.aspald.aspald.data.model.PostResponse
import com.aspald.aspald.data.model.ReportDetailResponse
import com.aspald.aspald.data.model.UserRequest
import com.aspald.aspald.data.model.UpdateUserResponse
import com.aspald.aspald.data.model.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {

    @GET("api/v1/reports")
    suspend fun getReport(): Response<GetReportResponse>

    @GET("api/v1/users/{id}")
    suspend fun getUser(
        @Path("id") id: String
    ): Response<UserResponse>

    @GET("api/v1/reports/{id}")
    suspend fun getReportById(
        @Path("id") userId: String
    ): Response<ReportDetailResponse>

    @POST("api/v1/auth/login")
    suspend fun signIn(@Body request: SignInRequest) : Response<SignInResponse>

    @POST("api/v1/auth/register")
    suspend fun signUp(@Body request: SignUpRequest) : Response<String>

    @Multipart
    @POST("api/v1/reports")
    suspend fun postReport(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody,
        @Part("lon") lon: RequestBody
    ): Response<PostResponse>

    @PUT("api/v1/users/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Body request: UserRequest
    ): Response<UpdateUserResponse>
}