package com.aspald.aspald.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.aspald.aspald.presentation.Dimens
import com.aspald.aspald.presentation.common.SearchBar
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun HomeScreen(
    onSearch: () -> Unit,
    currentPosition: LatLng,
    cameraState: CameraPositionState
) {
    val marker = LatLng(currentPosition.latitude, currentPosition.longitude)
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraState,
            properties = MapProperties(
                isMyLocationEnabled = true,
                mapType = MapType.NORMAL,
                isTrafficEnabled = true
            )
        ) {
            Marker(
                state = MarkerState(position = marker),
                title = "Your Position",
                snippet = "Marker Description",
                onInfoWindowClick = {
                    Toast.makeText(context, it.position.toString(), Toast.LENGTH_SHORT).show()
                },
                draggable = true
            )
        }
        SearchBar(
            modifier = Modifier
                .padding(horizontal = Dimens.MediumPadding1, vertical = Dimens.MediumPadding2)
                .align(Alignment.TopCenter),
            text = "",
            readOnly = true,
            onValueChange = {},
            onClick = onSearch,
            onSearch = {}
        )
    }
}