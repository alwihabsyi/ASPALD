package com.aspald.aspald.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspald.aspald.R
import com.aspald.aspald.ui.theme.AspaldOrange
import com.aspald.aspald.ui.theme.AspaldWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AspaldTopBar(
    modifier: Modifier = Modifier,
    topBarTitle: String,
    onBackClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = {
            Column {
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = topBarTitle,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(
                        Font(R.font.poppins_medium, FontWeight.Medium)
                    )
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = AspaldWhite,
            actionIconContentColor = AspaldOrange
        ),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    modifier = Modifier
                        .width(26.dp)
                        .height(26.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null,
                    tint = AspaldOrange
                )
            }
        }
    )
}