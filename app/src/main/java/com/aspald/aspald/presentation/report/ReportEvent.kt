package com.aspald.aspald.presentation.report

import java.io.File

sealed class ReportEvent {
    data class GetAddress(val lat: Double, val lng: Double): ReportEvent()
    data object GetLocation : ReportEvent()
    data object ResetState : ReportEvent()
    data class PostReport(val file: File, val description: String, val lat: Double, val lon: Double): ReportEvent()
}