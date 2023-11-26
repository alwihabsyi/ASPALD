package com.aspald.aspald.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspald.aspald.utils.GetLocationUseCase
import com.aspald.aspald.utils.PermissionEvent
import com.aspald.aspald.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase
): ViewModel() {
    private val _state: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val state = _state.asStateFlow()

    fun handle(event: PermissionEvent) {
        when(event) {
            PermissionEvent.Granted -> {
                viewModelScope.launch {
                    getLocationUseCase.invoke().collect {
                        _state.value = UiState.Success(it)
                    }
                }
            }
            PermissionEvent.Revoked -> {
                _state.value = UiState.RevokedPermissions
            }
        }
    }
}