package com.aspald.aspald.presentation.common

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.aspald.aspald.ui.theme.AspaldOrange
import com.aspald.aspald.ui.theme.AspaldWhite

@Composable
fun AspaldFab(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: Int
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        shape = CircleShape,
        containerColor = AspaldWhite
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = AspaldOrange
        )
    }
}