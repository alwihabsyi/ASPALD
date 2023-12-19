package com.aspald.aspald.data.repository

import com.aspald.aspald.data.model.PostResponse
import com.aspald.aspald.data.model.Report
import com.aspald.aspald.data.model.ReportRequest
import com.aspald.aspald.data.remote.ApiServices
import com.aspald.aspald.utils.UiState
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException

class ReportRepository(
    private val apiServices: ApiServices
) {
    private val _reportStateFlow = MutableStateFlow<UiState<List<Report>>>(UiState.Loading())
    private val reportState = _reportStateFlow.asStateFlow()

    private val _postReportState = MutableStateFlow<UiState<String>>(UiState.Loading())
    private val postReportState = _postReportState.asStateFlow()

    suspend fun getAllReport(): Flow<UiState<List<Report>>> {
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

    suspend fun getReportById(id: String): Flow<UiState<List<Report>>> {
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

    suspend fun postReport(reportRequest: ReportRequest): Flow<UiState<String>> {
        try {
            val client = apiServices.postReport(reportRequest)
            if (client.isSuccessful) {
                _postReportState.value = UiState.Success("Reported successfully")
            }else {
                throw HttpException(client)
            }
        }catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, PostResponse::class.java)
            val errorMessage = errorBody.message
            _postReportState.value = UiState.Error(errorMessage)
        }
        return postReportState
    }
}