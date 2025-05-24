package com.aura.enfocabita.domain.usecase.stats

import com.aura.enfocabita.data.repository.ProgresoHabitoDiarioRepository
import java.util.Calendar
import java.util.Date

class GetHabitsCompletedPerDayOfWeekUseCase(
    private val progresoRepo: ProgresoHabitoDiarioRepository
) {
    private val dias = listOf("Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb")

    suspend operator fun invoke(userId: Long): Map<String, Int> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        val startOfWeek = calendar.time

        val completadosPorDia = mutableMapOf<String, Int>()

        repeat(7) { i ->
            calendar.time = startOfWeek
            calendar.add(Calendar.DAY_OF_WEEK, i)

            val diaActual = calendar.time
            val (start, end) = getStartAndEndOfDay(diaActual)

            val count = progresoRepo.getHabitsCompletsToday(userId, start, end)
            val nombreDia = dias[calendar.get(Calendar.DAY_OF_WEEK) - 1]

            completadosPorDia[nombreDia] = count
        }

        return completadosPorDia
    }

    private fun getStartAndEndOfDay(date: Date): Pair<Date, Date> {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val start = calendar.time

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val end = calendar.time

        return start to end
    }
}