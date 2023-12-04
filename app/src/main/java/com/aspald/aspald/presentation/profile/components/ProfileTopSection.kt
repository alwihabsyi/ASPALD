package com.aspald.aspald.presentation.profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.aspald.aspald.R
import com.aspald.aspald.ui.theme.AspaldYellow

@Composable
fun ProfileTopSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxHeight(0.5f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                .background(AspaldYellow)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .widthIn(100.dp, 140.dp)
                        .heightIn(100.dp, 140.dp)
                        .clip(CircleShape)
                        .border(border = BorderStroke(3.dp, Color.White), shape = CircleShape)
                ) {
                    Image(
                        modifier = Modifier
                            .widthIn(90.dp, 130.dp)
                            .heightIn(90.dp, 130.dp)
                            .clip(CircleShape)
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_person),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Mega Wangi",
                    fontSize = 22.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
                    color = Color.White
                )
                Text(
                    text = "mbamega@gmail.com",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.poppins, FontWeight.Normal)),
                    color = Color.White
                )
            }
        }
        Card(
            modifier = Modifier
                .widthIn(min = 500.dp, max = 1000.dp)
                .heightIn(50.dp, 70.dp)
                .padding(horizontal = 30.dp)
                .offset(y = (-35).dp)
                .zIndex(1f),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        painter = painterResource(id = R.drawable.ic_note),
                        contentDescription = null,
                        tint = AspaldYellow
                    )
                    Text(
                        text = "18 Reports Made",
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = FontFamily(Font(R.font.poppins_medium, FontWeight.Medium))
                    )
                }
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(2.dp)
                        .padding(vertical = 8.dp)
                )
                Row {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        painter = painterResource(id = R.drawable.ic_note),
                        contentDescription = null,
                        tint = AspaldYellow
                    )
                    Text(
                        text = "0 Reports Accepted",
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = FontFamily(Font(R.font.poppins_medium, FontWeight.Medium))
                    )
                }
            }
        }
    }
}