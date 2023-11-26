package com.aspald.aspald.utils

sealed interface PermissionEvent {
    data object Granted: PermissionEvent
    data object Revoked: PermissionEvent
}