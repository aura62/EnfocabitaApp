package com.aura.enfocabita.presentation.inicio

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

/**
 * Pantalla de Inicio que muestra un resumen del día del usuario.
 *
 * Incluye información sobre hábitos completados, tiempo en sesiones Pomodoro
 * y la última fecha de actividad registrada.
 *
 * @param userId ID del usuario autenticado.
 * @param viewModel ViewModel que gestiona los datos y el estado de la pantalla.
 * @param modifier Modifier opcional para personalizar el layout.
 */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InicioScreen(
    userId: Long,
    viewModel: InicioViewModel,
    modifier: Modifier = Modifier
) {
    // Estado reactivo expuesto por el ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Carga automática del resumen cuando se entra por primera vez
    LaunchedEffect(userId) {
        viewModel.cargarResumen(userId)
    }

    // Contenido principal
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Resumen de hoy",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            // Indicador de carga mientras se obtienen los datos
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            // Mostrar progreso de hábitos
            Text(
                text = "Hábitos completados: ${uiState.habitosCompletados} de ${uiState.habitosTotales}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar minutos en Pomodoro
            Text(
                text = "Minutos en Pomodoro: ${uiState.minutosPomodoro} min",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar fecha de última actividad
            Text(
                text = "Última actividad: ${
                    uiState.ultimaActividad?.let {
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
                    } ?: "Sin datos"
                }",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}