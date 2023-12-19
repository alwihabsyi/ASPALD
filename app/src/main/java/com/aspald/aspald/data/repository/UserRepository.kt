package com.aspald.aspald.data.repository

import com.aspald.aspald.data.model.PostResponse
import com.aspald.aspald.data.model.UserRequest
import com.aspald.aspald.data.remote.ApiServices
import com.aspald.aspald.utils.UiState
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException

class UserRepository(
    private val apiServices: ApiServices
) {
    private val _userState = MutableStateFlow<UiState<String>>(UiState.Loading())

    suspend fun updateUser(userId: String, request: UserRequest): Flow<UiState<String>> {
        try {
            val client = apiServices.updateUser(userId, request)
            if (client.isSuccessful) {
                client.body()?.message?.let {
                    _userState.value = UiState.Success(it)
                }
            }else {
                throw HttpException(client)
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, PostResponse::class.java)
            val errorMessage = errorBody.message
            _userState.value = UiState.Error(errorMessage)
        }

        return _userState.asStateFlow()
    }
}