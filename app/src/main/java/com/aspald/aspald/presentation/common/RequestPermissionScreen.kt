package com.aspald.aspald.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aspald.aspald.R
import com.aspald.aspald.ui.theme.AspaldOrange
import com.aspald.aspald.utils.hasLocationPermission

@Composable
fun RequestPermissionScreen(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        Text(
            text = stringResource(id = R.string.permission_needed),
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(5.dp))
        Button(
            onClick = onClick,
            enabled = !context.hasLocationPermission(),
            colors = ButtonDefaults.buttonColors(
                containerColor = AspaldOrange
            )
        ) {
            if (context.hasLocationPermission()) CircularProgressIndicator(
                modifier = Modifier.size(14.dp),
                color = Color.White
            )
            else Text("Settings")
        }
    }
}