package com.aspald.aspald.presentation.common

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aspald.aspald.R
import com.aspald.aspald.data.model.Report
import com.aspald.aspald.utils.bitmapDescriptorFromVector
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun MapUi(
    context: Context,
    cameraState: CameraPositionState,
    currentPosition: LatLng,
    onInfoWindowClick: (String) -> Unit,
    report: List<Report>
) {
    val marker = LatLng(currentPosition.latitude, currentPosition.longitude)
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraState,
        properties = MapProperties(
            isMyLocationEnabled = true, mapType = MapType.NORMAL, isTrafficEnabled = true
        ),
        uiSettings = MapUiSettings(
            compassEnabled = false,
            myLocationButtonEnabled = false,
            zoomControlsEnabled = false,
            mapToolbarEnabled = false
        )
    ) {
        Marker(
            state = MarkerState(position = marker),
            title = "Your Position",
            snippet = "Marker Description",
            onInfoWindowClick = {},
            draggable = true,
            icon = bitmapDescriptorFromVector(context, R.drawable.ic_map_point)
        )
        report.forEach {
            val reportMarker = LatLng(it.lat, it.lon)
            val descriptionMaxLength = 30
            val ellipsis = "..."
            Marker(
                state = MarkerState(position = reportMarker),
                title = it.name,
                snippet = it.description.take(descriptionMaxLength - ellipsis.length) + ellipsis,
                onInfoWindowClick = { _ ->
                    onInfoWindowClick(it.id)
                },
                draggable = false,
                icon = bitmapDescriptorFromVector(context, R.drawable.ic_map_point)
            )
        }
    }
}