package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.PomodoroSesion

@Dao
interface PomodoroSesionDao {

    @Query("SELECT * FROM pomodoro_sesion")
    suspend fun getAll(): List<PomodoroSesion>

    @Query("SELECT * FROM pomodoro_sesion WHERE id_Usuario = :idUsr")
    suspend fun getByUsuario(idUsr: Long): List<PomodoroSesion>

    @Query("SELECT * FROM pomodoro_sesion WHERE nombre_tarea LIKE '%' || :nombre || '%' AND id_Usuario = :idUsr")
    suspend fun buscarPorNombre(nombre: String, idUsr: Long): List<PomodoroSesion>

    @Query("SELECT * FROM pomodoro_sesion WHERE idPomodoro = :id LIMIT 1")
    suspend fun getById(id: Long): PomodoroSesion?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pomodoroSesion: PomodoroSesion)

    @Update
    suspend fun update(pomodoroSesion: PomodoroSesion)

    @Query("DELETE FROM pomodoro_sesion WHERE idPomodoro = :id")
    suspend fun deleteById(id: Long)
}