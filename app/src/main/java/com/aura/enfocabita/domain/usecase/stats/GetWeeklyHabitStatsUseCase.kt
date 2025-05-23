package com.aura.enfocabita.domain.usecase.stats

import android.os.Build
import androidx.annotation.RequiresApi
import com.aura.enfocabita.data.local.database.DAO.ProgresoHabitoDiarioDao
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class GetWeeklyHabitStatsUseCase(
    private val progresoDao: ProgresoHabitoDiarioDao
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(userId: Long, semana: LocalDate): List<Pair<LocalDate, Int>> {
        val zone = ZoneId.systemDefault()
        val hoy = LocalDate.now()
        val fechas = (0..6).map { hoy.minusDays(it.toLong()) }.reversed()

        return fechas.map { fecha ->
            val inicio = Date.from(fecha.atStartOfDay(zone).toInstant())
            val fin = Date.from(fecha.plusDays(1).atStartOfDay(zone).toInstant())
            val total = progresoDao.getHabitsCompleteCountForDate(userId, inicio, fin)
            fecha to total
        }
    }
}