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
import com.aspald.aspald.presentation.common.AspaldTopBar
import com.aspald.aspald.presentation.common.IconLessTextField

@Composable
fun ProfileEditScreen(
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
            text = "Mega Wangi",
            onValueChange = { },
            onSearch = { }
        )
        Spacer(modifier = Modifier.height(25.dp))
        IconLessTextField(
            label = "Address",
            text = "Yogyakarta",
            onValueChange = { },
            onSearch = { }
        )
        Spacer(modifier = Modifier.height(25.dp))
        IconLessTextField(
            label = "Date of Birth",
            text = "02-10-2000",
            onValueChange = { },
            onSearch = { }
        )
        Spacer(modifier = Modifier.height(25.dp))
        IconLessTextField(
            label = "Phone",
            text = "085705778545",
            onValueChange = { },
            onSearch = { }
        )
    }
}