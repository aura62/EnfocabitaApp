package com.aura.enfocabita.presentation.stats

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun PieChartDistribucionHabitos(data: Map<String, Int>) {
    // ✅ Prevención de división por cero
    val total = data.values.sum().takeIf { it > 0 }?.toFloat() ?: 1f
    val scrollState = rememberScrollState()

    // 🎨 Colores asignados por tipo
    val coloresPorTipo = mapOf(
        "ADQUIRIR" to Color(0xFF81C784),
        "MANTENER" to Color(0xFF64B5F6),
        "ABANDONAR" to Color(0xFFE57373)
    )

    // 🥧 Datos ordenados de mayor a menor
    val slices = data.entries
        .sortedByDescending { it.value }
        .map {
            PieChartData.Slice(
                value = it.value.toFloat(),
                color = coloresPorTipo[it.key] ?: Color.Gray
            )
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 📊 Gráfico de pastel sin etiquetas internas
        PieChart(
            pieChartData = PieChartData(slices),
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            animation = simpleChartAnimation(),
            sliceDrawer = SimpleSliceDrawer()
        )

        // 📋 Leyenda textual externa con porcentajes
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            data.entries
                .sortedByDescending { it.value }
                .forEach { (tipo, cantidad) ->
                    val porcentaje = (cantidad / total * 100).toInt()
                    Text(
                        text = "$tipo: $cantidad hábito(s) ($porcentaje%)",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
        }
    }
}