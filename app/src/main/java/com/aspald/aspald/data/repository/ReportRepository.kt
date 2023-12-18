package com.aspald.aspald.data.repository

import com.aspald.aspald.data.api.ApiServices
import com.aspald.aspald.data.model.PostResponse
import com.aspald.aspald.data.model.Report
import com.aspald.aspald.data.model.SignInRequest
import com.aspald.aspald.data.model.SignUpRequest
import com.aspald.aspald.utils.UiState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException

class ReportRepository(
    private val apiServices: ApiServices
) {
    private val _reportStateFlow = MutableStateFlow<UiState<List<Report>>>(UiState.Loading())
    private val reportState = _reportStateFlow.asStateFlow()
    private val _authFlow = MutableStateFlow<UiState<String>>(UiState.Loading())
    private val authState = _authFlow.asStateFlow()

    suspend fun getAllReport(): StateFlow<UiState<List<Report>>> {
        try {
            val client = apiServices.getReport()
            if (client.isSuccessful) {
                val listReport = client.body()?.listReport
                listReport?.let {
                    _reportStateFlow.value = UiState.Success(it)
                }
            } else {
                throw HttpException(client)
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, PostResponse::class.java)
            val errorMessage = errorBody.message
            _reportStateFlow.value = UiState.Error(errorMessage)
        }
        return reportState
    }

    suspend fun getReportById(id: String): StateFlow<UiState<List<Report>>> {
        try {
            val client = apiServices.getReportById(id)
            if (client.isSuccessful) {
                val listReport = client.body()?.listReport
                listReport?.let {
                    _reportStateFlow.value = UiState.Success(it)
                }
            } else {
                throw HttpException(client)
            }
        } catch(e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, PostResponse::class.java)
            val errorMessage = errorBody.message
            _reportStateFlow.value = UiState.Error(errorMessage)
        }
        return reportState
    }

    suspend fun signUp(username: String, email: String, password: String): StateFlow<UiState<String>> {
        try {
            val client = apiServices.signUp(
                SignUpRequest(
                    username,
                    email,
                    password
                )
            )
            if (client.isSuccessful) {
                val message = client.body()?.message
                message?.let { _authFlow.value = UiState.Success(it) }
            } else {
                throw HttpException(client)
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, PostResponse::class.java)
            val errorMessage = errorBody.message
            _authFlow.value = UiState.Error(errorMessage)
        }

        return authState
    }

    suspend fun signIn(email: String, password: String): StateFlow<UiState<String>> {
        try {
            val client = apiServices.signIn(
                SignInRequest(
                    email,
                    password
                )
            )
            if (client.isSuccessful) {
                val message = client.body()?.message
                message?.let { _authFlow.value = UiState.Success(it) }
            } else {
                throw HttpException(client)
            }
        } catch(e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, PostResponse::class.java)
            val errorMessage = errorBody.message
            _authFlow.value = UiState.Error(errorMessage)
        }

        return authState
    }
}