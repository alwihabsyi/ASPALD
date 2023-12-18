package com.aspald.aspald.presentation.auth.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.aspald.aspald.presentation.common.MyTextField
import com.aspald.aspald.presentation.common.PasswordTextField
import com.aspald.aspald.utils.UiState
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction1

@Composable
fun SignUpScreen(
    event: KSuspendFunction1<AuthEvent, Unit>,
    state: UiState<String>,
    onSignInClick: () -> Unit
) {
    var name by remember{ mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }
    var load by remember { mutableStateOf(false) }

    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordValid by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(100.dp))

        Column(
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = "Create your account",
                style = MaterialTheme.typography.labelLarge,
                color = colorResource(id = R.color.grey)
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        MyTextField(
            labelValue = "name",
            onValueChange = {
                name = it
                nameError = false // Reset error when user changes the email
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            errorStatus = nameError,
        )

        Spacer(modifier = Modifier.height(8.dp))

        EmailTextField(
            labelValue = "email",
            onValueChange = { isEmailValid, it ->
                email = it
                emailError = isEmailValid // Reset error when user changes the email
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextField(
            password = password,
            labelValue = "password",
            onValueChange = { isPasswordValid, pw ->
                password = pw
                passwordValid = isPasswordValid // Reset error when user changes the password
            },
            passwordVisibility = passwordVisibility,
            onTogglePasswordVisibility = { passwordVisibility = !passwordVisibility }
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextField(
            password = confirmPassword,
            onValueChange = { isPasswordValid, pw ->
                confirmPassword = pw
                passwordValid = isPasswordValid
            },
            passwordVisibility = confirmPasswordVisibility,
            onTogglePasswordVisibility = { confirmPasswordVisibility = !confirmPasswordVisibility },
            labelValue = "re-enter password",
            modifier = Modifier.padding(bottom = 39.dp)
        )

        ButtonComponent(
            onClick = {
                coroutineScope.launch { event(AuthEvent.SignUp(name, email, password)) }
                load = true
            },
            buttonText = "Sign Up",
            isEnabled = email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && password == confirmPassword
        )


        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Already have an account?", style = MaterialTheme.typography.bodyMedium)
            TextButton(onClick = onSignInClick) {
                Text("Sign in", style = MaterialTheme.typography.bodyMedium, color = colorResource(id = R.color.primary))
            }
        }
    }

    val context = LocalContext.current
    when (state) {
        is UiState.Success -> {
            load = false
            onSignInClick()
            Toast.makeText(context, "Sign up success, please sign in", Toast.LENGTH_SHORT).show()
            LaunchedEffect(Unit) {
                event(AuthEvent.ResetState)
            }
        }

        is UiState.Error -> {
            load = false
            email = ""
            password = ""
            confirmPassword = ""
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            LaunchedEffect(Unit) {
                event(AuthEvent.ResetState)
            }
        }

        else -> {
            if (load) {
                LoadingScreen()
            }
        }
    }
}
