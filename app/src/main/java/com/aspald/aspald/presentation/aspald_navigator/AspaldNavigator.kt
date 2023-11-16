package com.aspald.aspald.presentation.aspald_navigator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aspald.aspald.R
import com.aspald.aspald.presentation.aspald_navigator.components.AspaldBottomNavigation
import com.aspald.aspald.presentation.aspald_navigator.components.BottomNavigationItem
import com.aspald.aspald.presentation.home.HomeScreen
import com.aspald.aspald.presentation.navgraph.Route
import com.aspald.aspald.presentation.profile.ProfileScreen
import com.aspald.aspald.ui.theme.AspaldYellow
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AspaldNavigator() {
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    selectedItem = remember(key1 = backStackState) {
        when(backStackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.ReportScreen.route -> 1
            Route.ProfileScreen.route -> 2
            else -> 0
        }
    }
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.ProfileScreen.route
    }
    SetStatusBar(backStackState)
    SetNavigation(isBottomBarVisible, selectedItem, navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetNavigation(
    bottomBarVisible: Boolean,
    selectedItem: Int,
    navController: NavHostController
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
                    onItemClicked = { index ->
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
                                route = Route.ProfileScreen.route
                            )
                        }
                    }
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
                HomeScreen()
            }
            composable(route = Route.ReportScreen.route) {

            }
            composable(route = Route.ProfileScreen.route) {
                ProfileScreen()
            }
            composable(route = Route.SearchScreen.route) {

            }
        }
    }
}

@Composable
fun SetStatusBar(backStackState: NavBackStackEntry?) {
    val systemController = rememberSystemUiController()
    val inProfile = backStackState?.destination?.route == Route.ProfileScreen.route

    SideEffect {
        systemController.setSystemBarsColor(
            color = if (inProfile) AspaldYellow else Color.Transparent,
            darkIcons = !inProfile
        )
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}