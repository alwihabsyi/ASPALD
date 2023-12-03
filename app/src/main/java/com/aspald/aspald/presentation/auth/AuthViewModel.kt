package com.aspald.aspald.presentation.auth

import androidx.lifecycle.ViewModel
import com.aspald.aspald.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth
): ViewModel() {
    private val _state = MutableStateFlow<UiState<String>>(UiState.Loading())
    val state = _state.asStateFlow()

    fun onEvent(event: AuthEvent) {
        when(event) {
            is AuthEvent.SignIn -> {
                auth.signInWithEmailAndPassword(event.email, event.password)
                    .addOnSuccessListener {
                        _state.value = UiState.Success("Sign in success")
                    }
                    .addOnFailureListener {
                        _state.value = UiState.Error(it.message.toString())
                    }
            }
            is AuthEvent.SignUp -> {
                auth.createUserWithEmailAndPassword(event.email, event.password)
                    .addOnSuccessListener {
                        _state.value = UiState.Success("Account created, please sign in")
                    }
                    .addOnFailureListener {
                        _state.value = UiState.Error(it.message.toString())
                    }
            }
            is AuthEvent.ResetPassword -> {
                auth.sendPasswordResetEmail(event.email)
                    .addOnSuccessListener {
                        _state.value = UiState.Success("Password reset link has been sent to your email")
                    }
                    .addOnFailureListener {
                        _state.value = UiState.Error(it.message.toString())
                    }
            }
        }
    }
}