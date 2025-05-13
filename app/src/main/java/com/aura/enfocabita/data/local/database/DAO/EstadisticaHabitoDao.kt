package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.EstadisticaHabito
import kotlinx.coroutines.flow.Flow

@Dao
interface EstadisticaHabitoDao {


    /** Reactivo: observa todas las estadísticas de hábitos */
    @Query("SELECT * FROM estadistica_habito")
    fun observeAllStats(): Flow<List<EstadisticaHabito>>

    /** Carga puntual de todas las estadísticas de hábitos */
    @Query("SELECT * FROM estadistica_habito")
    suspend fun getAllStats(): List<EstadisticaHabito>

    /** Obtiene la estadística de un hábito concreto */
    @Query("SELECT * FROM estadistica_habito WHERE idHabito = :habitId LIMIT 1")
    suspend fun getStatsByHabit(habitId: Long): EstadisticaHabito?

    /** Reactivo: observa la estadística de un hábito concreto */
    @Query("SELECT * FROM estadistica_habito WHERE idHabito = :habitId LIMIT 1")
    fun observeStatsByHabit(habitId: Long): Flow<EstadisticaHabito?>

    /** Total de hábitos completados por un usuario */
    @Query("""
      SELECT SUM(totalCompletados)
      FROM estadistica_habito AS e
      JOIN habito AS h ON e.idHabito = h.idHabito
      WHERE h.id_Usuario = :userId
    """)
    suspend fun getTotalHabitsCompleted(userId: Long): Int?

    /** Promedio de hábitos completados por un usuario */
    @Query("""
      SELECT AVG(promedioCompletados)
      FROM estadistica_habito AS e
      JOIN habito AS h ON e.idHabito = h.idHabito
      WHERE h.id_Usuario = :userId
    """)
    suspend fun getAverageHabitsCompleted(userId: Long): Float?

    /** Inserta o reemplaza, devolviendo el ID generado */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStat(stat: EstadisticaHabito): Long

    /** Actualiza y devuelve el número de filas afectadas */
    @Update
    suspend fun updateStat(stat: EstadisticaHabito): Int

    /** Elimina por ID y devuelve el número de filas borradas */
    @Query("DELETE FROM estadistica_habito WHERE idEstHabito = :id")
    suspend fun deleteStatById(id: Long): Int

    /** Transacción compuesta: inserta/reemplaza y devuelve la lista actualizada */
    @Transaction
    suspend fun upsertAndGetAll(stat: EstadisticaHabito): List<EstadisticaHabito> {
        insertStat(stat)
        return getAllStats()
    }
}