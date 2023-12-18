package com.aspald.aspald.presentation.auth

import androidx.lifecycle.ViewModel
import com.aspald.aspald.data.repository.ReportRepository
import com.aspald.aspald.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val reportRepository: ReportRepository
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

    private suspend fun signIn(email: String, password: String) =
        reportRepository.signIn(email, password)

    private suspend fun signUp(name: String, email: String, password: String) =
        reportRepository.signUp(name, email, password)
}