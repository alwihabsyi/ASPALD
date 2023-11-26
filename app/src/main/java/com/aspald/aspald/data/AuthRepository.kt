package com.aspald.aspald.data

import com.aspald.aspald.data.api.ApiServices
import com.aspald.aspald.data.model.SignInRequest
import com.aspald.aspald.utils.UserPreferences
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiServices: ApiServices,
    private val userPreferences: UserPreferences
) {
    suspend fun signIn(email: String, password: String): Result<String> {
        return try {
            val response = apiServices.signIn(SignInRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                response.body()?.let {
                    userPreferences.saveUser(it.loginResult.token)
                    Result.success(it.loginResult.token)
                } ?: Result.failure(Exception("Error logging in: Empty body"))
            } else {
                Result.failure(Exception("Error logging in: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}