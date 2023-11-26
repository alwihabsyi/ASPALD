package com.aspald.aspald.utils

import androidx.navigation.NavController

interface NavigationActions {
    fun navigateToHomeScreen()
}

class NavigationsImpl(private val navController: NavController) : NavigationActions{
    override fun navigateToHomeScreen() {
        navController.navigate("homeScreen")
    }
}