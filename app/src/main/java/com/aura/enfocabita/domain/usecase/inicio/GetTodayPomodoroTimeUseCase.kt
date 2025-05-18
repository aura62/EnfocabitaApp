package com.aura.enfocabita.domain.usecase.inicio

import android.os.Build
import androidx.annotation.RequiresApi
import com.aura.enfocabita.data.repository.PomodoroHistorialRepository
import java.util.Date

class GetTodayPomodoroTimeUseCase(
    private val pomodoroHistorialRepository: PomodoroHistorialRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(userId: Long, fecha: Date): Long {
        val (start, end) = obtenerRangoDelDia(fecha)
        return (pomodoroHistorialRepository.getTotalPomodoroTimeInRange(userId, start, end) ?: 0L) / 60000L

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun obtenerRangoDelDia(fecha: Date): Pair<Date, Date> {
        val localDate = fecha.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate()
        val start = Date.from(localDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant())
        val end = Date.from(localDate.plusDays(1).atStartOfDay(java.time.ZoneId.systemDefault()).toInstant())
        return start to end
    }
}
