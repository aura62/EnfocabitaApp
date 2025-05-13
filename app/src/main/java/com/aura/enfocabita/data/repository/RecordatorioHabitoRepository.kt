package com.aura.enfocabita.data.repository

import com.aura.enfocabita.data.local.database.DAO.RecordatorioHabitoDao
import com.aura.enfocabita.data.local.database.entidades.RecordatorioHabito
import kotlinx.coroutines.flow.Flow

interface RecordatorioHabitoRepository{
    fun observeAllRecordatorios(): Flow<List<RecordatorioHabito>>
    fun observeByHabitoId(habitId: Long): Flow<List<RecordatorioHabito>>
    suspend fun getAllRecordatorios(): List<RecordatorioHabito>
    suspend fun getRecordatoriosByHabito(habitId: Long): List<RecordatorioHabito>
    suspend fun saveRecordatorio(recordatorio: RecordatorioHabito): Long
    suspend fun updateRecordatorio(recordatorio: RecordatorioHabito): Int
    suspend fun deleteRecordatorioById(id: Long): Int
}

class RecordatorioHabitoRepositoryImpl (private val dao: RecordatorioHabitoDao) : RecordatorioHabitoRepository {

    override fun observeByHabitoId(habitId: Long): Flow<List<RecordatorioHabito>> = dao.observeByHabitoId(habitId)
    override fun observeAllRecordatorios(): Flow<List<RecordatorioHabito>> = dao.observeAllRecordatorios()
    override suspend fun getAllRecordatorios(): List<RecordatorioHabito> = dao.getAllRecordatorios()
    override suspend fun getRecordatoriosByHabito(habitId: Long): List<RecordatorioHabito> = dao.getRecordatoriosByHabito(habitId)
    override suspend fun updateRecordatorio(recordatorio: RecordatorioHabito): Int = dao.updateRecordatorio(recordatorio)
    override suspend fun deleteRecordatorioById(id: Long): Int = dao.deleteRecordatorioById(id)
    override suspend fun saveRecordatorio(recordatorio: RecordatorioHabito): Long = dao.insertRecordatorio(recordatorio)
}
