package com.aspald.aspald

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aspald.aspald.presentation.home.HomeViewModel
import com.aspald.aspald.presentation.navgraph.NavGraph
import com.aspald.aspald.ui.theme.ASPALDTheme
import com.aspald.aspald.utils.PermissionEvent
import com.aspald.aspald.utils.RationaleAlert
import com.aspald.aspald.utils.hasLocationPermission
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: HomeViewModel by viewModels()
        setContent {
            val permissionState = rememberMultiplePermissionsState(
                permissions = listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            val uiState by viewModel.state.collectAsStateWithLifecycle()
            ASPALDTheme {
                LaunchedEffect(!hasLocationPermission()) {
                    permissionState.launchMultiplePermissionRequest()
                }

                when {
                    permissionState.allPermissionsGranted -> {
                        LaunchedEffect(Unit) {
                            viewModel.handle(PermissionEvent.Granted)
                        }
                    }
                    permissionState.shouldShowRationale -> {
                        RationaleAlert(onDismiss = {  }) {
                            permissionState.launchMultiplePermissionRequest()
                        }
                    }
                    !permissionState.allPermissionsGranted && !permissionState.shouldShowRationale -> {
                        LaunchedEffect(Unit) {
                            viewModel.handle(PermissionEvent.Revoked)
                        }
                    }
                }

                Box(
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
                ) {
                    NavGraph(
                        uiState = uiState,
                        onRequestPermission = {
                            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                        },
                        startDestination = viewModel.startDestination
                    )
                }
            }
        }
    }
}