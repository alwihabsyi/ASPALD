package com.aspald.aspald.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

interface LocationServiceInterface {
    fun requestLocationUpdates(): Flow<LatLng?>
    fun requestCurrentLocation(): Flow<LatLng?>
}

class LocationService @Inject constructor(
    private val context: Context,
    private val locationClient: FusedLocationProviderClient
): LocationServiceInterface {
    @SuppressLint("MissingPermission")
    override fun requestLocationUpdates(): Flow<LatLng?> {
        return callbackFlow {
            if (!context.hasLocationPermission()) {
                trySend(null)
                return@callbackFlow
            }

            val request = LocationRequest.Builder(10000L)
                .setIntervalMillis(10000L)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build()

            val locationCallback = object: LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    p0.locations.lastOrNull()?.let {
                        trySend(LatLng(it.latitude, it.longitude))
                    }
                }
            }

            locationClient.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )

            awaitClose {
                locationClient.removeLocationUpdates(locationCallback)
            }
        }
    }

    override fun requestCurrentLocation(): Flow<LatLng?> {
        TODO("Not yet implemented")
    }

}