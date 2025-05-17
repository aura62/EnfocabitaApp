package com.aura.enfocabita.domain.usecase.inicio

import android.os.Build
import androidx.annotation.RequiresApi
import com.aura.enfocabita.data.repository.ProgresoHabitoDiarioRepository
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

/**
 * Devuelve un resumen del progreso de hábitos del usuario para el día actual.
 */
class GetTodayHabitProgressUseCase(
    private val progresoRepo: ProgresoHabitoDiarioRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(userId: Long, fecha: Date = Date()): Resultado {
        val (startOfDay, endOfDay) = obtenerRangoDelDia(fecha)

        val completados = progresoRepo.getHabitsCompletsToday(userId, startOfDay, endOfDay)
        val total = progresoRepo.getHabitsTotalToday(userId, startOfDay, endOfDay)

        return Resultado(completados, total)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun obtenerRangoDelDia(fecha: Date): Pair<Date, Date> {
        val localDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val start = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        val end = Date.from(localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant())
        return start to end
    }

    data class Resultado(
        val completados: Int,
        val total: Int
    )
}