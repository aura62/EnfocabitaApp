package com.aura.enfocabita.data.repository

import com.aura.enfocabita.data.local.database.DAO.EstadisticaGlobalDao
import com.aura.enfocabita.data.local.database.entidades.EstadisticaGlobal
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface EstadisticaGlobalRepository {
    fun observeAllStats(): Flow<List<EstadisticaGlobal>>
    fun observeStatsByUser(userId: Long): Flow<EstadisticaGlobal?>
    suspend fun getAllStats(): List<EstadisticaGlobal>
    suspend fun getStatsForUser(userId: Long): EstadisticaGlobal?
    suspend fun getTotalActiveDays(userId: Long): Int
    suspend fun getLastProgressDate(userId: Long): Date?
    suspend fun saveStat(stat: EstadisticaGlobal): Long
    suspend fun updateStat(stat: EstadisticaGlobal): Int
    suspend fun deleteStatsByUser(userId: Long): Int
}

class EstadisticaGlobalRepositoryImpl(
    private val dao: EstadisticaGlobalDao
) : EstadisticaGlobalRepository {

    override fun observeAllStats(): Flow<List<EstadisticaGlobal>> =
        dao.observeAllStats()

    override fun observeStatsByUser(userId: Long): Flow<EstadisticaGlobal?> =
        dao.observeStatsByUser(userId)

    override suspend fun getAllStats(): List<EstadisticaGlobal> =
        dao.getAllStats()

    override suspend fun getStatsForUser(userId: Long): EstadisticaGlobal? =
        dao.getStatsForUser(userId)

    override suspend fun getTotalActiveDays(userId: Long): Int =
        dao.getTotalActiveDays(userId)

    override suspend fun getLastProgressDate(userId: Long): Date? =
        dao.getLastProgressDate(userId)

    override suspend fun saveStat(stat: EstadisticaGlobal): Long =
        dao.insertStat(stat)

    override suspend fun updateStat(stat: EstadisticaGlobal): Int =
        dao.updateStat(stat)

    override suspend fun deleteStatsByUser(userId: Long): Int =
        dao.deleteStatsByUser(userId)
}