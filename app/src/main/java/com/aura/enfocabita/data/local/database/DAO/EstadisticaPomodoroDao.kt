package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.EstadisticaPomodoro

@Dao
interface EstadisticaPomodoroDao {

    @Query("SELECT * FROM estadistica_pomodoro")
    suspend fun getAll(): List<EstadisticaPomodoro>

    @Query("SELECT * FROM estadistica_pomodoro WHERE idPomodoro = :idPomodoro")
    suspend fun getByPomodoro(idPomodoro: Long): EstadisticaPomodoro?

    @Query("""
    SELECT SUM(totalSesiones)
    FROM estadistica_pomodoro
    INNER JOIN pomodoro_sesion ON estadistica_pomodoro.idPomodoro = pomodoro_sesion.idPomodoro
    WHERE pomodoro_sesion.id_Usuario = :idUsuario
""")
    suspend fun obtenerTotalPomodorosCompletados(idUsuario: Long): Int?

    @Query("""
    SELECT AVG(promedioDiario)
    FROM estadistica_pomodoro
    INNER JOIN pomodoro_sesion ON estadistica_pomodoro.idPomodoro = pomodoro_sesion.idPomodoro
    WHERE pomodoro_sesion.id_Usuario = :idUsuario
""")
    suspend fun obtenerPromedioPomodorosDiarios(idUsuario: Long): Float?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(estadisticaPomodoro: EstadisticaPomodoro)

    @Update
    suspend fun update(estadisticaPomodoro: EstadisticaPomodoro)

    @Query("DELETE FROM estadistica_pomodoro WHERE idEstPomodoro = :id")
    suspend fun deleteById(id: Long)
}