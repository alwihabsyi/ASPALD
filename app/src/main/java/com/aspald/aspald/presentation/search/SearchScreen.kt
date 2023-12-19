package com.aspald.aspald.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
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

@Composable
fun SearchScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .widthIn(200.dp, 250.dp)
                .align(Alignment.Center),
            text = "To Be Implemented",
            fontFamily = FontFamily(Font(R.font.poppins_medium, FontWeight.Medium)),
            textAlign = TextAlign.Center
        )
    }
}