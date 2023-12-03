package com.aspald.aspald.utils

import com.google.android.gms.maps.model.LatLng

sealed interface MapState {
    data object Loading: MapState
    data class Success(val location: LatLng?): MapState
    data object RevokedPermissions: MapState
}