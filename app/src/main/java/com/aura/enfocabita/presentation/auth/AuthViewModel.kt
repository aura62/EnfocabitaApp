package com.aura.enfocabita.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.enfocabita.domain.error.AuthException
import com.aura.enfocabita.domain.usecase.LoginUser
import com.aura.enfocabita.domain.usecase.RegisterUser
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsable de manejar el proceso de autenticación.
 * Gestiona tanto el registro como el login y emite estados y eventos
 * para ser consumidos por la UI (pantallas de login y registro).
 */

class AuthViewModel(
    private val registerUser: RegisterUser,
    private val loginUser   : LoginUser
) : ViewModel() {

    //Estado observable de la UI (ej. cargando, error, éxito, etc.)
    private val _uiState = MutableStateFlow<AuthState>(AuthState.Idle)
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    // Eventos únicos de navegación o notificación
    private val _events = MutableSharedFlow<AuthEvent>()
    val events: SharedFlow<AuthEvent> = _events.asSharedFlow()

    /**
     * Inicia el registro de un nuevo usuario.
     * En caso de éxito, emite el evento de navegación a Home.
     */
    fun register(nombre: String, correo: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            try {
                val id = registerUser(nombre.trim(), correo.trim(), password)
                _uiState.value = AuthState.Registered(id)
                _events.emit(AuthEvent.NavigateToHome(userId = id))
            } catch (e: AuthException) {
                _uiState.value = AuthState.Error(e.message ?: "Error de autenticación")
            } catch (t: Throwable) {
                _uiState.value = AuthState.Error("Error inesperado")
            }
        }
    }

    /**
     * Inicia el login del usuario existente.
     * Si las credenciales son válidas, emite evento de navegación a Home.
     */
    fun login(correo: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            try {
                val user = loginUser(correo.trim(), password)
                if (user != null) {
                    _uiState.value = AuthState.LoggedIn(user)
                    _events.emit(AuthEvent.NavigateToHome(userId = user.idUsuario))
                } else {
                    _uiState.value = AuthState.InvalidCredentials
                }
            } catch (e: AuthException) {
                _uiState.value = AuthState.Error(e.message ?: "Error de autenticación")
            } catch (t: Throwable) {
                _uiState.value = AuthState.Error("Error inesperado")
            }
        }
    }
}