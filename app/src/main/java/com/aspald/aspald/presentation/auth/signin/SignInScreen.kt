package com.aspald.aspald.presentation.auth.signin

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.aspald.aspald.R
import com.aspald.aspald.presentation.auth.AuthEvent
import com.aspald.aspald.presentation.common.ButtonComponent
import com.aspald.aspald.presentation.common.EmailTextField
import com.aspald.aspald.presentation.common.LoadingScreen
import com.aspald.aspald.presentation.common.PasswordTextField
import com.aspald.aspald.utils.UiState

@Composable
fun SignInScreen(
    event: (AuthEvent) -> Unit,
    onSignUpClick: () -> Unit,
    state: UiState<String>,
    navigateToHome: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var load by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(false) }
    val isFieldEmpty =
        email.isNotEmpty() && password.isNotEmpty() && isEmailValid && isPasswordValid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(160.dp))

        Column(
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = "Sign In",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = "Please sign in to continue",
                style = MaterialTheme.typography.labelLarge,
                color = colorResource(id = R.color.grey)
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        EmailTextField(
            labelValue = "Email",
            onValueChange = { isValid, it ->
                email = it
                isEmailValid = isValid
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextField(
            password = password,
            labelValue = "Password",
            onValueChange = { isPwValid, pw ->
                isPasswordValid = isPwValid
                password = pw
            },
            passwordVisibility = passwordVisibility,
            onTogglePasswordVisibility = { passwordVisibility = !passwordVisibility }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ButtonComponent(
            onClick = {
                load = true
                event(AuthEvent.SignIn(email, password))
            },
            buttonText = "Sign In",
            isEnabled = isFieldEmpty
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don’t have an account?",
                style = MaterialTheme.typography.bodyMedium
            )
            TextButton(
                onClick = onSignUpClick
            ) {
                Text(
                    text = "Sign up",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.primary)
                )
            }
        }
    }

    when (state) {
        is UiState.Success -> {
            navigateToHome()
        }

        is UiState.Error -> {
            load = false
            val context = LocalContext.current
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }

        else -> {
            if (load) {
                LoadingScreen()
            }
        }
    }
}
