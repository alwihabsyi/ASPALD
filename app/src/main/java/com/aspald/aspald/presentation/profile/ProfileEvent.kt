package com.aspald.aspald.presentation.profile

sealed class ProfileEvent {
    data object LogOut : ProfileEvent()
}