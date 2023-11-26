package com.aspald.aspald.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspald.aspald.R

@Composable
fun HistoryCard(
    date: String,
    streetName: String,
    isLast: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .width(24.dp),
                painter = painterResource(id = R.drawable.ic_note),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = date,
                    style = TextStyle.Default.copy(
                        fontFamily = FontFamily(
                            Font(
                                R.font.poppins,
                                FontWeight.Normal
                            )
                        ),
                        fontSize = 12.sp
                    )
                )
                Text(text = streetName, style = TextStyle.Default.copy(
                    fontFamily = FontFamily(
                        Font(
                            R.font.poppins_semibold,
                            FontWeight.Medium
                        )
                    ),
                    fontSize = 16.sp
                ))
                Text(
                    text = "Report",
                    style = TextStyle.Default.copy(
                        fontFamily = FontFamily(
                            Font(
                                R.font.poppins,
                                FontWeight.Normal
                            )
                        ),
                        fontSize = 14.sp
                    )
                )
            }
            IconButton(
                modifier = Modifier
                    .width(24.dp),
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_three_dots),
                        contentDescription = null
                    )
                },
                onClick = {  }
            )
        }
        if (!isLast) {
            Spacer(modifier = Modifier.height(5.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Color.Gray)
            )
        }
    }
}