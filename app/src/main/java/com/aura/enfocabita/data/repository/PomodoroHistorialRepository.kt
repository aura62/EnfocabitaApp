package com.aura.enfocabita.data.repository

import com.aura.enfocabita.data.local.database.DAO.PomodoroHistorialDao
import com.aura.enfocabita.data.local.database.entidades.PomodoroHistorial
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Repositorio para gestionar el historial de sesiones Pomodoro.
 */
interface PomodoroHistorialRepository {

    /** Observa en tiempo real todos los registros de historial */
    fun observeAllEntries(): Flow<List<PomodoroHistorial>>

    /** Observa registros de historial de una sesión concreta */
    fun observeEntriesBySession(sessionId: Long): Flow<List<PomodoroHistorial>>

    /** Observa un único registro de historial por su ID */
    fun observeEntryById(id: Long): Flow<PomodoroHistorial?>

    /** Carga puntual de registros de una sesión concreta */
    suspend fun getEntriesBySession(sessionId: Long): List<PomodoroHistorial>

    /** Carga puntual de registros para una fecha */
    suspend fun getEntriesByDate(date: Date): List<PomodoroHistorial>

    /** Cuenta cuántas sesiones hay en una fecha */
    suspend fun countSessionsByDate(date: Date): Int

    /**
     * Inserta o actualiza un registro y devuelve la lista completa
     * de entradas en la misma transacción.
     */
    suspend fun saveAndFetchAll(record: PomodoroHistorial): List<PomodoroHistorial>

    /** Elimina un registro por su ID */
    suspend fun deleteEntryById(id: Long): Int
}

/**
 * Implementación del repositorio usando el DAO de Room.
 */
class PomodoroHistorialRepositoryImpl(
    private val dao: PomodoroHistorialDao
) : PomodoroHistorialRepository {

    override fun observeAllEntries(): Flow<List<PomodoroHistorial>> =
        dao.observeAllHistorial()

    override fun observeEntriesBySession(sessionId: Long): Flow<List<PomodoroHistorial>> =
        dao.observeHistorialByPomodoro(sessionId)

    override fun observeEntryById(id: Long): Flow<PomodoroHistorial?> =
        dao.observeById(id)

    override suspend fun getEntriesBySession(sessionId: Long): List<PomodoroHistorial> =
        dao.getHistorialByPomodoro(sessionId)

    override suspend fun getEntriesByDate(date: Date): List<PomodoroHistorial> =
        dao.getHistorialByDate(date)

    override suspend fun countSessionsByDate(date: Date): Int =
        dao.countSessionsByDate(date)

    override suspend fun saveAndFetchAll(record: PomodoroHistorial): List<PomodoroHistorial> =
        dao.upsertAndGetAll(record)

    override suspend fun deleteEntryById(id: Long): Int =
        dao.deleteHistorialById(id)
}