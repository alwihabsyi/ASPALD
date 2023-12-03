package com.aspald.aspald.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspald.aspald.R
import com.aspald.aspald.utils.isPasswordValid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    password: String,
    labelValue: String,
    onValueChange: (Boolean, String) -> Unit,
    passwordVisibility: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    modifier: Modifier = Modifier
) {
    var hasInteracted by remember { mutableStateOf(false) }

    val isPasswordValid = if (hasInteracted) {
        isPasswordValid(password)
    } else {
        true
    }

    val errorText = remember { mutableStateOf("") }

    if (hasInteracted) {
        errorText.value =
            if (isPasswordValid) ""
            else "Password must contain at least 8 characters."
    }

    Column {
        OutlinedTextField(
            shape = RoundedCornerShape(12.dp),
            value = password,
            onValueChange = { newPass ->
                onValueChange(isPasswordValid, newPass)
                hasInteracted = true
            },
            label = { Text(labelValue) },
            modifier = modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock_xml),
                    contentDescription = "Password Icon"
                )
            },
            trailingIcon = {
                val image = if (passwordVisibility)
                    R.drawable.ic_show
                else
                    R.drawable.ic_hide

                IconButton(onClick = onTogglePasswordVisibility) {
                    Icon(
                        painter = painterResource(id = image),
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            isError = !isPasswordValid,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (isPasswordValid) Color.Black else Color.Red,
                focusedLabelColor = if (isPasswordValid) Color.Black else Color.Red,
                cursorColor = if (isPasswordValid) Color.Black else Color.Red,
            )
        )

        if (errorText.value.isNotEmpty()) {
            Text(
                text = errorText.value,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}