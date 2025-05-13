package com.aura.enfocabita.data.repository

import com.aura.enfocabita.data.local.database.DAO.EstadisticaPomodoroDao
import com.aura.enfocabita.data.local.database.entidades.EstadisticaPomodoro
import kotlinx.coroutines.flow.Flow

interface EstadisticaPomodoroRepository{
    fun observeStatsByPomodoroId(pomodoroId: Long): Flow<EstadisticaPomodoro?>
    fun observeAllStats(): Flow<List<EstadisticaPomodoro>>
    suspend fun getAllStats(): List<EstadisticaPomodoro>
    suspend fun getStatsByPomodoroId(pomodoroId: Long): EstadisticaPomodoro?
    suspend fun getTotalPomodorosByUser(userId: Long): Int?
    suspend fun getAverageDailyPomodorosByUser(userId: Long): Float?
    suspend fun savetStat(stat: EstadisticaPomodoro): Long
    suspend fun updateStat(stat: EstadisticaPomodoro): Int
    suspend fun removeStatById(id: Long): Int
    suspend fun upsertAndGetAll(stat: EstadisticaPomodoro): List<EstadisticaPomodoro>


}

class EstadisticaPomodoroRepositoryImpl(private val dao: EstadisticaPomodoroDao) : EstadisticaPomodoroRepository {
    override fun observeStatsByPomodoroId(pomodoroId: Long): Flow<EstadisticaPomodoro?> = dao.observeStatsByPomodoroId(pomodoroId)
    override fun observeAllStats(): Flow<List<EstadisticaPomodoro>> = dao.observeAllStats()
    override suspend fun getAllStats(): List<EstadisticaPomodoro> = dao.getAllStats()
    override suspend fun getStatsByPomodoroId(pomodoroId: Long): EstadisticaPomodoro? = dao.getStatsByPomodoroId(pomodoroId)
    override suspend fun getTotalPomodorosByUser(userId: Long): Int? = dao.getTotalPomodorosByUser(userId)
    override suspend fun getAverageDailyPomodorosByUser(userId: Long): Float? = dao.getAverageDailyPomodorosByUser(userId)
    override suspend fun savetStat(stat: EstadisticaPomodoro): Long = dao.insertStat(stat)
    override suspend fun updateStat(stat: EstadisticaPomodoro): Int = dao.updateStat(stat)
    override suspend fun removeStatById(id: Long): Int = dao.deleteStatById(id)
    override suspend fun upsertAndGetAll(stat: EstadisticaPomodoro): List<EstadisticaPomodoro> = dao.upsertAndGetAll(stat)

}