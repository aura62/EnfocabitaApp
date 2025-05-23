package com.aura.enfocabita.domain.usecase.stats

import com.aura.enfocabita.data.local.database.DAO.EstadisticaGlobalDao
import com.aura.enfocabita.data.local.database.DAO.EstadisticaHabitoDao
import com.aura.enfocabita.data.local.database.DAO.EstadisticaPomodoroDao
import java.util.Date

data class GlobalStatsSummary(
    val totalHabitos: Int,
    val promedioHabitos: Float,
    val totalPomodoros: Int,
    val promedioPomodoros: Float,
    val diasActivos: Int,
    val fechaUltimaActividad: Date?
)

class GetGlobalStatsUseCase(
    private val habitoDao: EstadisticaHabitoDao,
    private val pomodoroDao: EstadisticaPomodoroDao,
    private val globalDao: EstadisticaGlobalDao
) {
    suspend operator fun invoke(userId: Long): GlobalStatsSummary {
        return GlobalStatsSummary(
            totalHabitos = habitoDao.getTotalHabitsCompleted(userId) ?: 0,
            promedioHabitos = habitoDao.getAverageHabitsCompleted(userId) ?: 0f,
            totalPomodoros = pomodoroDao.getTotalPomodorosByUser(userId) ?: 0,
            promedioPomodoros = pomodoroDao.getAverageDailyPomodorosByUser(userId) ?: 0f,
            diasActivos = globalDao.getTotalActiveDays(userId),
            fechaUltimaActividad = globalDao.getLastProgressDate(userId)
        )
    }
}