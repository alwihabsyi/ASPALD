package com.aspald.aspald.presentation.detail

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.aspald.aspald.R
import com.aspald.aspald.data.model.Report
import com.aspald.aspald.presentation.common.AspaldTopBar
import com.aspald.aspald.presentation.report.components.MapCard
import com.aspald.aspald.ui.theme.AspaldOrange
import com.aspald.aspald.ui.theme.AspaldYellow
import com.aspald.aspald.utils.UiState
import com.aspald.aspald.utils.centerOnLocation
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DetailScreen(
    report: UiState<Report>,
    address: String,
    onBackClick: () -> Unit,
    event: (DetailEvent) -> Unit
) {
    val context = LocalContext.current
    with(report) {
        when (this) {
            is UiState.Error -> {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                onBackClick
            }
            is UiState.Loading -> {

            }
            is UiState.Success -> {
                report.data?.let {
                    DetailContent(
                        report = report.data,
                        address = address,
                        onBackClick = onBackClick
                    )
                    event(DetailEvent.GetAddress(it.lat, it.lon))
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    report: Report,
    address: String,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val currentLocation = LatLng(report.lat, report.lon)
    val reportCameraState = rememberCameraPositionState()
    LaunchedEffect(key1 = report.lat) {
        reportCameraState.centerOnLocation(currentLocation)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AspaldTopBar(topBarTitle = "Broken road detail", onBackClick = onBackClick)

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = stringResource(id = R.string.detail),
                    fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
                    fontSize = 22.sp
                )
                Text(
                    text = "This damage is classified as: ${report.damageType}",
                    fontFamily = FontFamily(Font(R.font.poppins, FontWeight.Light)),
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .heightIn(200.dp, 250.dp)
                        .fillMaxWidth(),
                    value = report.description,
                    enabled = false,
                    onValueChange = {  },
                    maxLines = 5,
                    placeholder = {
                        Text(
                            text = "Description",
                            color = Color.Gray,
                            fontFamily = FontFamily(Font(R.font.poppins_medium, FontWeight.Medium))
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.Transparent,
                        disabledTextColor = Color.Gray,
                        focusedBorderColor = AspaldYellow,
                        unfocusedBorderColor = AspaldYellow,
                        disabledBorderColor = AspaldYellow
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .heightIn(50.dp, 100.dp)
                        .fillMaxWidth(),
                    value = address,
                    onValueChange = {  },
                    placeholder = {
                        Text(
                            text = "Address",
                            color = Color.Gray,
                            fontFamily = FontFamily(Font(R.font.poppins_medium, FontWeight.Medium))
                        )
                    },
                    enabled = false,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.Transparent,
                        disabledTextColor = Color.Gray,
                        focusedBorderColor = AspaldYellow,
                        unfocusedBorderColor = AspaldYellow,
                        disabledBorderColor = AspaldYellow
                    ),
                    trailingIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_point),
                                contentDescription = null,
                                tint = AspaldOrange
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
                MapCard(
                    cameraState = reportCameraState,
                    currentPosition = currentLocation,
                    context = context
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = stringResource(id = R.string.road_photos),
                    fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(15.dp))
                DetailPhotoCard(
                    imageUrl = report.photoUrl
                )
            }
        }
    }
}

@Composable
fun DetailPhotoCard(
    modifier: Modifier = Modifier,
    imageUrl: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, AspaldYellow),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
    }
}