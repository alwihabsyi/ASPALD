package com.aspald.aspald.presentation.report

import android.location.Geocoder
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspald.aspald.data.repository.ReportRepository
import com.aspald.aspald.utils.GetLocationUseCase
import com.aspald.aspald.utils.MapState
import com.aspald.aspald.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("DEPRECATION")
@HiltViewModel
class ReportViewModel @Inject constructor(
    private val geocoder: Geocoder,
    private val getLocationUseCase: GetLocationUseCase,
    private val reportRepository: ReportRepository
): ViewModel() {
    private val _address = MutableStateFlow("")
    val address = _address.asStateFlow()

    private val _currentLocation: MutableStateFlow<MapState> = MutableStateFlow(MapState.Loading)
    val currentLocation = _currentLocation.asStateFlow()

    private val _postReportState = MutableStateFlow<UiState<String>>(UiState.Loading())
    val postReportState = _postReportState.asStateFlow()

    init {
        onEvent(ReportEvent.GetLocation)
    }

    fun onEvent(event: ReportEvent) {
        when (event) {
            ReportEvent.ResetState -> {

            }
            is ReportEvent.GetAddress -> {
                getMarkerAddressDetails(event.lat, event.lng)
            }
            is ReportEvent.GetLocation -> {
                viewModelScope.launch {
                    getLocationUseCase.invoke().collect {
                        _currentLocation.value = MapState.Success(it)
                    }
                }
            }
            is ReportEvent.PostReport -> {
                viewModelScope.launch {
                    reportRepository.postReport(event.reportRequest).collect {
                        _postReportState.value = it
                    }
                }
            }
        }
    }

    private fun getMarkerAddressDetails(lat: Double, long: Double) {
        try {
            if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(//Pass LatLng and get address
                    lat,
                    long,
                    1,//no. of addresses you want
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
                    if(!addresses.isNullOrEmpty()){//The address can be null or empty
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