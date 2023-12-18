package com.aspald.aspald.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aspald.aspald.R
import com.aspald.aspald.data.model.User
import com.aspald.aspald.presentation.navgraph.Route
import com.aspald.aspald.presentation.profile.components.ProfileTiles
import com.aspald.aspald.presentation.profile.components.ProfileTopSection

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    user: User,
    navigate: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileTopSection(
            user = user
        )
        Card(
            modifier = Modifier
                .widthIn(min = 500.dp, max = 1000.dp)
                .padding(horizontal = 30.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            for (i in profileNavRoutes) {
                ProfileTiles(
                    modifier = Modifier.height(48.dp),
                    icon = i.icon,
                    title = i.title,
                    navigate = { navigate(i.route) },
                    isLastComponent = i.route == "Logout"
                )
            }
        }
    }
}

data class ProfileNavigationItem(
    val icon: Int,
    val route: String,
    val title: String
)

val profileNavRoutes = listOf(
    ProfileNavigationItem(R.drawable.ic_profile, Route.AccountScreen.route, "Account"),
    ProfileNavigationItem(R.drawable.ic_profile, Route.ProfileSettingScreen.route, "Profile"),
    ProfileNavigationItem(R.drawable.ic_history, Route.HistoryScreen.route, "History"),
    ProfileNavigationItem(R.drawable.ic_about, Route.AboutScreen.route, "About Aspald"),
    ProfileNavigationItem(R.drawable.ic_logout, "Logout", "Log out"),
)