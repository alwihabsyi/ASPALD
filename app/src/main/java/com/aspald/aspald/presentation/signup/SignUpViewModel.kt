package com.aspald.aspald.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspald.aspald.data.AuthRepository
import com.aspald.aspald.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun signUp(name: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            if (password == confirmPassword) {
                _uiState.value = UiState.Loading
                val result = authRepository.signUp(name, email, password)
                if (result.isSuccess) {
                    _uiState.value = UiState.Success(null)
                } else {
                    _uiState.value = UiState.Error(result.exceptionOrNull()?.message ?: "Unknown Error")
                }
            } else {
                // Passwords do not match
                _uiState.value = UiState.Error("Passwords do not match")
            }
        }
    }
}