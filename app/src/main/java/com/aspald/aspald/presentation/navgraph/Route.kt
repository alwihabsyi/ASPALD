package com.aspald.aspald.presentation.navgraph

sealed class Route(
    val route: String
) {
    data object HomeScreen: Route(route = "homeScreen")
    data object ProfileNavigator: Route(route = "profileNavigator")
    data object ReportScreen: Route(route = "reportScreen")
    data object SearchScreen: Route(route = "searchScreen")
    data object DetailScreen: Route(route = "detailScreen")
    data object SignInScreen: Route(route = "signInScreen")
    
    //Profile
    data object AccountScreen: Route(route = "accountScreen")
    data object ProfileSettingScreen: Route(route = "profileSettingScreen")
    data object ProfileScreen: Route(route = "profileScreen")
    data object HistoryScreen: Route(route = "historyScreen")
    data object SettingsScreen: Route(route = "settingsScreen")
    data object AboutScreen: Route(route = "aboutScreen")
}
