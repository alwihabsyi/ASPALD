package com.aspald.aspald.presentation.profile.profileedit

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aspald.aspald.data.model.User
import com.aspald.aspald.presentation.common.AspaldTopBar
import com.aspald.aspald.presentation.common.IconLessTextField
import com.aspald.aspald.presentation.report.components.ConfirmButton
import com.aspald.aspald.presentation.report.postReport
import com.google.android.gms.maps.model.LatLng
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ProfileEditScreen(
    user: User,
    onBackClick: () -> Unit
) {
    var isEditEnabled by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(user.name) }
    var address by remember { mutableStateOf(user.address) }

    var dob = ""
    user.dateOfBirth?.let {
        dob = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(user.dateOfBirth)
    }

    var ph = ""
    user.phone?.let {
        ph = it.toString()
    }
    var phone by remember { mutableStateOf(ph) }

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
            onEdit = {
                isEditEnabled = !isEditEnabled
            },
            isEnabled = isEditEnabled,
            onSearch = { }
        )
        Spacer(modifier = Modifier.height(25.dp))
        IconLessTextField(
            label = "Phone",
            text = phone,
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
                if (name != null && address != null && phone.isNotEmpty()) {

                }
            }
        )
    }
}