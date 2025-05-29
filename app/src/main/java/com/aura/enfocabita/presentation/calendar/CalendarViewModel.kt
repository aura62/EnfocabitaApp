package com.aura.enfocabita.presentation.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.enfocabita.data.local.database.entidades.Habito
import com.aura.enfocabita.domain.usecase.calendar.GetCompletedDatesUseCase
import com.aura.enfocabita.domain.usecase.calendar.GetCompletedHabitsForDateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel(
    private val getCompletedDatesUseCase: GetCompletedDatesUseCase,
    private val getCompletedHabitsForDateUseCase: GetCompletedHabitsForDateUseCase
) : ViewModel() {

    private val _completedDates = MutableStateFlow<List<LocalDate>>(emptyList())
    val completedDates: StateFlow<List<LocalDate>> = _completedDates

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _habitosCompletados = MutableStateFlow<List<Habito>>(emptyList())
    val habitosCompletados: StateFlow<List<Habito>> = _habitosCompletados

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarFechas(userId: Long, mes: Int, anio: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _completedDates.value = getCompletedDatesUseCase(userId, mes, anio)
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al cargar fechas"
            } finally {
                _isLoading.value = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarHabitosPorFecha(userId: Long, fecha: LocalDate) {
        viewModelScope.launch {
            try {
                _habitosCompletados.value = getCompletedHabitsForDateUseCase(userId, fecha)
            } catch (e: Exception) {
                _habitosCompletados.value = emptyList() // fallback si hay error
            }
        }
    }
}