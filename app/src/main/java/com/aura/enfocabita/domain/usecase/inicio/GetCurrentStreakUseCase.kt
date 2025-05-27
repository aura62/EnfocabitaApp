package com.aura.enfocabita.domain.usecase.inicio

import android.os.Build
import androidx.annotation.RequiresApi
import com.aura.enfocabita.data.repository.ProgresoHabitoDiarioRepository
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class GetCurrentStreakUseCase(
    private val progresoRepo: ProgresoHabitoDiarioRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(userId: Long): Int {
        var streak = 0
        val zone = ZoneId.systemDefault()
        var date = LocalDate.now()

        while (true) {
            val start = Date.from(date.atStartOfDay(zone).toInstant())
            val end = Date.from(date.plusDays(1).atStartOfDay(zone).toInstant())

            val completados = progresoRepo.getHabitsCompletsToday(userId, start, end)
            if (completados > 0) {
                streak++
                date = date.minusDays(1)
            } else {
                break
            }
        }

        return streak
    }
}