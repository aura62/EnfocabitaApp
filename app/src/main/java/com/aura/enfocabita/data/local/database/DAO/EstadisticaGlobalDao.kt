package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.EstadisticaGlobal
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface EstadisticaGlobalDao {

    /** Reactivo: observa todas las estadísticas globales */
    @Query("SELECT * FROM estadistica_global")
    fun observeAllStats(): Flow<List<EstadisticaGlobal>>

    /** Reactivo: observa la estadística global de un usuario */
    @Query("SELECT * FROM estadistica_global WHERE idUsuario = :userId LIMIT 1")
    fun observeStatsByUser(userId: Long): Flow<EstadisticaGlobal?>

    /** Carga puntual de todas las estadísticas globales */
    @Query("SELECT * FROM estadistica_global")
    suspend fun getAllStats(): List<EstadisticaGlobal>

    /** Carga puntual de la estadística global de un usuario */
    @Query("SELECT * FROM estadistica_global WHERE idUsuario = :userId LIMIT 1")
    suspend fun getStatsForUser(userId: Long): EstadisticaGlobal?

    /** Cuenta de días activos distintos para un usuario */
    @Query("""
      SELECT COUNT(DISTINCT fecha)
      FROM (
        SELECT fecha FROM pomodoro_historial WHERE id_pomodoro IN (
          SELECT idPomodoro FROM pomodoro_sesion WHERE id_Usuario = :userId
        )
        UNION
        SELECT fecha_registro FROM progreso_habito_diario WHERE id_Habito IN (
          SELECT idHabito FROM habito WHERE id_Usuario = :userId
        )
      )
    """)
    suspend fun getTotalActiveDays(userId: Long): Int

    /** Fecha del último progreso de un usuario */
    @Query("""
      SELECT MAX(fecha)
      FROM (
        SELECT fecha FROM pomodoro_historial WHERE id_pomodoro IN (
          SELECT idPomodoro FROM pomodoro_sesion WHERE id_Usuario = :userId
        )
        UNION ALL
        SELECT fecha_registro FROM progreso_habito_diario WHERE id_Habito IN (
          SELECT idHabito FROM habito WHERE id_Usuario = :userId
        )
      )
    """)
    suspend fun getLastProgressDate(userId: Long): Date?

    /** Inserta o reemplaza, devolviendo el ID generado */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStat(stat: EstadisticaGlobal): Long

    /** Actualiza y devuelve el número de filas afectadas */
    @Update
    suspend fun updateStat(stat: EstadisticaGlobal): Int

    /** Elimina por usuario y devuelve el número de filas borradas */
    @Query("DELETE FROM estadistica_global WHERE idUsuario = :userId")
    suspend fun deleteStatsByUser(userId: Long): Int
}