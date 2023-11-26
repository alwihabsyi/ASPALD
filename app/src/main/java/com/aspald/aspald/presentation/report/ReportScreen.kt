package com.aspald.aspald.presentation.report

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspald.aspald.R
import com.aspald.aspald.presentation.common.AspaldTopBar
import com.aspald.aspald.presentation.report.components.PhotoCard
import com.aspald.aspald.ui.theme.AspaldOrange
import com.aspald.aspald.ui.theme.AspaldYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    onBackClick: () -> Unit
) {
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AspaldTopBar(topBarTitle = "Mark a broken road", onBackClick = onBackClick)

            Column(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = stringResource(id = R.string.detail),
                    fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
                    fontSize = 22.sp
                )
                Text(
                    text = stringResource(id = R.string.provide_information),
                    fontFamily = FontFamily(Font(R.font.poppins, FontWeight.Light)),
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .heightIn(200.dp, 250.dp)
                        .fillMaxWidth(),
                    value = description,
                    onValueChange = { if (it.length < 255) description = it },
                    maxLines = 5,
                    placeholder = {
                        Text(
                            text = "Description",
                            color = Color.Gray,
                            fontFamily = FontFamily(Font(R.font.poppins_medium, FontWeight.Medium))
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.Transparent,
                        disabledTextColor = Color.Gray,
                        focusedBorderColor = AspaldYellow,
                        unfocusedBorderColor = AspaldYellow
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .heightIn(50.dp, 100.dp)
                        .fillMaxWidth(),
                    value = location,
                    onValueChange = { if (it.length < 100) location = it },
                    placeholder = {
                        Text(
                            text = "Address",
                            color = Color.Gray,
                            fontFamily = FontFamily(Font(R.font.poppins_medium, FontWeight.Medium))
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.Transparent,
                        disabledTextColor = Color.Gray,
                        focusedBorderColor = AspaldYellow,
                        unfocusedBorderColor = AspaldYellow,
                        disabledBorderColor = AspaldYellow
                    ),
                    trailingIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_point),
                                contentDescription = null,
                                tint = AspaldOrange
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier
                        .heightIn(100.dp, 150.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Button(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(10.dp),
                            shape = RoundedCornerShape(50.dp),
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = AspaldYellow
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_maps),
                                contentDescription = null,
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = stringResource(id = R.string.pin_location_point),
                                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                fontSize = 10.sp,
                                color = Color.Black
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = stringResource(id = R.string.road_photos),
                    fontFamily = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold)),
                    fontSize = 16.sp
                )
                Text(
                    text = stringResource(id = R.string.add_helpful_photos),
                    fontFamily = FontFamily(Font(R.font.poppins, FontWeight.Light)),
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(15.dp))

                Row {
                    val context = LocalContext.current
                    var bitmap by remember { mutableStateOf(false) }
                    PhotoCard {
                        Toast.makeText(context, "Ambil Foto", Toast.LENGTH_SHORT).show()
                        bitmap = true
                    }
                    if (bitmap) {
                        Spacer(modifier = Modifier.width(10.dp))
                        PhotoCard {
                            Toast.makeText(context, "Foto Baru", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        // TODO: Finish OnReport Click Function
        Button(
            onClick = { },
            modifier = Modifier
                .heightIn(70.dp, 90.dp)
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 10.dp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = AspaldYellow
            )
        ) {
            Text(
                text = "Confirm",
                fontFamily = FontFamily(Font(R.font.poppins_medium, FontWeight.Medium)),
                fontSize = 18.sp
            )
        }
    }
}