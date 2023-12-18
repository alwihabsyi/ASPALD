package com.aspald.aspald.presentation.report

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.aspald.aspald.BuildConfig
import com.aspald.aspald.R
import com.aspald.aspald.presentation.common.AspaldTopBar
import com.aspald.aspald.presentation.report.components.ConfirmButton
import com.aspald.aspald.presentation.report.components.MapCard
import com.aspald.aspald.presentation.report.components.PhotoCard
import com.aspald.aspald.ui.theme.AspaldOrange
import com.aspald.aspald.ui.theme.AspaldYellow
import com.aspald.aspald.utils.centerOnLocation
import com.aspald.aspald.utils.checkCameraPermission
import com.aspald.aspald.utils.createImageFile
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.launch
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    currentPosition: LatLng,
    cameraState: CameraPositionState,
    onBackClick: () -> Unit
) {
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AspaldTopBar(topBarTitle = "Mark a broken road", onBackClick = onBackClick)

            Column(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
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
                    text = stringResource(id = R.string.provide_information),
                    fontFamily = FontFamily(Font(R.font.poppins, FontWeight.Light)),
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .heightIn(200.dp, 250.dp)
                        .fillMaxWidth(),
                    value = description,
                    onValueChange = { if (it.length < 255) description = it },
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
                        unfocusedBorderColor = AspaldYellow
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .heightIn(50.dp, 100.dp)
                        .fillMaxWidth(),
                    value = location,
                    onValueChange = { if (it.length < 100) location = it },
                    placeholder = {
                        Text(
                            text = "Address",
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
                    ),
                    trailingIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                cameraState.centerOnLocation(currentPosition)
                            }
                        }) {
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
                    cameraState = cameraState,
                    currentPosition = currentPosition
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = stringResource(id = R.string.road_photos),
                    fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
                    fontSize = 16.sp
                )
                Text(
                    text = stringResource(id = R.string.add_helpful_photos),
                    fontFamily = FontFamily(Font(R.font.poppins, FontWeight.Light)),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(15.dp))
                Photo()
            }
        }

        // TODO: Finish OnReport Click Function
        ConfirmButton(
            modifier = Modifier
                .heightIn(70.dp, 90.dp)
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 10.dp)
                .align(Alignment.BottomCenter),
            onClick = {  }
        )
    }
}

@Composable
fun Photo() {
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )
    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Row {
        PhotoCard {
            if (checkCameraPermission(context)) {
                cameraLauncher.launch(uri)
            }else {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
        if (capturedImageUri.path?.isNotEmpty() == true) {
            Spacer(modifier = Modifier.width(10.dp))
            PhotoCard(
                imageUri = capturedImageUri
            ) {

            }
        }
    }
}