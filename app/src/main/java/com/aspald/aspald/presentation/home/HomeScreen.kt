package com.aspald.aspald.presentation.home

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aspald.aspald.R
import com.aspald.aspald.data.model.Report
import com.aspald.aspald.presentation.Dimens
import com.aspald.aspald.presentation.common.AspaldFab
import com.aspald.aspald.presentation.common.MapUi
import com.aspald.aspald.presentation.common.SearchBar
import com.aspald.aspald.presentation.home.components.CardJalanRusak
import com.aspald.aspald.ui.theme.AspaldWhite
import com.aspald.aspald.ui.theme.AspaldYellow
import com.aspald.aspald.utils.centerOnLocation
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onSearch: () -> Unit,
    currentPosition: LatLng,
    cameraState: CameraPositionState,
    reports: List<Report>,
    onInfoWindowClick: (String) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        MapUi(
            context = context,
            cameraState = cameraState,
            currentPosition = currentPosition,
            report = reports,
            onInfoWindowClick = onInfoWindowClick
        )
        SearchBar(modifier = Modifier
            .padding(
                horizontal = Dimens.MediumPadding1, vertical = Dimens.MediumPadding2
            )
            .align(Alignment.TopCenter),
            text = "",
            readOnly = true,
            onValueChange = {},
            onClick = onSearch,
            onSearch = {})

        var expanded by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .width(60.dp)
                    .height(24.dp),
                onClick = {
                    expanded = expanded != true
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = AspaldYellow
                )
            ) {
                if (!expanded) Icon(
                    painter = painterResource(id = R.drawable.ic_keyboard_arrow_up),
                    contentDescription = null,
                    tint = AspaldWhite
                )
                else Icon(
                    painter = painterResource(id = R.drawable.ic_keyboard_arrow_down),
                    contentDescription = null,
                    tint = AspaldWhite
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(tween(500)),
                exit = shrinkVertically(tween(500))
            ) {
                val descriptionMaxLength = 40
                val ellipsis = "..."
                val message = "Hai! Ada jalan rusak nih di : "
                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(reports, key = { it.id }) {
                        CardJalanRusak(imageUrl = it.photoUrl,
                            title = it.name,
                            description = it.description.take(descriptionMaxLength - ellipsis.length) + ellipsis,
                            onDirectionClick = {
                                coroutineScope.launch {
                                    cameraState.centerOnLocation(
                                        LatLng(
                                            it.lat,
                                            it.lon
                                        )
                                    )
                                }
                            },
                            onShareClick = {
                                Intent(Intent.ACTION_SEND).also { intent ->
                                    intent.putExtra(
                                        Intent.EXTRA_TEXT,
                                        "$message https://maps.google.com/?ll=${it.lat},${it.lon}"
                                    )
                                    intent.type = "text/plain"
                                    if (intent.resolveActivity(context.packageManager) != null) {
                                        context.startActivity(intent)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            visible = !expanded,
            enter = scaleIn(tween(500)),
            exit = scaleOut(tween(500))
        ) {
            AspaldFab(
                onClick = {
                    coroutineScope.launch {
                        cameraState.centerOnLocation(currentPosition)
                    }
                }, icon = R.drawable.ic_center_map
            )
        }
    }
}