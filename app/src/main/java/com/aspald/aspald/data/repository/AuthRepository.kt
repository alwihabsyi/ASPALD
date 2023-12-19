package com.aspald.aspald.data.repository

import android.content.Context
import com.aspald.aspald.data.Preferences
import com.aspald.aspald.data.model.PostResponse
import com.aspald.aspald.data.model.SignInRequest
import com.aspald.aspald.data.model.SignUpRequest
import com.aspald.aspald.data.model.User
import com.aspald.aspald.data.model.UserResponse
import com.aspald.aspald.data.remote.ApiServices
import com.aspald.aspald.utils.Constants
import com.aspald.aspald.utils.UiState
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException

class AuthRepository(
    private val apiServices: ApiServices,
    private val context: Context
) {
    private val _authFlow = MutableStateFlow<UiState<String>>(UiState.Loading())
    private val authState = _authFlow.asStateFlow()

    private val _signedInUser = MutableStateFlow(UserResponse())

    suspend fun signUp(username: String, email: String, password: String): Flow<UiState<String>> {
        try {
            val client = apiServices.signUp(
                SignUpRequest(
                    username,
                    email,
                    password
                )
            )
            if (client.isSuccessful) {
                _authFlow.value = UiState.Success("User registered successfully, please Sign In")
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

    suspend fun signIn(email: String, password: String): Flow<UiState<String>> {
        try {
            val client = apiServices.signIn(
                SignInRequest(
                    email,
                    password
                )
            )
            if (client.isSuccessful) {
                val message = client.body()?.message
                val loginResult = client.body()?.loginResult
                loginResult?.let {
                    Preferences.saveToken(
                        it.token,
                        it.name,
                        email,
                        it.userId,
                        context
                    )
                }
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

    fun isUserSignedIn(): Boolean {
        val sharedPref = Preferences.init(context, "session")
        val token = sharedPref.getString(Constants.TOKEN, "")

        return token != ""
    }

    fun getSignedInUser(): User {
        val sharedPref = Preferences.init(context, "session")
        val userId = sharedPref.getString(Constants.USER_ID, "")
        val name = sharedPref.getString(Constants.NAME, "")
        val email = sharedPref.getString(Constants.EMAIL, "")

        return User(
            id = userId,
            name = name,
            email = email
        )
    }

//    suspend fun getSignedInUser(): Flow<UserResponse> {
//        val sharedPref = Preferences.init(context, "session")
//        val userId = sharedPref.getString(Constants.USER_ID, "")
//        try {
//            val client = apiServices.getUser(userId ?: "")
//            if (client.isSuccessful) {
//                client.body()?.let {
//                    _signedInUser.value = it
//                }
//            }else {
//                throw HttpException(client)
//            }
//        } catch (e: HttpException) {
//            val jsonInString = e.response()?.errorBody()?.string()
//            val errorBody = Gson().fromJson(jsonInString, PostResponse::class.java)
//            val errorMessage = errorBody.message
//            Log.e("HTTPERROR: ", errorMessage)
//        }
//
//        return _signedInUser.asStateFlow()
//    }

    fun logOut() {
        Preferences.logOut(context)
    }
}