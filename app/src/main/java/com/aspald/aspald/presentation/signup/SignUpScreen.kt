package com.aspald.aspald.presentation.signup


import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.aspald.aspald.presentation.components.MyTextField
import com.aspald.aspald.presentation.components.MyTypography
import com.aspald.aspald.presentation.components.PasswordTextField
import com.aspald.aspald.presentation.navgraph.Route
import com.aspald.aspald.utils.UiState

@Composable
fun SignUpScreen(navController: NavController, signUpViewModel: SignUpViewModel = hiltViewModel()) {
    var name by remember{ mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    val uiState by signUpViewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(uiState){
        if (uiState is UiState.Success){
            navController.navigate(Route.SignInScreen.route){
                popUpTo(Route.SignUpScreen.route) { inclusive = true}
            }
        }
    }

    MaterialTheme(
        typography = MyTypography
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
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

            MyEmailTextField(
                labelValue = "email",
                onValueChange = {
                    email = it
                    emailError = false // Reset error when user changes the email
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
                onValueChange = {
                    password = it
                    passwordError = false // Reset error when user changes the password
                },
                passwordVisibility = passwordVisibility,
                onTogglePasswordVisibility = { passwordVisibility = !passwordVisibility }
            )

            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(
                password = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    passwordError = false // Reset error when user changes the confirmation password
                },
                passwordVisibility = confirmPasswordVisibility,
                onTogglePasswordVisibility = { confirmPasswordVisibility = !confirmPasswordVisibility },
                labelValue = "re-enter password",
                modifier = Modifier.padding(bottom = 39.dp)

            )


            ButtonComponent(
                onClick = {
                    if (password == confirmPassword){
                        Log.d("Coba", "$name, $email, $password, $confirmPassword")
                        signUpViewModel.signUp(name, email, password, confirmPassword)
                    }else {
                        emailError = email.isEmpty()
                        // You might want to handle password error similarly
                    }
                },
                buttonText = "Sign Up"
            )


            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Already have an account?", style = MaterialTheme.typography.bodyMedium)
                TextButton(onClick = { /* TODO: Navigate to Sign In */ }) {
                    Text("Sign in", style = MaterialTheme.typography.bodyMedium, color = colorResource(id = R.color.primary))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = NavController(LocalContext.current)) // You need to provide a NavController here.
}