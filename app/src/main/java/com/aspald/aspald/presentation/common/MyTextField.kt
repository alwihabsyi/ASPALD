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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(
    labelValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorStatus: Boolean = false,
) {
    var hasInteracted by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }

    val textValue = remember {
        mutableStateOf("")
    }

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
            value = textValue.value,
            onValueChange = {
                textValue.value = it
                onValueChange(it)

                // Name validation
                if (hasInteracted) {
                    errorText = if (it.isNotEmpty()) "" else "Name can't be empty"
                }
            },
            label = { Text(text = labelValue) },
            modifier = modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_message),
                    contentDescription = "Email Icon"
                )
            },
            isError = errorStatus || errorText.isNotEmpty(),
        )
        if (errorText.isNotEmpty()) {
            Text(
                text = errorText,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(start = 0.dp)
            )
        }
    }

    DisposableEffect(textValue.value) {
        hasInteracted = true
        onDispose { }
    }
}