package com.aspald.aspald.utils

import com.google.android.gms.maps.model.LatLng

sealed interface UiState {
    data object Loading: UiState
    data class Success(val location: LatLng?): UiState
    data object RevokedPermissions: UiState
    data class Error(val message: String) : UiState
    data object SignIn : UiState // Added for navigation to Sign In screen
    data object SignUp : UiState // Added for navigation to Sign Up screen
}