package com.aspald.aspald.presentation.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aspald.aspald.R

@Composable
fun ProfileTiles(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    navigate: () -> Unit,
    isLastComponent: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .clickable { navigate() },
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                modifier = Modifier.width(24.dp),
                painter = painterResource(id = icon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                modifier = Modifier.fillMaxWidth(0.9f),
                text = title,
                textAlign = TextAlign.Left,
                fontFamily = FontFamily(Font(R.font.poppins_medium, FontWeight.Medium))
            )
            Icon(
                modifier = Modifier.width(24.dp),
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
    if (!isLastComponent) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
    }
}