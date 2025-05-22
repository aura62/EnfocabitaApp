package com.aura.enfocabita.domain.usecase.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import com.aura.enfocabita.data.local.database.DAO.ProgresoHabitoDiarioDao
import com.aura.enfocabita.data.local.database.entidades.Habito
import com.aura.enfocabita.utils.obtenerRangoDelDia
import java.time.LocalDate

class GetCompletedHabitsForDateUseCase(
    private val dao: ProgresoHabitoDiarioDao
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(userId: Long, date: LocalDate): List<Habito> {
        val (start, end) = obtenerRangoDelDia(date)
        return dao.getCompletedHabitsByDate(userId, start, end)
    }
}