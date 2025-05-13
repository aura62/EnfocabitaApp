package com.aura.enfocabita.data.repository

import com.aura.enfocabita.data.local.database.DAO.EstadisticaHabitoDao
import com.aura.enfocabita.data.local.database.entidades.EstadisticaHabito
import kotlinx.coroutines.flow.Flow

interface EstadisticaHabitoRepository {

    fun observeAllStats(): Flow<List<EstadisticaHabito>>
    fun observeStatsByHabit(habitId: Long): Flow<EstadisticaHabito?>
    suspend fun getTotalHabitsCompleted(userId: Long): Int?
    suspend fun getAverageHabitsCompleted(userId: Long): Float?
    suspend fun saveStat(stat: EstadisticaHabito): Long
    suspend fun getStatsByHabit(habitId: Long): EstadisticaHabito?
    suspend fun updateStat(stat: EstadisticaHabito): Int
    suspend fun getAllStats(): List<EstadisticaHabito>
    suspend fun deleteStatById(id: Long): Int
    suspend fun upsertAndGetAll(stat: EstadisticaHabito): List<EstadisticaHabito>

}

class EstadisticaHabitoRepositoryImpl(private val dao: EstadisticaHabitoDao): EstadisticaHabitoRepository {

    override fun observeAllStats() : Flow<List<EstadisticaHabito>> = dao.observeAllStats()
    override fun observeStatsByHabit(habitId: Long): Flow<EstadisticaHabito?> = dao.observeStatsByHabit(habitId)
    override suspend fun getTotalHabitsCompleted(userId: Long): Int? = dao.getTotalHabitsCompleted(userId)
    override suspend fun getAverageHabitsCompleted(userId: Long): Float? = dao.getAverageHabitsCompleted(userId)
    override suspend fun saveStat(stat: EstadisticaHabito): Long = dao.insertStat(stat)
    override suspend fun getStatsByHabit(habitId: Long): EstadisticaHabito? = dao.getStatsByHabit(habitId)
    override suspend fun updateStat(stat: EstadisticaHabito): Int = dao.updateStat(stat)
    override suspend fun getAllStats(): List<EstadisticaHabito> = dao.getAllStats()
    override suspend fun deleteStatById(id: Long): Int = dao.deleteStatById(id)
    override suspend fun upsertAndGetAll(stat: EstadisticaHabito): List<EstadisticaHabito> = dao.upsertAndGetAll(stat)
}