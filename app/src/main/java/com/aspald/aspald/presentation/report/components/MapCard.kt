package com.aspald.aspald.presentation.report.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspald.aspald.R
import com.aspald.aspald.presentation.report.ReportMapUi
import com.aspald.aspald.ui.theme.AspaldYellow
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState

@Composable
fun MapCard(cameraState: CameraPositionState, currentPosition: LatLng) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .heightIn(100.dp, 150.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(30.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            ReportMapUi(
                context = context,
                cameraState = cameraState,
                currentPosition = currentPosition
            )
            Button(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp),
                shape = RoundedCornerShape(50.dp),
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = AspaldYellow
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_maps),
                    contentDescription = null,
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = stringResource(id = R.string.pin_location_point),
                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                    fontSize = 10.sp,
                    color = Color.Black
                )
            }
        }
    }
}