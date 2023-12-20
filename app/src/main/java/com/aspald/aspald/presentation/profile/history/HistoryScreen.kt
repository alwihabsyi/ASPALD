package com.aspald.aspald.presentation.profile.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aspald.aspald.R
import com.aspald.aspald.data.model.Report
import com.aspald.aspald.presentation.common.AspaldTopBar
import com.aspald.aspald.presentation.common.LoadingScreen
import com.aspald.aspald.presentation.profile.components.HistoryCard
import com.aspald.aspald.utils.UiState
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HistoryScreen(
    userReports: UiState<List<Report>>,
    onBackClick: () -> Unit
) {
    with(userReports) {
        when (this) {
            is UiState.Success -> {
                data?.let {
                    if (data.isNotEmpty()) {
                        HistoryContent(userReports = data, onBackClick = onBackClick)
                    } else {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(
                                modifier = Modifier
                                    .widthIn(200.dp, 250.dp)
                                    .align(Alignment.Center),
                                text = "No reports made",
                                fontFamily = FontFamily(Font(R.font.poppins_medium, FontWeight.Medium)),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            else -> {
                LoadingScreen()
            }
        }
    }
}

@Composable
fun HistoryContent(
    userReports: List<Report>,
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
        LazyColumn {
            items(userReports, key = { it.id }) {
                val date = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(it.createdAt)
                HistoryCard(
                    date = date,
                    streetName = it.description
                )
            }
        }
    }
}