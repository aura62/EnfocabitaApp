package com.aura.enfocabita.presentation.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import org.koin.compose.viewmodel.koinViewModel
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    userId: Long,
    viewModel: CalendarViewModel = koinViewModel()
) {
    val hoy = LocalDate.now()
    var mesActual by remember { mutableStateOf(hoy.monthValue) }
    var anioActual by remember { mutableStateOf(hoy.year) }
    var fechaSeleccionada by remember { mutableStateOf<LocalDate?>(null) }

    val fechasCompletadas by viewModel.completedDates.collectAsState()
    val habitosCompletados by viewModel.habitosCompletados.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(mesActual, anioActual) {
        viewModel.cargarFechas(userId, mesActual, anioActual)
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = {
                if (mesActual == 1) {
                    mesActual = 12
                    anioActual -= 1
                } else mesActual -= 1
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Anterior")
            }

            Text(
                text = Month.of(mesActual).getDisplayName(TextStyle.FULL, Locale.getDefault()) + " $anioActual",
                style = MaterialTheme.typography.titleLarge
            )

            IconButton(onClick = {
                if (mesActual == 12) {
                    mesActual = 1
                    anioActual += 1
                } else mesActual += 1
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Siguiente")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        CalendarGrid(
            mes = mesActual,
            anio = anioActual,
            fechasCompletadas = fechasCompletadas,
            onDiaSeleccionado = { fecha ->
                fechaSeleccionada = fecha
                viewModel.cargarHabitosPorFecha(userId, fecha)
            }
        )

        if (isLoading) {
            Spacer(Modifier.height(16.dp))
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        fechaSeleccionada?.let { fecha ->
            Spacer(Modifier.height(16.dp))
            Text("Hábitos completados el ${fechaSeleccionada!!.format(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale("es")))}"
            )
            Spacer(Modifier.height(8.dp))
            habitosCompletados.forEach { habito ->
                Text("- ${habito.titulo}")
            }
        }
    }
}
