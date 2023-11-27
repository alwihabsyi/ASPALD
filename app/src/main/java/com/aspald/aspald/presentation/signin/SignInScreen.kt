package com.aspald.aspald.presentation.signin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aspald.aspald.R
import com.aspald.aspald.presentation.components.ButtonComponent
import com.aspald.aspald.presentation.components.MyEmailTextField
import com.aspald.aspald.presentation.components.MyTypography
import com.aspald.aspald.presentation.components.PasswordTextField
import com.aspald.aspald.presentation.navgraph.Route
import com.aspald.aspald.utils.UiState

@Composable
fun SignInScreen(
    navController: NavController,
    signInViewModel: SignInViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }

    val uiState by signInViewModel.uiState.collectAsState()

    // Navigate when sign in is successful
    LaunchedEffect(uiState) {
        if (uiState is UiState.Success) {
            navController.navigate(Route.HomeScreen.route) { // This should match the route defined in your NavHost
                popUpTo(Route.SignInScreen.route) { inclusive = true }
            }
        }
    }

    MaterialTheme(typography = MyTypography) {
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

            MyEmailTextField(
                labelValue = "Email",
                onValueChange = {
                    email = it
                    emailError = it.isBlank() // Example validation
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                errorStatus = emailError,
            )

            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(
                password = password,
                labelValue = "Password",
                onValueChange = { password = it },
                passwordVisibility = passwordVisibility,
                onTogglePasswordVisibility = { passwordVisibility = !passwordVisibility }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState is UiState.Error) {
                val errorMessage = (uiState as UiState.Error).message
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            ButtonComponent(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        signInViewModel.signIn(email, password)
                    } else {
                        emailError = email.isEmpty()
                        // You might want to handle password error similarly
                    }
                },
                buttonText = "Sign In"
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
                TextButton(onClick = {
                    navController.navigate(Route.SignUpScreen.route)
                }) {
                    Text(
                        text = "Sign up",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(id = R.color.primary)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(navController = NavController(LocalContext.current)) // You need to provide a NavController here.
}
