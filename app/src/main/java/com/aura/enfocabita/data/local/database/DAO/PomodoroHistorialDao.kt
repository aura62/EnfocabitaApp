package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.PomodoroHistorial
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface PomodoroHistorialDao {

    /** Reactivo: observa todos los registros de historial */
    @Query("SELECT * FROM pomodoro_historial")
    fun observeAllHistorial(): Flow<List<PomodoroHistorial>>

    /** Carga puntual de todos los registros */
    @Query("SELECT * FROM pomodoro_historial")
    suspend fun getAllHistorial(): List<PomodoroHistorial>

    /** Reactivo: historial para una sesión Pomodoro concreta */
    @Query("SELECT * FROM pomodoro_historial WHERE id_pomodoro = :pomodoroId")
    fun observeHistorialByPomodoro(pomodoroId: Long): Flow<List<PomodoroHistorial>>

    /** Carga puntual de historial por sesión Pomodoro */
    @Query("SELECT * FROM pomodoro_historial WHERE id_pomodoro = :pomodoroId")
    suspend fun getHistorialByPomodoro(pomodoroId: Long): List<PomodoroHistorial>

    /** Historiales de un día concreto */
    @Query("SELECT * FROM pomodoro_historial WHERE fecha = :date")
    suspend fun getHistorialByDate(date: Date): List<PomodoroHistorial>

    /** Cuenta sesiones completadas en una fecha */
    @Query("SELECT COUNT(*) FROM pomodoro_historial WHERE fecha = :date")
    suspend fun countSessionsByDate(date: Date): Int

    @Query("SELECT * FROM pomodoro_historial WHERE idHistorial = :id LIMIT 1")
    fun observeById(id: Long): Flow<PomodoroHistorial?>


    /** Inserta o reemplaza y devuelve el ID generado */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistorial(record: PomodoroHistorial): Long

    /** Actualiza y devuelve filas modificadas */
    @Update
    suspend fun updateHistorial(record: PomodoroHistorial): Int

    /** Elimina por su ID y devuelve filas borradas */
    @Query("DELETE FROM pomodoro_historial WHERE idHistorial = :id")
    suspend fun deleteHistorialById(id: Long): Int

    @Transaction
    suspend fun upsertAndGetAll(record: PomodoroHistorial): List<PomodoroHistorial> {
        insertHistorial(record)
        return getAllHistorial()
    }
}