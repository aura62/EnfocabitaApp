package com.aura.enfocabita.presentation.inicio

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.enfocabita.data.repository.UsuarioRepository
import com.aura.enfocabita.domain.usecase.inicio.GetCurrentStreakUseCase
import com.aura.enfocabita.domain.usecase.inicio.GetLastActivityDateUseCase
import com.aura.enfocabita.domain.usecase.inicio.GetTodayHabitProgressUseCase
import com.aura.enfocabita.domain.usecase.inicio.GetTodayPomodoroTimeUseCase
import com.aura.enfocabita.utils.FrasesMotivacionales
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

/**
 * ViewModel para la pantalla de Inicio.
 *
 * Se encarga de consultar y mantener el estado del resumen diario del usuario,
 * incluyendo hábitos completados, tiempo Pomodoro y fecha de última actividad.
 *
 * @param getTodayHabitProgressUseCase Caso de uso que obtiene el progreso de hábitos del día.
 * @param getTodayPomodoroTimeUseCase Caso de uso que calcula el total de minutos Pomodoro de hoy.
 * @param getLastActivityDateUseCase Caso de uso que consulta la fecha más reciente de actividad.
 */

class InicioViewModel(
    private val getTodayHabitProgressUseCase: GetTodayHabitProgressUseCase,
    private val getTodayPomodoroTimeUseCase: GetTodayPomodoroTimeUseCase,
    private val getLastActivityDateUseCase: GetLastActivityDateUseCase,
    private val usuarioRepository: UsuarioRepository,
    val racha : GetCurrentStreakUseCase
) : ViewModel() {

    // Estado observable de la UI que contiene los datos del resumen
    private val _uiState = MutableStateFlow(InicioUiState())
    val uiState: StateFlow<InicioUiState> = _uiState

    private val _fraseDelDia = MutableStateFlow<String?>(null)
    val fraseDelDia: StateFlow<String?> = _fraseDelDia

    /**
     * Carga el resumen diario del usuario para la fecha actual (por defecto) o una personalizada.
     *
     * @param userId ID del usuario autenticado.
     * @param fecha Fecha que se va a evaluar (por defecto: hoy).
     */

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarResumen(userId: Long, fecha: Date = Date()) {
        /*Se usa viewModelScope para lanzar la carga de datos en background.*/
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // Llamadas a los casos de uso
            val user = usuarioRepository.getUserById(userId)
            val progreso = getTodayHabitProgressUseCase(userId, fecha)
            val minutosPomodoro = getTodayPomodoroTimeUseCase(userId, fecha)
            val ultimaActividad = getLastActivityDateUseCase(userId)
            val rachaActual = racha(userId)


            // Actualiza el estado de UI con los resultados
            user?.let {
                seleccionarFraseDelDia()
                _uiState.value = InicioUiState(
                    isLoading = false,
                    habitosCompletados = progreso.completados, // ✅ Corregido
                    habitosTotales = progreso.total,
                    minutosPomodoro = minutosPomodoro,
                    ultimaActividad = ultimaActividad,
                    nombreUsuario = it.nombre,
                    rachaActual = rachaActual
                )
            }



        }
    }

    private fun seleccionarFraseDelDia() {
        _fraseDelDia.value = FrasesMotivacionales.obtenerAleatoria()
    }

    fun reiniciarEstado() {
        _uiState.value = InicioUiState(isLoading = true)
    }

}