package com.aspald.aspald.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspald.aspald.data.model.User
import com.aspald.aspald.data.model.UserRequest
import com.aspald.aspald.data.repository.AuthRepository
import com.aspald.aspald.data.repository.UserRepository
import com.aspald.aspald.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    private val _userState = MutableStateFlow<UiState<String>>(UiState.Loading())
    val userState = _userState.asStateFlow()

    init {
        _user.value = authRepository.getSignedInUser()
    }

//    private fun getSignedInUser() {
//        viewModelScope.launch {
//            authRepository.getSignedInUser().collect {
//                _user.value = User(
//                    it.id,
//                    it.name,
//                    it.email,
//                    it.address,
//                    it.dob,
//                    it.phone
//                )
//            }
//        }
//    }

    fun onEvent(event: ProfileEvent) {
        when(event){
            is ProfileEvent.LogOut -> {
                authRepository.logOut()
            }

            is ProfileEvent.UpdateUser -> {
                updateUser(
                    event.userId,
                    event.userRequest
                )
            }

            is ProfileEvent.ResetState -> {
                _userState.value = UiState.Loading()
            }
        }
    }

    private fun updateUser(userId: String, request: UserRequest) {
        viewModelScope.launch {
            userRepository.updateUser(userId, request).collect {
                _userState.value = it
            }
        }
    }
}