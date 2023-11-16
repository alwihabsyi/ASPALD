package com.aspald.aspald.presentation.navgraph

sealed class Route(
    val route: String
) {
    data object HomeScreen: Route(route = "homeScreen")
    data object ProfileScreen: Route(route = "profileScreen")
    data object ReportScreen: Route(route = "reportScreen")
    data object SearchScreen: Route(route = "searchScreen")
    data object DetailScreen: Route(route = "detailScreen")
}
