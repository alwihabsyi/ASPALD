package com.aspald.aspald.presentation.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aspald.aspald.R
import com.aspald.aspald.presentation.components.ButtonComponent
import com.aspald.aspald.presentation.components.MyTextField
import com.aspald.aspald.presentation.components.MyTypography
import com.aspald.aspald.presentation.components.PasswordTextField
import com.aspald.aspald.utils.UiState


@Composable
fun SignInScreen(navController: NavController,signInViewModel: SignInViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf(false) }

    val uiState by signInViewModel.uiState.collectAsState()

    when(uiState){
        is UiState.Loading -> {
            // Show a loading indicator
        }
        is UiState.Success -> {
            // Handle success, navigate to next screen or show a success message
        }
        is UiState.Error -> {
            // Show an error message
            val errorMessage = (uiState as UiState.Error).message
            // Update your UI with the error message
        }

        else -> {}
    }

    MaterialTheme(
        typography = MyTypography
    ) {
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

            MyTextField(
                labelValue = "email",
                onValueChange = {
                    email = it
                    emailError
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                errorStatus = emailError,
            )

            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(
                password = password,
                labelValue = "password",
                onValueChange = {password = it},
                passwordVisibility = passwordVisibility,
                onTogglePasswordVisibility = { passwordVisibility = !passwordVisibility })

            Text(
                text = "Forgot password?",
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 39.dp)
                    .align(Alignment.Start),
                style = MaterialTheme.typography.labelMedium,
                color = colorResource(id = R.color.primary)
            )

            ButtonComponent(onClick = { signInViewModel.signIn(email, password)}, buttonText = "Sign In")

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Don’t have an account?", style = MaterialTheme.typography.bodyMedium)
                TextButton(onClick = { /* TODO: Navigate to Sign Up */ }) {
                    Text("Sign up", style = MaterialTheme.typography.bodyMedium,color = colorResource(id = R.color.primary))
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginComposablePreview(){
    Surface(color = Color.White) {
      //  SignInScreen()
    }
}