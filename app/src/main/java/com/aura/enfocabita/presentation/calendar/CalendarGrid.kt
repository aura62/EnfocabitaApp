package com.aura.enfocabita.presentation.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import java.util.*

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
    val diaSemanaInicio = primerDiaDelMes.dayOfWeek
    val offset = (diaSemanaInicio.value + 6) % 7 // Lunes = 0, Domingo = 6

    val totalCeldas = diasEnMes + offset

    val diasSemana = listOf(
        DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
        userScrollEnabled = false
    ) {
        // Nombres de los días en español
        items(7) { i ->
            val nombre = diasSemana[i].getDisplayName(TextStyle.SHORT, Locale("es"))
            Text(
                text = nombre,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium
            )
        }

        // Espacios vacíos antes del primer día
        items(offset) {
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
                    .background(
                        if (estaCompleto) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                        else Color.Transparent
                    )
                    .clickable { onDiaSeleccionado(fecha) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = dia.toString(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


