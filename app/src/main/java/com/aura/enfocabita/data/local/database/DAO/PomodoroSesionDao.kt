package com.aura.enfocabita.data.local.database.DAO

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.aura.enfocabita.data.local.database.entidades.PomodoroSesion
import java.util.Date

@Dao
interface PomodoroSesionDao {

    /** Reactivo: observa todas las sesiones Pomodoro */
    @Query("SELECT * FROM pomodoro_sesion")
    fun observeAll(): Flow<List<PomodoroSesion>>

    /** Carga puntual de todas las sesiones */
    @Query("SELECT * FROM pomodoro_sesion")
    suspend fun getAllSesiones(): List<PomodoroSesion>

    /** Observa sesiones de un usuario en tiempo real */
    @Query("SELECT * FROM pomodoro_sesion WHERE id_Usuario = :userId")
    fun observeByUsuario(userId: Long): Flow<List<PomodoroSesion>>

    /** Carga puntual de sesiones de un usuario */
    @Query("SELECT * FROM pomodoro_sesion WHERE id_Usuario = :userId")
    suspend fun getSesionesByUsuario(userId: Long): List<PomodoroSesion>

    /** Obtiene una sesión por su ID */
    @Query("SELECT * FROM pomodoro_sesion WHERE idPomodoro = :id LIMIT 1")
    suspend fun getSesionById(id: Long): PomodoroSesion?

    /** Busca sesiones por nombre de tarea (pattern: \"%texto%\") */
    @Query("SELECT * FROM pomodoro_sesion WHERE nombre_tarea LIKE :pattern AND id_Usuario = :userId")
    suspend fun searchSesionesByNombre(pattern: String, userId: Long): List<PomodoroSesion>

    /** Inserta o reemplaza y devuelve el ID generado */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSesion(sesion: PomodoroSesion): Long

    /** Actualiza y devuelve el número de filas afectadas */
    @Update
    suspend fun updateSesion(sesion: PomodoroSesion): Int

    /** Elimina por ID y devuelve el número de filas borradas */
    @Query("DELETE FROM pomodoro_sesion WHERE idPomodoro = :id")
    suspend fun deleteSesionById(id: Long): Int

    /**
     * Transacción compuesta: inserta/reemplaza y devuelve lista actualizada
     */
    @Transaction
    suspend fun upsertAndGetAll(sesion: PomodoroSesion): List<PomodoroSesion> {
        insertSesion(sesion)
        return getAllSesiones()
    }
}