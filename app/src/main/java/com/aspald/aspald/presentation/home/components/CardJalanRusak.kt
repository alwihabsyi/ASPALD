package com.aspald.aspald.presentation.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.aspald.aspald.R
import com.aspald.aspald.ui.theme.AspaldOrange
import com.aspald.aspald.ui.theme.AspaldWhite
import com.aspald.aspald.ui.theme.AspaldYellow

@Composable
fun CardJalanRusak(
    imageUrl: String,
    title: String,
    description: String,
    onDirectionClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .heightIn(200.dp, 300.dp)
            .widthIn(300.dp, 400.dp)
            .padding(horizontal = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = AspaldWhite
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            contentScale = ContentScale.Crop,
            model = imageUrl,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = title,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = description,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.poppins, FontWeight.Normal)),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onDirectionClick,
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AspaldYellow
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_direction),
                    contentDescription = null,
                    tint = Color.Black
                )
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Direction",
                    fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
            Button(
                onClick = onShareClick,
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AspaldWhite
                ),
                border = BorderStroke(2.dp, AspaldOrange)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = null,
                    tint = AspaldOrange
                )
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Share",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
                    color = AspaldOrange
                )
            }
        }
    }
}