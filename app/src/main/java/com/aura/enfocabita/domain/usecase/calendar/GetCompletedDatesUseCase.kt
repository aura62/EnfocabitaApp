package com.aura.enfocabita.domain.usecase.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import com.aura.enfocabita.data.local.database.DAO.ProgresoHabitoDiarioDao
import java.time.LocalDate
import java.time.ZoneId

class GetCompletedDatesUseCase @Inject constructor(
    private val progresoDao: ProgresoHabitoDiarioDao
) {
    annotation class Inject

    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(userId: Long, mes: Int, anio: Int): List<LocalDate> {
        val zona = ZoneId.systemDefault()

        val inicioMes = LocalDate.of(anio, mes, 1)
        val finMes = inicioMes.plusMonths(1).minusDays(1)

        val startDate = inicioMes.atStartOfDay(zona).toInstant()
        val endDate = finMes.plusDays(1).atStartOfDay(zona).toInstant()

        val start = java.util.Date.from(startDate)
        val end = java.util.Date.from(endDate)

        val progresos = progresoDao.getDatesHabitsComplete(userId, start, end)

        return progresos.map { it.toInstant().atZone(zona).toLocalDate() }

    }
}
