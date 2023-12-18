package com.aspald.aspald.presentation.profile

import androidx.lifecycle.ViewModel
import com.aspald.aspald.data.model.User
import com.aspald.aspald.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    init {
        _user.value = authRepository.getSignedInUser()
    }

    fun onEvent(event: ProfileEvent) {
        when(event){
            is ProfileEvent.LogOut -> {
                authRepository.logOut()
            }
        }
    }
}