package com.aspald.aspald.presentation.profile.profileedit

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aspald.aspald.data.model.User
import com.aspald.aspald.data.model.UserRequest
import com.aspald.aspald.presentation.common.AspaldTopBar
import com.aspald.aspald.presentation.common.IconLessTextField
import com.aspald.aspald.presentation.common.LoadingScreen
import com.aspald.aspald.presentation.profile.ProfileEvent
import com.aspald.aspald.presentation.report.components.ConfirmButton
import com.aspald.aspald.utils.UiState
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ProfileEditScreen(
    user: User,
    state: UiState<String>,
    event: (ProfileEvent) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var isEditEnabled by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(user.name) }
    var address by remember { mutableStateOf(user.address) }
    var load by remember { mutableStateOf(false) }

    var dob = ""
    user.dateOfBirth?.let {
        dob = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(user.dateOfBirth)
    }
    var phone by remember { mutableStateOf(user.phone) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AspaldTopBar(topBarTitle = "Profile", onBackClick = onBackClick)
        Spacer(modifier = Modifier.height(70.dp))
        IconLessTextField(
            label = "Name",
            text = name ?: "",
            onEdit = {
                isEditEnabled = !isEditEnabled
                if (isEditEnabled) {
                    name = ""
                }
            },
            isEnabled = isEditEnabled,
            onValueChange = { name = it },
            onSearch = { }
        )
        Spacer(modifier = Modifier.height(25.dp))
        IconLessTextField(
            label = "Address",
            text = address ?: "...",
            onEdit = {
                isEditEnabled = !isEditEnabled
                if (isEditEnabled) {
                    address = ""
                }
            },
            isEnabled = isEditEnabled,
            onValueChange = { address = it },
            onSearch = { }
        )
        Spacer(modifier = Modifier.height(25.dp))
        IconLessTextField(
            label = "Date of Birth",
            text = dob,
            onValueChange = { },
            isEnabled = false,
            onSearch = { }
        )
        Spacer(modifier = Modifier.height(25.dp))
        IconLessTextField(
            label = "Phone",
            text = phone ?: "...",
            onValueChange = { phone = it },
            onEdit = {
                isEditEnabled = !isEditEnabled
                if (isEditEnabled) {
                    phone = ""
                }
            },
            keyboardType = KeyboardType.Phone,
            isEnabled = isEditEnabled,
            onSearch = { }
        )
        Spacer(modifier = Modifier.height(100.dp))
        ConfirmButton(
            modifier = Modifier
                .heightIn(70.dp, 90.dp)
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 10.dp),
            isEnabled = true,
            onClick = {
                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val text = "0000-00-00"
                val date = formatter.parse(text)
                if (name != null && address != null && phone != null) {
                    val request = UserRequest(
                        name,
                        address,
                        date,
                        phone
                    )

                    load = true
                    event(ProfileEvent.UpdateUser(user.id!!, request))
                }else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    with(state) {
        when (this) {
            is UiState.Error -> {
                load = false
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                LaunchedEffect(Unit) {
                    event(ProfileEvent.ResetState)
                }
            }
            is UiState.Success -> {
                load = false
                Toast.makeText(context, data, Toast.LENGTH_SHORT).show()
                onBackClick()
                LaunchedEffect(Unit) {
                    event(ProfileEvent.ResetState)
                }
            }
            else -> {
                if (load) {
                    LoadingScreen()
                }
            }
        }
    }
}