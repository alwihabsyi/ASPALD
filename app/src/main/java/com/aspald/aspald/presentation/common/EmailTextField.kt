package com.aspald.aspald.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspald.aspald.R
import com.aspald.aspald.utils.isValidEmail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(
    labelValue: String,
    onValueChange: (Boolean, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var hasInteracted by remember { mutableStateOf(false) }
    var textValue by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf("") }

    val isEmailValid = isValidEmail(textValue)
    val errorStatus = textValue.isNotEmpty() && !isEmailValid
    errorText =
        if (!errorStatus) ""
        else "Enter a valid email address"

    Column {
        OutlinedTextField(
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (errorStatus) Color.Red else colorResource(id = R.color.grey),
                focusedLabelColor = if (errorStatus) Color.Red else colorResource(id = R.color.grey),
                cursorColor = if (errorStatus) Color.Red else colorResource(id = R.color.grey),
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine = true,
            maxLines = 1,
            value = textValue,
            onValueChange = {
                textValue = it
                onValueChange(isEmailValid, it)
                hasInteracted = true
            },
            label = { Text(text = labelValue) },
            modifier = modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_message),
                    contentDescription = "Email Icon"
                )
            },
            isError = errorStatus,
        )
        if (errorText.isNotEmpty()) {
            Text(
                text = errorText,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 0.dp)
            )
        }
    }

    DisposableEffect(textValue) {
        hasInteracted = true
        onDispose { }
    }
}