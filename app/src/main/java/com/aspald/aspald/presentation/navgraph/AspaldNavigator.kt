package com.aspald.aspald.presentation.navgraph

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aspald.aspald.R
import com.aspald.aspald.presentation.common.LoadingScreen
import com.aspald.aspald.presentation.common.RequestPermissionScreen
import com.aspald.aspald.presentation.home.HomeScreen
import com.aspald.aspald.presentation.navgraph.components.AspaldBottomNavigation
import com.aspald.aspald.presentation.navgraph.components.BottomNavigationItem
import com.aspald.aspald.presentation.profile.ProfileScreen
import com.aspald.aspald.presentation.profile.account.AccountScreen
import com.aspald.aspald.presentation.profile.history.HistoryScreen
import com.aspald.aspald.presentation.profile.profileedit.ProfileEditScreen
import com.aspald.aspald.presentation.report.ReportScreen
import com.aspald.aspald.utils.SetStatusBar
import com.aspald.aspald.utils.MapState
import com.aspald.aspald.utils.centerOnLocation
import com.aspald.aspald.utils.navigateProfile
import com.aspald.aspald.utils.navigateToTab
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun AspaldNavigator(
    uiState: MapState,
    onRequestPermission: () -> Unit
) {
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.ReportScreen.route -> 1
            Route.ProfileNavigator.route -> 2
            else -> 0
        }
    }
    val isBottomBarVisible = when (backStackState?.destination?.route) {
        Route.HomeScreen.route -> true
        Route.ProfileNavigator.route -> true
        else -> false
    }
    SetStatusBar(backStackState)
    SetNavigation(isBottomBarVisible, selectedItem, navController, uiState, onRequestPermission)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetNavigation(
    bottomBarVisible: Boolean,
    selectedItem: Int,
    navController: NavHostController,
    uiState: MapState,
    onRequestPermission: () -> Unit
) {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Explore"),
            BottomNavigationItem(icon = R.drawable.ic_add_map, text = "Report"),
            BottomNavigationItem(icon = R.drawable.ic_profile, text = "Profile")
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (bottomBarVisible) {
                AspaldBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClicked = { onItemClicked(navController, it) }
                )
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                with(uiState) {
                    when(this) {
                        MapState.Loading -> {
                            LoadingScreen(isFromHome = true)
                        }
                        MapState.RevokedPermissions -> {
                            RequestPermissionScreen(onRequestPermission)
                        }
                        is MapState.Success -> {
                            val currentLoc = LatLng(
                                location?.latitude ?: 0.0,
                                location?.longitude ?: 0.0
                            )
                            val cameraState = rememberCameraPositionState()
                            LaunchedEffect(key1 = currentLoc) {
                                cameraState.centerOnLocation(currentLoc)
                            }
                            HomeScreen(
                                currentPosition = LatLng(currentLoc.latitude, currentLoc.longitude),
                                cameraState = cameraState,
                                onSearch = { navigateToTab(navController, Route.SearchScreen.route) }
                            )
                        }
                    }
                }
            }
            composable(route = Route.ReportScreen.route) {
                ReportScreen (
                    onBackClick = { navController.navigateUp() }
                )
            }
            composable(route = Route.ProfileNavigator.route) {
                val context = LocalContext.current
                ProfileScreen(
                    navigate = { route ->
                        if (route == "Logout") {
                            Toast.makeText(context, route, Toast.LENGTH_SHORT).show()
                            return@ProfileScreen
                        }

                        navigateProfile(navController, route)
                    }
                )
            }
            composable(route = Route.SearchScreen.route) {

            }
            composable(route = Route.AccountScreen.route) {
                AccountScreen(
                    onBackClick = { navController.navigateUp() }
                )
            }
            composable(
                route = Route.ProfileSettingScreen.route
            ) {
                ProfileEditScreen (
                    onBackClick = { navController.navigateUp() }
                )
            }
            composable(
                route = Route.HistoryScreen.route
            ) {
                HistoryScreen (
                    onBackClick = { navController.navigateUp() }
                )
            }
            composable(
                route = Route.SettingsScreen.route
            ) {

            }
            composable(
                route = Route.AboutScreen.route
            ) {

            }
        }
    }
}

fun onItemClicked(navController: NavController, index: Int) {
    when (index) {
        0 -> navigateToTab(
            navController = navController,
            route = Route.HomeScreen.route
        )

        1 -> navigateToTab(
            navController = navController,
            route = Route.ReportScreen.route
        )

        2 -> navigateToTab(
            navController = navController,
            route = Route.ProfileNavigator.route
        )
    }
}
