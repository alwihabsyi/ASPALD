package com.aspald.aspald.presentation.signup


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aspald.aspald.R
import com.aspald.aspald.presentation.components.MyTextField
import com.aspald.aspald.presentation.components.MyTypography
import com.aspald.aspald.presentation.components.PasswordTextField
import com.aspald.aspald.presentation.signin.SignInScreen

@Composable
fun SignUpScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

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

            Button(
                onClick = {
                    // TODO: Handle sign up logic
                    //handleSignUp(email, password, confirmPassword)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary))
            ) {
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.titleMedium,
                    color = colorResource(id = R.color.secondary)
                )
            }

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

@Preview
@Composable
fun LoginComposablePreview(){
    Surface(color = Color.White) {
        SignUpScreen()
    }
}