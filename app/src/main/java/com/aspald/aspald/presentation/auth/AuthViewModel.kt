package com.aspald.aspald.presentation.auth

import androidx.lifecycle.ViewModel
import com.aspald.aspald.data.model.User
import com.aspald.aspald.utils.Constants.USER_COLLECTION
import com.aspald.aspald.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): ViewModel() {
    private val _state = MutableStateFlow<UiState<String>>(UiState.Loading())
    val state = _state.asStateFlow()

    fun onEvent(event: AuthEvent) {
        when(event) {
            is AuthEvent.SignIn -> {
                signIn(event.email, event.password)
            }
            is AuthEvent.SignUp -> {
                signUp(event.name, event.email, event.password)
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

            else -> {
                _state.value = UiState.Loading()
            }
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _state.value = UiState.Success("Sign in success")
            }
            .addOnFailureListener {
                _state.value = UiState.Error(it.message.toString())
            }
    }

    private fun signUp(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                saveUserInfo(it.user!!.uid, name, email)
            }
            .addOnFailureListener {
                _state.value = UiState.Error(it.message.toString())
            }
    }

    private fun saveUserInfo(userId: String, name: String, email: String) {
        val user = User(name, email, null)
        firestore.collection(USER_COLLECTION).document(userId).set(user)
            .addOnSuccessListener {
                _state.value = UiState.Success("Account created, please sign in")
            }
            .addOnFailureListener {
                _state.value = UiState.Error(it.message.toString())
            }
    }
}