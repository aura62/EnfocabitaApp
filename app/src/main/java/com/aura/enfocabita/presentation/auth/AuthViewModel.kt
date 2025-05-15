package com.aura.enfocabita.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.enfocabita.data.local.database.entidades.Usuario
import com.aura.enfocabita.data.repository.UsuarioRepository
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

class AuthViewModel(
    private val registerUser: RegisterUser,
    private val loginUser   : LoginUser
) : ViewModel() {

    // Estado de la UI
    private val _uiState = MutableStateFlow<AuthState>(AuthState.Idle)
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    // Eventos únicos (ej. navegación)
    private val _events = MutableSharedFlow<AuthEvent>()
    val events: SharedFlow<AuthEvent> = _events.asSharedFlow()

    /** Inicia el registro de un nuevo usuario. */
    fun register(nombre: String, correo: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            try {
                val id = registerUser(nombre.trim(), correo.trim(), password)

                _uiState.value = AuthState.Registered(id)
                _events.emit(AuthEvent.NavigateToHome)
            } catch (e: AuthException) {
                _uiState.value = AuthState.Error(e.message ?: "Error de autenticación")
            } catch (t: Throwable) {
                _uiState.value = AuthState.Error("Error inesperado")
            }
        }
    }

    /** Inicia el proceso de login de un usuario existente. */
    fun login(correo: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            try {
                val user = loginUser(correo.trim(), password)
                if (user != null) {
                    _uiState.value = AuthState.LoggedIn(user)
                    _events.emit(AuthEvent.NavigateToHome)
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