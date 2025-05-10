package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.PomodoroHistorial
import java.util.Date

@Dao
interface PomodoroHistorialDao {

    @Query("SELECT * FROM pomodoro_historial")
    suspend fun getAll(): List<PomodoroHistorial>

    @Query("SELECT * FROM pomodoro_historial WHERE id_pomodoro = :idPomodoro")
    suspend fun getByPomodoro(idPomodoro: Long): List<PomodoroHistorial>

    @Query("SELECT * FROM pomodoro_historial WHERE fecha = :fecha")
    suspend fun getByFecha(fecha: Date): List<PomodoroHistorial>

    @Query("SELECT COUNT(*) FROM pomodoro_historial WHERE fecha = :fecha")
    suspend fun contarSesionesPorFecha(fecha: Date): Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(historial: PomodoroHistorial)

    @Update
    suspend fun update(historial: PomodoroHistorial)

    @Query("DELETE FROM pomodoro_historial WHERE idHistorial = :id")
    suspend fun deleteById(id: Long)

}