package com.aspald.aspald.utils

import com.google.android.gms.maps.model.LatLng

sealed interface UiState {
    data object Loading: UiState
    data class Success(val location: LatLng?): UiState
    data object RevokedPermissions: UiState
    data class Error(val message: String) : UiState
}