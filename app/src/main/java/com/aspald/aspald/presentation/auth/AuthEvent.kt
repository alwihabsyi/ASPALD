package com.aspald.aspald.presentation.auth

sealed class AuthEvent {
    data class SignIn(val email: String, val password: String): AuthEvent()
    data class SignUp(val email: String, val password: String): AuthEvent()
    data class ResetPassword(val email: String): AuthEvent()
    data object ResetState : AuthEvent()
}