package com.aura.enfocabita.presentation.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarGrid(
    mes: Int,
    anio: Int,
    fechasCompletadas: List<LocalDate>,
    onDiaSeleccionado: (LocalDate) -> Unit
) {
    val primerDiaDelMes = LocalDate.of(anio, mes, 1)
    val diasEnMes = primerDiaDelMes.lengthOfMonth()
    val primerDiaDeLaSemana = primerDiaDelMes.dayOfWeek.value % 7

    val totalCeldas = diasEnMes + primerDiaDeLaSemana

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
        userScrollEnabled = false
    ) {
        // Nombres de los días
        items(7) { i ->
            val nombre = DayOfWeek.of((i + 1) % 7 + 1).getDisplayName(TextStyle.SHORT, Locale.getDefault())
            Text(nombre, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        }

        // Espacios vacíos antes del primer día
        items(primerDiaDeLaSemana) {
            Box(Modifier.size(40.dp))
        }

        // Días del mes
        items(diasEnMes) { index ->
            val dia = index + 1
            val fecha = LocalDate.of(anio, mes, dia)
            val estaCompleto = fechasCompletadas.contains(fecha)

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(40.dp)
                    .border(1.dp, MaterialTheme.colorScheme.outline, shape = MaterialTheme.shapes.small)
                    .background(if (estaCompleto) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) else Color.Transparent)
                    .clickable { onDiaSeleccionado(fecha) },
                contentAlignment = Alignment.Center
            ) {
                Text(dia.toString(), textAlign = TextAlign.Center)
            }
        }
    }
}


