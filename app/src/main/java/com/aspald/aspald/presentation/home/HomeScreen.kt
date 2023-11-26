package com.aspald.aspald.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.aspald.aspald.R
import com.aspald.aspald.presentation.Dimens
import com.aspald.aspald.presentation.common.AspaldFab
import com.aspald.aspald.presentation.common.MapUi
import com.aspald.aspald.presentation.common.SearchBar
import com.aspald.aspald.utils.centerOnLocation
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onSearch: () -> Unit,
    currentPosition: LatLng,
    cameraState: CameraPositionState
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        MapUi(
            context = context,
            cameraState = cameraState,
            currentPosition = currentPosition
        )

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

        AspaldFab(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            onClick = {
                coroutineScope.launch {
                    cameraState.centerOnLocation(currentPosition)
                }
            },
            icon = R.drawable.ic_center_map
        )
    }
}