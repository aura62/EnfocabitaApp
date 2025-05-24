package com.aura.enfocabita.presentation.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.collectAsState
import java.util.Calendar

@Composable
fun StatsScreen(
    userId: Long,
    viewModel: StatsViewModel = koinViewModel()
) {
    val resumenState = viewModel.resumen.collectAsState()
    val resumen = resumenState.value


    val (inicioSemana, finSemana) = remember {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        val inicio = calendar.time
        calendar.add(Calendar.DAY_OF_WEEK, 6)
        val fin = calendar.time
        inicio to fin
    }

    LaunchedEffect(userId) {
        viewModel.cargar(userId, inicioSemana, finSemana)
    }

    resumen?.let {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Resumen semanal", style = MaterialTheme.typography.titleLarge)
            Text("Total de hábitos: ${it.totalHabitos}")
            Text("Completados esta semana: ${it.completadosSemana} de ${it.totalEsperado}")
            Text("Cumplimiento: ${"%.1f".format(it.cumplimientoPorcentaje)}%")
            Text("Promedio diario: ${"%.1f".format(it.promedioDiario)} hábitos")
            Text("Días activos: ${it.diasActivos} / 7")
        }
    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}