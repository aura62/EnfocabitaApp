package com.aura.enfocabita.presentation.habit

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.enfocabita.data.local.database.DAO.ProgresoHabitoDiarioDao
import com.aura.enfocabita.data.local.database.entidades.Habito
import com.aura.enfocabita.data.local.database.entidades.ProgresoHabitoDiario
import com.aura.enfocabita.domain.usecase.habit.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de gestionar la lógica de la pantalla de hábitos:
 * creación, edición, eliminación y observación reactiva de los hábitos del usuario.
 */

class HabitViewModel(
    private val createHabitUseCase: CreateHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val getHabitsByUserUseCase: GetHabitsByUserUseCase,
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
    private val toggleHabitCompletionUseCase: ToggleHabitCompletionUseCase

) : ViewModel() {

    private val _habitos = MutableStateFlow<List<Habito>>(emptyList())
    val habitos: StateFlow<List<Habito>> = _habitos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _mensaje = MutableSharedFlow<String>()
    val mensaje: SharedFlow<String> = _mensaje

    private val _mensajeError = MutableSharedFlow<String>()
    val mensajeError: SharedFlow<String> = _mensajeError

    fun observarHabitos(userId: Long) {
        viewModelScope.launch {
            getHabitsByUserUseCase(userId).collect {
                _habitos.value = it
            }
        }
    }

    fun crearHabito(userId: Long, titulo: String, tipo: String, frecuencia: Int, periodo: String) {
        viewModelScope.launch {
            try {
                createHabitUseCase(userId, titulo, tipo, frecuencia, periodo)
                _mensaje.emit("Hábito creado con éxito")
            } catch (e: Exception) {
                _mensajeError.emit("Error al crear hábito: ${e.message}")
            }
        }
    }

    fun actualizarHabito(habito: Habito) {
        viewModelScope.launch {
            try {
                updateHabitUseCase(habito)
                _mensaje.emit("Hábito actualizado")
            } catch (e: Exception) {
                _mensajeError.emit("Error al actualizar hábito: ${e.message}")
            }
        }
    }

    fun eliminarHabito(habitoId: Long) {
        viewModelScope.launch {
            try {
                deleteHabitUseCase(habitoId)
                _mensaje.emit("Hábito eliminado")
            } catch (e: Exception) {
                _mensajeError.emit("Error al eliminar hábito: ${e.message}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun estaCompletadoHoy(idHabito: Long): Boolean {
        return toggleHabitCompletionUseCase.obtenerEstadoActual(idHabito)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun actualizarEstadoHabito(habitId: Long, completado: Boolean) {
        viewModelScope.launch {
            try {
                toggleHabitCompletionUseCase.actualizarEstado(habitId, completado)
                _mensaje.emit("Estado del hábito actualizado")
            } catch (e: Exception) {
                _mensajeError.emit("Error al actualizar el estado del hábito: ${e.message}")
            }
        }
    }

    suspend fun cargarHabitoPorId(id: Long): Habito? {
        return getHabitByIdUseCase(id)
    }


}
