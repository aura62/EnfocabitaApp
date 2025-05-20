package com.aura.enfocabita.presentation.pomodoro

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aura.enfocabita.data.local.database.entidades.PomodoroSesion
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun SimplePomodoroTimerDialog(
    sesion: PomodoroSesion,
    onClose: () -> Unit
) {
    var tiempoRestante by remember { mutableStateOf(sesion.duracion_ms) }
    var enEjecucion by remember { mutableStateOf(false) }

    // Lógica del temporizador
    LaunchedEffect(enEjecucion) {
        while (enEjecucion && tiempoRestante > 0) {
            delay(1000L)
            tiempoRestante -= 1000L
        }
        if (tiempoRestante <= 0 && enEjecucion) {
            enEjecucion = false
            // Puedes emitir un sonido o mostrar un mensaje aquí
        }
    }

    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {
            Button(onClick = { enEjecucion = !enEjecucion }) {
                Text(if (enEjecucion) "Pausar" else if (tiempoRestante == 0L) "Reiniciar" else "Iniciar")
            }
        },
        dismissButton = {
            TextButton(onClick = onClose) {
                Text("Cerrar")
            }
        },
        title = { Text("Temporizador Pomodoro") },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = sesion.tituloTarea)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = formatTime(tiempoRestante),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    )
}

private fun formatTime(ms: Long): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(ms)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(ms) % 60
    return String.format("%02d:%02d", minutes, seconds)
}