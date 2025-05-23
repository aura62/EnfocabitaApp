package com.aura.enfocabita.presentation.stats

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatsScreen(
    userId: Long,
    viewModel: StatsViewModel = koinViewModel()
) {
    val stats by viewModel.stats.collectAsState()
    val resumen by viewModel.globalSummary.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarEstadisticas(userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Estadísticas semanales",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        WeeklyBarChart(stats = stats)

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Resumen global",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        resumen?.let {
            Text("Total de días activos: ${it.diasActivos}")
            Text(
                "Último día activo: ${
                    it.fechaUltimaActividad?.toInstant()?.atZone(java.time.ZoneId.systemDefault())
                        ?.toLocalDate()
                        ?.format(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale("es")))
                }"
            )
        } ?: Text("Cargando resumen...")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyBarChart(
    stats: List<Pair<String, Int>>,
    modifier: Modifier = Modifier
) {
    val maxValor = stats.maxOfOrNull { it.second } ?: 1
    val barColor = MaterialTheme.colorScheme.primary

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {
        stats.forEach { (fecha, valor) ->
            val dia = try {
                LocalDate.parse(fecha)
                    .dayOfWeek
                    .getDisplayName(TextStyle.SHORT, Locale("es"))
                    .replaceFirstChar { it.uppercaseChar() }
            } catch (e: Exception) {
                fecha.take(3)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text("$valor", style = MaterialTheme.typography.labelSmall)
                Box(
                    modifier = Modifier
                        .height((valor * 120 / maxValor).dp)
                        .width(24.dp)
                        .background(barColor)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = dia, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}