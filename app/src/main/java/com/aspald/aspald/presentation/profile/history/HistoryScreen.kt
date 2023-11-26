package com.aspald.aspald.presentation.profile.history

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
import com.aspald.aspald.presentation.profile.components.HistoryCard

@Composable
fun HistoryScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AspaldTopBar(topBarTitle = "History", onBackClick = onBackClick)
        Spacer(modifier = Modifier.height(70.dp))
        HistoryCard(date = "1 November 2023", streetName = "Jl. Jeglongan Sewu KM 69")
    }
}