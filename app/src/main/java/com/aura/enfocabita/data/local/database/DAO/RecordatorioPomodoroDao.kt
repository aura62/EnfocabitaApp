package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.RecordatorioPomodoro

@Dao
interface RecordatorioPomodoroDao {

    @Query("SELECT * FROM recordatorios_pomodoro")
    suspend fun getAll(): List<RecordatorioPomodoro>

    @Query("SELECT * FROM recordatorios_pomodoro WHERE idRdPomodoro = :id LIMIT 1")
    suspend fun getById(id: Long): RecordatorioPomodoro?


    @Query("SELECT * FROM recordatorios_pomodoro WHERE id_pomodoro = :idPomodoro")
    suspend fun getByPomodoro(idPomodoro: Long): List<RecordatorioPomodoro>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rdPomodoro: RecordatorioPomodoro)

    @Update
    suspend fun update(rdPomodoro: RecordatorioPomodoro)

    @Query("DELETE FROM recordatorios_pomodoro WHERE idRdPomodoro = :id")
    suspend fun deleteById(id: Long)
}