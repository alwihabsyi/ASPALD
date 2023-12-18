package com.aspald.aspald.utils

import com.aspald.aspald.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object Constants {
    const val USER_COLLECTION = "user"
    const val BASE_URL = "" // TODO: Fill Base Url
    const val SESSION = "session"
    const val TOKEN = "token"

    private val loggingInterceptor = if (BuildConfig.DEBUG){
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }else{
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }
    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}