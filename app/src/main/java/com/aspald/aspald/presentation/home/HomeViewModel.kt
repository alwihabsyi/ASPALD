package com.aspald.aspald.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspald.aspald.data.model.Report
import com.aspald.aspald.data.repository.AuthRepository
import com.aspald.aspald.data.repository.ReportRepository
import com.aspald.aspald.presentation.navgraph.Route
import com.aspald.aspald.utils.GetLocationUseCase
import com.aspald.aspald.utils.MapState
import com.aspald.aspald.utils.PermissionEvent
import com.aspald.aspald.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase,
    private val authRepository: AuthRepository,
    private val reportRepository: ReportRepository
) : ViewModel() {
    private val _state: MutableStateFlow<MapState> = MutableStateFlow(MapState.Loading)
    val state = _state.asStateFlow()

    private val _reportList = MutableStateFlow<List<Report>>(listOf())
    val reports = _reportList.asStateFlow()

    var startDestination by mutableStateOf(Route.AuthNavigator.route)

    init {
        viewModelScope.launch {
            startDestination =
                if (authRepository.isUserSignedIn()) Route.HomeNavigator.route else Route.AuthNavigator.route
        }
    }

    fun handle(event: PermissionEvent) {
        when (event) {
            PermissionEvent.Granted -> {
                viewModelScope.launch {
                    getLocationUseCase.invoke().collect {
                        _state.value = MapState.Success(it)
                    }
                    getAllReports()
                }
            }

            PermissionEvent.Revoked -> {
                _state.value = MapState.RevokedPermissions
            }
        }
    }

    fun getAllReports() {
        viewModelScope.launch {
            reportRepository.getAllReport().collect {
                when(it){
                    is UiState.Success -> {
                        it.data?.let { reports ->
                            _reportList.value = reports
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}