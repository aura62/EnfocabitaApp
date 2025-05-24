package com.aura.enfocabita.presentation.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.enfocabita.domain.model.ResumenEstadisticasSemana
import com.aura.enfocabita.domain.usecase.stats.GetHabitCountByTypeUseCase
import com.aura.enfocabita.domain.usecase.stats.GetResumenEstadisticasSemanaUseCase
import com.aura.enfocabita.domain.usecase.stats.GetHabitsCompletedPerDayOfWeekUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class StatsViewModel(
    private val resumenUseCase: GetResumenEstadisticasSemanaUseCase,
    private val graficoUseCase: GetHabitsCompletedPerDayOfWeekUseCase,
    private val piechartUseCase : GetHabitCountByTypeUseCase
) : ViewModel() {

    // 📊 Resumen textual semanal
    private val _resumen = MutableStateFlow<ResumenEstadisticasSemana?>(null)
    val resumen: StateFlow<ResumenEstadisticasSemana?> = _resumen

    // 📈 Datos para gráfico de barras
    private val _graficoSemanal = MutableStateFlow<Map<String, Int>>(emptyMap())
    val graficoSemanal: StateFlow<Map<String, Int>> = _graficoSemanal

    //datos para graficos de tarta
    private val _habitDistribucion = MutableStateFlow<Map<String, Int>>(emptyMap())
    val habitDistribucion: StateFlow<Map<String, Int>> = _habitDistribucion

    // Cargar resumen completo de semana
    fun cargar(userId: Long, inicio: Date, fin: Date) {
        viewModelScope.launch {
            _resumen.value = resumenUseCase(userId, inicio, fin)
        }
    }

    // Cargar gráfico de progreso diario
    fun cargarGrafico(userId: Long) {
        viewModelScope.launch {
            _graficoSemanal.value = graficoUseCase(userId)
        }
    }

    fun cargarDistribucion(userId: Long) {
        viewModelScope.launch {
            val result = piechartUseCase(userId)
            _habitDistribucion.value = result.mapKeys { it.key.name }
        }
    }
}