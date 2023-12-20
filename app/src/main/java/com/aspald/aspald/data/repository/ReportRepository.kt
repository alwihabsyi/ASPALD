package com.aspald.aspald.data.repository

import android.util.Log
import com.aspald.aspald.data.model.PostResponse
import com.aspald.aspald.data.model.Report
import com.aspald.aspald.data.remote.ApiServices
import com.aspald.aspald.utils.UiState
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class ReportRepository(
    private val apiServices: ApiServices
) {
    private val _reportStateFlow = MutableStateFlow<UiState<List<Report>>>(UiState.Loading())
    private val reportState = _reportStateFlow.asStateFlow()

    private val _userReport = MutableStateFlow<UiState<List<Report>>>(UiState.Loading())

    private val _detailReportStateFlow = MutableStateFlow<UiState<Report>>(UiState.Loading())

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

    suspend fun getReportById(id: String): Flow<UiState<Report>> {
        try {
            val client = apiServices.getReportById(id)
            if (client.isSuccessful) {
                val listReport = client.body()?.report
                listReport?.let {
                    _detailReportStateFlow.value = UiState.Success(it)
                }
            } else {
                throw HttpException(client)
            }
        } catch(e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, PostResponse::class.java)
            val errorMessage = errorBody.message
            _detailReportStateFlow.value = UiState.Error(errorMessage)
        }
        return _detailReportStateFlow.asStateFlow()
    }

    suspend fun postReport(file: File, description: String, lat: Double, lon: Double): Flow<UiState<String>> {
        val requestImageFile = file.asRequestBody("image/*".toMediaType())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "image",
            file.name,
            requestImageFile
        )
        val descriptionCast = description.toRequestBody("text/plain;charset=utf-8".toMediaType())
        val latitudeCast = lat.toString().toRequestBody("text/plain".toMediaType())
        val longitudeCast = lon.toString().toRequestBody("text/plain".toMediaType())

        try {
            val client = apiServices.postReport(
                imageMultipart,
                descriptionCast,
                latitudeCast,
                longitudeCast
            )
            if (client.isSuccessful) {
                _postReportState.value = UiState.Success("Reported successfully")
            }else {
                throw HttpException(client)
            }
        }catch (e: HttpException) {
            Log.e("REPORT ERROR: ", e.localizedMessage ?: e.message())
            _postReportState.value = UiState.Error(e.localizedMessage ?: e.message())
        }
        return postReportState
    }

    suspend fun getUserReports(userId: String): Flow<UiState<List<Report>>> {
        try {
            val client = apiServices.getUserReport(userId)
            if (client.isSuccessful) {
                val listReport = client.body()?.listReport
                listReport?.let {
                    _userReport.value = UiState.Success(it)
                }
            } else {
                throw HttpException(client)
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, PostResponse::class.java)
            val errorMessage = errorBody.message
            _userReport.value = UiState.Error(errorMessage)
        }
        return _userReport.asStateFlow()
    }
}