package com.aura.enfocabita.presentation.configuration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.enfocabita.data.local.preferences.DarkThemePreferences
import com.aura.enfocabita.data.local.preferences.PreferenceManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ConfigurationViewModel(private val preferences: PreferenceManager) : ViewModel() {

    private val _notificacionesActivas = preferences.notificacionesActivas.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        true
    )

    val notificacionesActivas: StateFlow<Boolean> = _notificacionesActivas
    val isDarkMode = preferences.isDarkMode

    fun cambiarNotificacionesActivas(activo: Boolean) {
        viewModelScope.launch {
            preferences.setNotificacionesActivas(activo)
        }
    }

    fun cambiarModoOscuro(activo: Boolean) {
        viewModelScope.launch {
            preferences.guardarModoOscuro(activo)
        }
    }
}