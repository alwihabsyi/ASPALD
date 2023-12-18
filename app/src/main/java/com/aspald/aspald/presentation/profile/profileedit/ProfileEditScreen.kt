package com.aspald.aspald.presentation.profile.profileedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aspald.aspald.data.model.User
import com.aspald.aspald.presentation.common.AspaldTopBar
import com.aspald.aspald.presentation.common.IconLessTextField

@Composable
fun ProfileEditScreen(
    user: User,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AspaldTopBar(topBarTitle = "Profile", onBackClick = onBackClick)
        Spacer(modifier = Modifier.height(70.dp))
        IconLessTextField(
            label = "Name",
            text = user.name ?: "",
            onValueChange = { },
            onSearch = { }
        )
        Spacer(modifier = Modifier.height(25.dp))
        IconLessTextField(
            label = "Address",
            text = user.email ?: "...",
            onValueChange = { },
            onSearch = { }
        )
        Spacer(modifier = Modifier.height(25.dp))
        IconLessTextField(
            label = "Date of Birth",
            text = "...",
            onValueChange = { },
            onSearch = { }
        )
        Spacer(modifier = Modifier.height(25.dp))
        IconLessTextField(
            label = "Phone",
            text = "...",
            onValueChange = { },
            onSearch = { }
        )
    }
}