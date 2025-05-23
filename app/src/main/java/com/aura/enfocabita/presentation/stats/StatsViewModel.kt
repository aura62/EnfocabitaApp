package com.aura.enfocabita.presentation.stats

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.enfocabita.domain.usecase.stats.GetGlobalStatsUseCase
import com.aura.enfocabita.domain.usecase.stats.GetWeeklyHabitStatsUseCase
import com.aura.enfocabita.domain.usecase.stats.GlobalStatsSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class StatsViewModel(
    private val getWeeklyHabitStats: GetWeeklyHabitStatsUseCase,
    private val getGlobalStats: GetGlobalStatsUseCase
) : ViewModel() {

    private val _stats = MutableStateFlow<List<Pair<String, Int>>>(emptyList())
    val stats: StateFlow<List<Pair<String, Int>>> get() = _stats

    private val _globalSummary = MutableStateFlow<GlobalStatsSummary?>(null)
    val globalSummary: StateFlow<GlobalStatsSummary?> get() = _globalSummary

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarEstadisticas(userId: Long, semana: LocalDate = LocalDate.now()) {
        viewModelScope.launch {
            try {
                val resumenSemanal = getWeeklyHabitStats(userId, semana)
                val resumenGlobal = getGlobalStats(userId)
                val formatter = DateTimeFormatter.ofPattern("dd-MM", Locale("es"))

                _stats.value = resumenSemanal.map { (fecha, valor) -> formatter.format(fecha) to valor }
                _globalSummary.value = resumenGlobal
            } catch (e: Exception) {
                // Aquí podrías emitir un mensaje de error si planeas agregar feedback de UI.
                _stats.value = emptyList()
                _globalSummary.value = null
            }
        }
    }
}