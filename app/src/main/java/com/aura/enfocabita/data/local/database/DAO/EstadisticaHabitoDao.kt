package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.EstadisticaHabito

@Dao
interface EstadisticaHabitoDao {

    @Query("SELECT * FROM estadistica_habito")
    suspend fun getAll(): List<EstadisticaHabito>

    @Query("SELECT * FROM estadistica_habito WHERE idHabito = :idHabito")
    suspend fun getByHabito(idHabito: Long): EstadisticaHabito?

    @Query("""
    SELECT SUM(totalCompletados)
    FROM estadistica_habito
    INNER JOIN habito ON estadistica_habito.idHabito = habito.idHabito
    WHERE habito.id_Usuario = :idUsuario
""")
    suspend fun obtenerTotalHabitosCompletados(idUsuario: Long): Int?

    @Query("""
    SELECT AVG(promedioCompletados)
    FROM estadistica_habito
    INNER JOIN habito ON estadistica_habito.idHabito = habito.idHabito
    WHERE habito.id_Usuario = :idUsuario
""")
    suspend fun obtenerPromedioHabitosCompletados(idUsuario: Long): Float?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(estadisticaHabito: EstadisticaHabito)

    @Update
    suspend fun update(estadisticaHabito: EstadisticaHabito)

    @Query("DELETE FROM estadistica_habito WHERE idEstHabito = :id")
    suspend fun deleteById(id: Long)
}