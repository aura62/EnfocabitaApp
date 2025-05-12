package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.EstadisticaPomodoro
import kotlinx.coroutines.flow.Flow

@Dao
interface EstadisticaPomodoroDao {

    /** Carga puntual de todas las estadísticas */
    @Query("SELECT * FROM estadistica_pomodoro")
    suspend fun getAllStats(): List<EstadisticaPomodoro>

    /** Reactivo: observa todas las estadísticas */
    @Query("SELECT * FROM estadistica_pomodoro")
    fun observeAllStats(): Flow<List<EstadisticaPomodoro>>

    /** Estadística de una sesión Pomodoro concreta */
    @Query("SELECT * FROM estadistica_pomodoro WHERE idPomodoro = :pomodoroId LIMIT 1")
    suspend fun getStatsByPomodoroId(pomodoroId: Long): EstadisticaPomodoro?

    /** Reactivo para esa misma estadística */
    @Query("SELECT * FROM estadistica_pomodoro WHERE idPomodoro = :pomodoroId LIMIT 1")
    fun observeStatsByPomodoroId(pomodoroId: Long): Flow<EstadisticaPomodoro?>

    /** Suma total de sesiones para un usuario */
    @Query("""
      SELECT SUM(totalSesiones)
      FROM estadistica_pomodoro AS e
      JOIN pomodoro_sesion AS p ON e.idPomodoro = p.idPomodoro
      WHERE p.id_Usuario = :userId
    """)
    suspend fun getTotalPomodorosByUser(userId: Long): Int?

    /** Promedio diario de sesiones para un usuario */
    @Query("""
      SELECT AVG(promedioDiario)
      FROM estadistica_pomodoro AS e
      JOIN pomodoro_sesion AS p ON e.idPomodoro = p.idPomodoro
      WHERE p.id_Usuario = :userId
    """)
    suspend fun getAverageDailyPomodorosByUser(userId: Long): Float?

    /** Inserta o reemplaza, devolviendo el ID generado */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStat(stat: EstadisticaPomodoro): Long

    /** Actualiza y devuelve filas modificadas */
    @Update
    suspend fun updateStat(stat: EstadisticaPomodoro): Int

    /** Borra por ID y devuelve filas borradas */
    @Query("DELETE FROM estadistica_pomodoro WHERE idEstPomodoro = :id")
    suspend fun deleteStatById(id: Long): Int
}
