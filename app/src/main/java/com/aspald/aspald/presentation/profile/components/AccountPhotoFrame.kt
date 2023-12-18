package com.aspald.aspald.presentation.profile.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.aspald.aspald.R
import com.aspald.aspald.ui.theme.AspaldOrange

@Composable
fun AccountPhotoFrame() {
    Box(
        modifier = Modifier
            .widthIn(200.dp, 250.dp)
            .heightIn(200.dp, 250.dp)
    ) {
        Box(
            modifier = Modifier
                .widthIn(200.dp, 250.dp)
                .heightIn(200.dp, 250.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .border(border = BorderStroke(5.dp, AspaldOrange), shape = CircleShape)
        ) {
            Image(
                modifier = Modifier
                    .widthIn(180.dp, 220.dp)
                    .heightIn(180.dp, 220.dp)
                    .clip(CircleShape)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.ic_person),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        val context = LocalContext.current
        Image(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .align(Alignment.BottomEnd)
                .zIndex(1f)
                .offset(x = (-10).dp, y = (-10).dp)
                .clickable {
                    Toast
                        .makeText(context, "To be implemented", Toast.LENGTH_SHORT)
                        .show()
                },
            painter = painterResource(id = R.drawable.ic_photo),
            contentDescription = null,
        )
    }
}