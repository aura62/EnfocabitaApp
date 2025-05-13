package com.aura.enfocabita.data.repository

import com.aura.enfocabita.data.local.database.DAO.RecordatorioPomodoroDao
import com.aura.enfocabita.data.local.database.entidades.RecordatorioPomodoro
import kotlinx.coroutines.flow.Flow

interface RecordatorioPomodoroRepository{
    fun observeAllRecordatorios(): Flow<List<RecordatorioPomodoro>>
    suspend fun getAllRecordatorios(): List<RecordatorioPomodoro>
    fun observeByPomodoroId(pomodoroId: Long): Flow<List<RecordatorioPomodoro>>
    suspend fun getRecordatoriosByPomodoroId(pomodoroId: Long): List<RecordatorioPomodoro>
    suspend fun getRecordatorioById(id: Long): RecordatorioPomodoro?
    suspend fun insertRecordatorio(recordatorio: RecordatorioPomodoro): Long
    suspend fun updateRecordatorio(recordatorio: RecordatorioPomodoro): Int
    suspend fun deleteRecordatorioById(id: Long): Int
    suspend fun upsertAndGetAll(recordatorio: RecordatorioPomodoro): List<RecordatorioPomodoro>
}

class RecordatorioPomodoroRepositoryImpl(private val dao: RecordatorioPomodoroDao) : RecordatorioPomodoroRepository {
    override fun observeAllRecordatorios(): Flow<List<RecordatorioPomodoro>> = dao.observeAllRecordatorios()
    override suspend fun getAllRecordatorios(): List<RecordatorioPomodoro> = dao.getAllRecordatorios()
    override fun observeByPomodoroId(pomodoroId: Long): Flow<List<RecordatorioPomodoro>> = dao.observeByPomodoroId(pomodoroId)
    override suspend fun getRecordatoriosByPomodoroId(pomodoroId: Long): List<RecordatorioPomodoro> = dao.getRecordatoriosByPomodoroId(pomodoroId)
    override suspend fun getRecordatorioById(id: Long): RecordatorioPomodoro? = dao.getRecordatorioById(id)
    override suspend fun insertRecordatorio(recordatorio: RecordatorioPomodoro): Long = dao.insertRecordatorio(recordatorio)
    override suspend fun updateRecordatorio(recordatorio: RecordatorioPomodoro): Int = dao.updateRecordatorio(recordatorio)
    override suspend fun deleteRecordatorioById(id: Long): Int = dao.deleteRecordatorioById(id)
    override suspend fun upsertAndGetAll(recordatorio: RecordatorioPomodoro): List<RecordatorioPomodoro> = dao.upsertAndGetAll(recordatorio)

}