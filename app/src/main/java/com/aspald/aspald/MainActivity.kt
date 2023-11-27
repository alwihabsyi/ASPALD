package com.aspald.aspald

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.net.Uri.*
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.aspald.aspald.presentation.home.HomeViewModel
import com.aspald.aspald.presentation.navgraph.AspaldNavigator
import com.aspald.aspald.presentation.navgraph.Route
import com.aspald.aspald.presentation.signin.SignInScreen

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
        val locationViewModel: HomeViewModel by viewModels()
        val mainViewModel : MainViewModel by viewModels()
        setContent {

            val navController = rememberNavController()
            val initialRoute by mainViewModel.initialRoute.collectAsStateWithLifecycle()

            val permissionState = rememberMultiplePermissionsState(
                permissions = listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            val uiState by locationViewModel.state.collectAsStateWithLifecycle()
            ASPALDTheme {
                LaunchedEffect(!hasLocationPermission()) {
                    permissionState.launchMultiplePermissionRequest()
                }

                when {
                    permissionState.allPermissionsGranted -> {
                        LaunchedEffect(Unit) {
                            locationViewModel.handle(PermissionEvent.Granted)
                        }
                    }
                    permissionState.shouldShowRationale -> {
                        RationaleAlert(onDismiss = {  }) {
                            permissionState.launchMultiplePermissionRequest()
                        }
                    }
                    !permissionState.allPermissionsGranted && !permissionState.shouldShowRationale -> {
                        LaunchedEffect(Unit) {
                            locationViewModel.handle(PermissionEvent.Revoked)
                        }
                    }
                }

                Box(
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
                ) {
                   NavHost(navController = rememberNavController(), startDestination = Route.SignInScreen.route){
                       composable(Route.SignInScreen.route) { SignInScreen(navController) }
                       composable(Route.HomeScreen.route){
                           AspaldNavigator(uiState = uiState, onRequestPermission = {
                               startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                   data = fromParts("package", packageName, null)
                               })
                           })
                       }
                   }
                }
            }
        }
    }

}