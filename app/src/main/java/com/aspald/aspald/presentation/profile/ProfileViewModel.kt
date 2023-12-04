package com.aspald.aspald.presentation.profile

import androidx.lifecycle.ViewModel
import com.aspald.aspald.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth
): ViewModel() {
    private val _state = MutableStateFlow<UiState<String>>(UiState.Loading())
    val state = _state.asStateFlow()

    fun onEvent(event: ProfileEvent) {
        when(event){
            is ProfileEvent.LogOut -> {
                auth.signOut()
                _state.value = UiState.Success("Logged out")
            }
        }
    }
}