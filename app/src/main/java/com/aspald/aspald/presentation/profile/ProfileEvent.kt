package com.aspald.aspald.presentation.profile

import com.aspald.aspald.data.model.UserRequest

sealed class ProfileEvent {
    data object LogOut : ProfileEvent()
    data class UpdateUser(val userId: String, val userRequest: UserRequest): ProfileEvent()
    data object ResetState: ProfileEvent()
}