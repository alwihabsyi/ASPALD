package com.aspald.aspald.utils

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationService: LocationServiceInterface
) {
    operator fun invoke(): Flow<LatLng?> = locationService.requestLocationUpdates()
}