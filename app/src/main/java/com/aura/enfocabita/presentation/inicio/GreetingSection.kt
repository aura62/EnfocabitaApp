package com.aura.enfocabita.presentation.inicio

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GreetingSection(nombre: String) {
    val hora = remember { LocalTime.now().hour }
    val saludo = when (hora) {
        in 5..11 -> "¡Buenos días"
        in 12..18 -> "¡Buenas tardes"
        else -> "¡Buenas noches"
    }

    Text(
        text = "$saludo, $nombre!",
        style = MaterialTheme.typography.headlineMedium
    )
}
