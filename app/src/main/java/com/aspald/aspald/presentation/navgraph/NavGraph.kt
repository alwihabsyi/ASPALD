package com.aspald.aspald.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.aspald.aspald.presentation.auth.AuthViewModel
import com.aspald.aspald.presentation.auth.signin.SignInScreen
import com.aspald.aspald.presentation.auth.signup.SignUpScreen
import com.aspald.aspald.utils.MapState
import com.aspald.aspald.utils.navigateAuth

@Composable
fun NavGraph(
    startDestination: String,
    uiState: MapState,
    onRequestPermission: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = Route.AuthNavigator.route,
            startDestination = Route.SignInScreen.route
        ) {
            composable(
                route = Route.SignInScreen.route
            ) {
                val viewModel: AuthViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState()
                SignInScreen(
                    event = viewModel::onEvent,
                    state = state.value,
                    onSignUpClick = { navigateAuth(navController, Route.SignUpScreen.route) },
                    navigateToHome = { navigateToHome(navController, Route.HomeNavigator.route) }
                )
            }

            composable(
                route = Route.SignUpScreen.route
            ) {
                SignUpScreen()
            }
        }

        navigation(
            route = Route.HomeNavigator.route,
            startDestination = Route.AspaldNavigator.route
        ) {
            composable(
                route = Route.AspaldNavigator.route
            ) {
                AspaldNavigator(uiState = uiState, onRequestPermission = onRequestPermission)
            }
        }
    }
}

fun navigateToHome(navController: NavController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id){
            inclusive = true
        }
        navController.popBackStack()
    }
}