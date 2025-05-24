package com.aura.enfocabita.presentation.stats

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import java.util.*

@Composable
fun StatsScreen(
    userId: Long,
    viewModel: StatsViewModel = koinViewModel()
) {
    val resumen by viewModel.resumen.collectAsState()
    val graficoSemanal by viewModel.graficoSemanal.collectAsState()
    val habitDistribucion by viewModel.habitDistribucion.collectAsState()

    val (inicioSemana, finSemana) = remember {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        val inicio = calendar.time
        calendar.add(Calendar.DAY_OF_WEEK, 6)
        val fin = calendar.time
        inicio to fin
    }

    // Carga inicial de datos
    LaunchedEffect(userId) {
        viewModel.cargar(userId, inicioSemana, finSemana)
        viewModel.cargarGrafico(userId)
        viewModel.cargarDistribucion(userId)
    }

    // UI principal
    resumen?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 📊 Resumen textual
            Text("Resumen semanal", style = MaterialTheme.typography.titleLarge)
            Text("Total de hábitos: ${it.totalHabitos}")
            Text("Completados esta semana: ${it.completadosSemana} de ${it.totalEsperado}")
            Text("Cumplimiento: ${"%.1f".format(it.cumplimientoPorcentaje)}%")
            Text("Promedio diario: ${"%.1f".format(it.promedioDiario)} hábitos")
            Text("Días activos: ${it.diasActivos} / 7")

            Divider()

            // 📈 Gráfico de barras por día
            if (graficoSemanal.isNotEmpty()) {
                Text("Progreso diario", style = MaterialTheme.typography.titleMedium)
                BarChartProgresoSemanal(data = graficoSemanal)
            } else {
                Text("No hay datos suficientes para el gráfico diario.")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider()

            // 🥧 Gráfico de pastel por tipo de hábito
            if (habitDistribucion.isNotEmpty()) {
                Text("Distribución por tipo de hábito", style = MaterialTheme.typography.titleMedium)
                PieChartDistribucionHabitos(habitDistribucion)
            }
        }
    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}