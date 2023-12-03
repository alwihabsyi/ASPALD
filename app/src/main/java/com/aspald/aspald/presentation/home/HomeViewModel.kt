package com.aspald.aspald.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspald.aspald.presentation.navgraph.Route
import com.aspald.aspald.utils.GetLocationUseCase
import com.aspald.aspald.utils.PermissionEvent
import com.aspald.aspald.utils.MapState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase, auth: FirebaseAuth
) : ViewModel() {
    private val _state: MutableStateFlow<MapState> = MutableStateFlow(MapState.Loading)
    val state = _state.asStateFlow()

    var startDestination by mutableStateOf(Route.AuthNavigator.route)

    init {
        viewModelScope.launch {
            startDestination =
                if (auth.currentUser != null) Route.HomeNavigator.route else Route.AuthNavigator.route
        }
    }

    fun handle(event: PermissionEvent) {
        when (event) {
            PermissionEvent.Granted -> {
                viewModelScope.launch {
                    getLocationUseCase.invoke().collect {
                        _state.value = MapState.Success(it)
                    }
                }
            }

            PermissionEvent.Revoked -> {
                _state.value = MapState.RevokedPermissions
            }
        }
    }
}