package com.aura.enfocabita.presentation.pomodoro

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aura.enfocabita.data.local.database.entidades.PomodoroSesion
import com.aura.enfocabita.domain.usecase.pomodoro.PomodoroFase
import com.aura.enfocabita.domain.usecase.pomodoro.avanzarAFaseSiguiente
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import androidx.compose.ui.platform.LocalContext


@Composable
fun SimplePomodoroTimerDialog(
    sesion: PomodoroSesion,
    onClose: () -> Unit
) {
    var tiempoRestante by remember { mutableStateOf(sesion.duracion_ms) }
    var enEjecucion by remember { mutableStateOf(false) }
    var faseActual by remember { mutableStateOf<PomodoroFase>(PomodoroFase.Trabajo(1)) }

    val context = LocalContext.current

    LaunchedEffect(enEjecucion, tiempoRestante) {
        if (enEjecucion && tiempoRestante > 0) {
            delay(1000L)
            tiempoRestante -= 1000L
        } else if (enEjecucion && tiempoRestante <= 0) {
            enEjecucion = false
            reproducirTonoSistema(context)
            faseActual = avanzarAFaseSiguiente(faseActual, sesion)
            tiempoRestante = when (faseActual) {
                is PomodoroFase.Trabajo -> sesion.duracion_ms
                is PomodoroFase.DescansoCorto -> sesion.dcorto_ms
                PomodoroFase.DescansoLargo -> sesion.dLargo_ms
            }
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
        title = { Text(sesion.tituloTarea) },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Fase: ${faseActual.nombre()}")
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

private fun reproducirTonoSistema(context: android.content.Context) {
    val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val ringtone = RingtoneManager.getRingtone(context, notification)
    ringtone?.play()
}

private fun PomodoroFase.nombre(): String = when (this) {
    is PomodoroFase.Trabajo -> "Trabajo #$sesionActual"
    is PomodoroFase.DescansoCorto -> "Descanso Corto"
    PomodoroFase.DescansoLargo -> "Descanso Largo"
}

