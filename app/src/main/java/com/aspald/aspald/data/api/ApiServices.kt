package com.aspald.aspald.data.api

import com.aspald.aspald.data.model.SignInRequest
import com.aspald.aspald.data.model.SignInResponse
import com.aspald.aspald.data.model.SignUpRequest
import com.aspald.aspald.data.model.SignUpResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {
    @POST("login")
    suspend fun signIn(@Body request: SignInRequest) : Response<SignInResponse>

    @POST("register")
    fun signUp(@Body request: SignUpRequest) : Response<SignUpResponse>
}