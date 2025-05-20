package com.aura.enfocabita.presentation.pomodoro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.enfocabita.data.local.database.entidades.PomodoroSesion
import com.aura.enfocabita.domain.usecase.pomodoro.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.aura.enfocabita.domain.usecase.pomodoro.PomodoroFase

class PomodoroViewModel(
    private val createSession: CreatePomodoroSessionUseCase,
    private val getSessions: GetPomodoroSessionsByUserUseCase,
    private val updateSession: UpdatePomodoroSessionUseCase,
    private val deleteSession: DeletePomodoroSessionUseCase,
    private val getSessionById: GetPomodoroSessionByIdUseCase
) : ViewModel() {

    private val _sesiones = MutableStateFlow<List<PomodoroSesion>>(emptyList())
    val sesiones: StateFlow<List<PomodoroSesion>> = _sesiones

    private val _mensajeError = MutableSharedFlow<String>()
    val mensajeError: SharedFlow<String> = _mensajeError

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    var faseActual by mutableStateOf<PomodoroFase>(PomodoroFase.Trabajo(1))


    fun observarSesiones(userId: Long) {
        viewModelScope.launch {
            getSessions(userId).collect {
                _sesiones.value = it
            }
        }
    }

    fun crearSesion(sesion: PomodoroSesion) {
        viewModelScope.launch {
            try {
                createSession(sesion)
                _mensajeError.emit("Sesión guardada")
            } catch (e: Exception) {
                _error.emit("Error al crear sesión: ${e.message}")
            }
        }
    }

    fun actualizarSesion(sesion: PomodoroSesion) {
        viewModelScope.launch {
            try {
                updateSession(sesion)
                _mensajeError.emit("Sesión actualizada")
            } catch (e: Exception) {
                _error.emit("Error al actualizar sesión: ${e.message}")
            }
        }
    }

    fun eliminarSesion(id: Long) {
        viewModelScope.launch {
            try {
                deleteSession(id)
                _mensajeError.emit("Sesión eliminada")
            } catch (e: Exception) {
                _error.emit("Error al eliminar sesión: ${e.message}")
            }
        }
    }

    suspend fun cargarSesionPorId(id: Long): PomodoroSesion? {
        return getSessionById(id)
    }

}