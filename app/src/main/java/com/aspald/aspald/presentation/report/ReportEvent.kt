package com.aspald.aspald.presentation.report

import com.aspald.aspald.data.model.ReportRequest

sealed class ReportEvent {
    data class GetAddress(val lat: Double, val lng: Double): ReportEvent()
    data object GetLocation : ReportEvent()
    data object ResetState : ReportEvent()
    data class PostReport(val reportRequest: ReportRequest): ReportEvent()
}