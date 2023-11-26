package com.aspald.aspald.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aspald.aspald.R
import com.aspald.aspald.ui.theme.AspaldYellow

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = AspaldYellow
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.widthIn(200.dp, 250.dp),
            text = stringResource(id = R.string.loading_message),
            fontFamily = FontFamily(Font(R.font.poppins_medium, FontWeight.Medium)),
            textAlign = TextAlign.Center
        )
    }
}