package com.aspald.aspald.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspald.aspald.data.AuthRepository
import com.aspald.aspald.data.api.ApiServices
import com.aspald.aspald.data.model.SignInRequest
import com.aspald.aspald.data.model.SignInResponse
import com.aspald.aspald.utils.NavigationActions
import com.aspald.aspald.utils.UiState
import com.aspald.aspald.utils.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = authRepository.signIn(email, password)
//            _uiState.value = result.fold(
//                onSuccess = { UiState.Success(null) },
//                onFailure = { UiState.Error(it.message ?: "Unknown error") }
//            )
            if (result.isSuccess) {
                _uiState.value = UiState.Success(null)
            } else {
                _uiState.value = UiState.Error(result.exceptionOrNull()?.message ?: "Unknown Error")
            }
        }
    }
}
