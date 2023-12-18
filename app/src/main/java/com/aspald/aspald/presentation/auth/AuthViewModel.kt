package com.aspald.aspald.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspald.aspald.data.repository.AuthRepository
import com.aspald.aspald.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _state = MutableStateFlow<UiState<String>>(UiState.Loading())
    val state = _state.asStateFlow()

    suspend fun onEvent(event: AuthEvent) {
        when(event) {
            is AuthEvent.SignIn -> {
                signIn(event.email, event.password)
            }
            is AuthEvent.SignUp -> {
                signUp(event.name, event.email, event.password)
            }
            is AuthEvent.ResetPassword -> {
                _state.value = UiState.Success("To be implemented")
            }

            else -> {
                _state.value = UiState.Loading()
            }
        }
    }

    private suspend fun signIn(email: String, password: String) {
        authRepository.signIn(email, password).collect {
            _state.value = it
        }
    }

    private fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            authRepository.signUp(name, email, password).collect {
                _state.value = it
            }
        }
    }
}