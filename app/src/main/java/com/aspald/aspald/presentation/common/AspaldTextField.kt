package com.aspald.aspald.presentation.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspald.aspald.R
import com.aspald.aspald.ui.theme.AspaldOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AspaldTextField(
    label: String,
    text: String,
    icon: Int,
    isEditable: Boolean = false,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        value = text,
        onValueChange = onValueChange,
        textStyle = TextStyle.Default.copy(
            fontFamily = FontFamily(
                Font(
                    R.font.poppins_medium,
                    FontWeight.Medium
                )
            ),
            fontSize = 16.sp
        ),
        label = {
            Text(
                text = label,
                fontFamily = FontFamily(Font(R.font.poppins, FontWeight.Normal))
            )
        },
        leadingIcon = {
            Icon(painter = painterResource(id = icon), contentDescription = null)
        },
        trailingIcon = {
            if (isEditable) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = null,
                    tint = AspaldOrange
                )
            }
        },
        enabled = false,
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch()
        }),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            disabledTextColor = Color.Black,
            disabledTrailingIconColor = Color.Black,
            disabledLeadingIconColor = Color.Black,
            disabledLabelColor = Color.Black
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconLessTextField(
    label: String,
    text: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        value = text,
        onValueChange = onValueChange,
        textStyle = TextStyle.Default.copy(
            fontFamily = FontFamily(
                Font(
                    R.font.poppins_medium,
                    FontWeight.Medium
                )
            ),
            fontSize = 16.sp
        ),
        enabled = false,
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch()
        }),
        decorationBox = {
            TextFieldDefaults.TextFieldDecorationBox(
                value = text,
                enabled = false,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                innerTextField = {
                    Text(
                        modifier = Modifier.padding(top = 5.dp),
                        text = text,
                        style = TextStyle.Default.copy(
                            fontFamily = FontFamily(
                                Font(
                                    R.font.poppins_medium,
                                    FontWeight.Medium
                                )
                            ),
                            fontSize = 16.sp
                        )
                    )
                },
                interactionSource = interactionSource,
                label = {
                    Text(
                        text = label,
                        fontFamily = FontFamily(Font(R.font.poppins, FontWeight.Normal))
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = null,
                        tint = AspaldOrange
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    disabledTextColor = Color.Black,
                    disabledTrailingIconColor = Color.Black,
                    disabledLeadingIconColor = Color.Black,
                    disabledLabelColor = Color.Black
                ),
                contentPadding = PaddingValues(0.dp)
            )
        }
    )
}