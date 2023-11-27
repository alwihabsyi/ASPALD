package com.aspald.aspald

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspald.aspald.utils.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferences: UserPreferences
): ViewModel() {
    val initialRoute = userPreferences.getUserKey().map { token ->
        if (token.isEmpty()) "signIn" else "home"
    }.stateIn(viewModelScope, SharingStarted.Lazily, "signIn" )
}