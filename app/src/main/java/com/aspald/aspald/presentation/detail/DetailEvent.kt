package com.aspald.aspald.presentation.detail

sealed class DetailEvent {
    data class GetAddress(val lat: Double, val lng: Double): DetailEvent()
    data object ResetState : DetailEvent()
}