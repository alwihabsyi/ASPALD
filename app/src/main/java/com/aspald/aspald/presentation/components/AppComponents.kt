package com.aspald.aspald.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspald.aspald.R
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import java.util.regex.Pattern

@Composable
fun NormalTextComponent(value: String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ), color = Color.Black,
        textAlign = TextAlign.Center
    )
}

val MyFontFamily = FontFamily(
    Font(R.font.poppins_bold, weight = FontWeight.Bold),
    Font(R.font.poppins_medium, weight = FontWeight.Medium),
    Font(R.font.poppins_regular, weight = FontWeight.Normal),
    Font(R.font.poppins_bold_talic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.poppins_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.poppins_semi_bold, weight = FontWeight.SemiBold),
    Font(R.font.poppins_light, weight = FontWeight.Light)
)

val MyTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 20.sp,
        letterSpacing = 0.4.sp
    ),
    labelMedium = TextStyle(
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        letterSpacing = 0.4.sp
    ),
)

@Composable
fun HeadingTextComponent(value: String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ), color = Color.Black,
        textAlign = TextAlign.Center
    )
}


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

                // Email validation
                if (hasInteracted) {
                    errorText = if (isValidEmail(it)) "" else "Enter a valid email address"
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


private fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("^\\S+@\\S+\\.\\S+\$")
    return emailRegex.matches(email)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    password: String,
    labelValue: String,
    onValueChange: (String) -> Unit,
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

    var errorText = remember { mutableStateOf("") }

    if (hasInteracted) {
        errorText.value =
            if (isPasswordValid) ""
            else "Password must contain at least 8 characters, 1 lowercase, 1 uppercase, and 1 special character."
    }

    Column {
        OutlinedTextField(
            shape = RoundedCornerShape(12.dp),
            value = password,
            onValueChange = { newPass ->
                onValueChange(newPass)
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

private fun isPasswordValid(password: String): Boolean {
    val pattern = Pattern.compile(
        "(?=.*[a-z])" +        // at least 1 lowercase character
                "(?=.*[A-Z])" +        // at least 1 uppercase character
                "(?=.*[@#$%^&+=])" +   // at least 1 special character
                "(?=\\S+$)" +          // no whitespace allowed
                ".{8,}"                // at least 8 characters
    )
    return pattern.matcher(password).matches()
}


@Composable
fun ButtonComponent(
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary))
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.titleMedium,
            color = colorResource(id = R.color.secondary),
        )
    }
}
