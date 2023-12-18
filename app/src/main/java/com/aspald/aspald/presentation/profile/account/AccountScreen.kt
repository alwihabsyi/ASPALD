package com.aspald.aspald.presentation.profile.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aspald.aspald.R
import com.aspald.aspald.data.model.User
import com.aspald.aspald.presentation.common.AspaldTextField
import com.aspald.aspald.presentation.common.AspaldTopBar
import com.aspald.aspald.presentation.profile.components.AccountPhotoFrame

@Composable
fun AccountScreen(
    user: User,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AspaldTopBar(topBarTitle = "Account", onBackClick = onBackClick)
        Spacer(modifier = Modifier.height(30.dp))
        AccountPhotoFrame()
        Spacer(modifier = Modifier.height(70.dp))
        AspaldTextField(
            icon = R.drawable.ic_email,
            label = "Email",
            text = user.email ?: "",
            onValueChange = { },
            onSearch = { }
        )
        Spacer(modifier = Modifier.height(30.dp))
        AspaldTextField(
            icon = R.drawable.ic_lock,
            label = "Password",
            text = "********",
            onValueChange = { },
            onSearch = { }
        )
    }
}