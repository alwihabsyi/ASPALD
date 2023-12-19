package com.aspald.aspald.presentation.detail

import android.location.Geocoder
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspald.aspald.data.model.Report
import com.aspald.aspald.data.repository.ReportRepository
import com.aspald.aspald.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("DEPRECATION")
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val geocoder: Geocoder,
    private val reportRepository: ReportRepository
): ViewModel() {
    private val _report = MutableStateFlow<UiState<Report>>(UiState.Loading())
    val report = _report.asStateFlow()

    private val _address = MutableStateFlow("")
    val address = _address.asStateFlow()

    fun onEvent(event: DetailEvent) {
        when (event) {
            DetailEvent.ResetState -> {

            }
            is DetailEvent.GetAddress -> {
                getMarkerAddressDetails(event.lat, event.lng)
            }
        }
    }

    fun getReport(id: String) {
        viewModelScope.launch {
            reportRepository.getReportById(id).collect {
                _report.value = it
            }
        }
    }

    private fun getMarkerAddressDetails(lat: Double, long: Double) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(
                    lat,
                    long,
                    1,
                ) { p0 ->
                    _address.value = p0[0].getAddressLine(0)
                }
            } else {
                val addresses = geocoder.getFromLocation(
                    lat,
                    long,
                    1,
                )
                _address.value =
                    if(!addresses.isNullOrEmpty()){
                        addresses[0].getAddressLine(0)
                    }else{
                        "Address is null"
                    }
            }
        } catch (e: Exception) {
            _address.value = e.message ?: "An unknown error occurred"
        }
    }
}