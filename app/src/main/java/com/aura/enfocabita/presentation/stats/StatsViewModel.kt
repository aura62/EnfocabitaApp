package com.aura.enfocabita.presentation.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.enfocabita.domain.model.ResumenEstadisticasSemana
import com.aura.enfocabita.domain.usecase.stats.GetResumenEstadisticasSemanaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class StatsViewModel(
    private val useCase: GetResumenEstadisticasSemanaUseCase
) : ViewModel() {

    private val _resumen = MutableStateFlow<ResumenEstadisticasSemana?>(null)
    val resumen: StateFlow<ResumenEstadisticasSemana?> = _resumen

    fun cargar(userId: Long, inicio: Date, fin: Date) {
        viewModelScope.launch {
            _resumen.value = useCase(userId, inicio, fin)
        }
    }
}