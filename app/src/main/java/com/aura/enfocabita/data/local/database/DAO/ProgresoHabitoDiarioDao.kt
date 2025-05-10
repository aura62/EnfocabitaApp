package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.ProgresoHabitoDiario
import java.util.Date

@Dao
interface ProgresoHabitoDiarioDao {

    @Query("SELECT * FROM progreso_habito_diario")
    suspend fun getAll(): List<ProgresoHabitoDiario>

    @Query("SELECT * FROM progreso_habito_diario WHERE id_Habito = :idHabito")
    suspend fun getByHabito(idHabito: Long): List<ProgresoHabitoDiario>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progreso: ProgresoHabitoDiario)

    @Update
    suspend fun update(progreso: ProgresoHabitoDiario)

    @Query("DELETE FROM progreso_habito_diario WHERE idPrHab = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM progreso_habito_diario WHERE id_Habito = :idHabito AND fecha_registro = :fecha LIMIT 1")
    suspend fun getByHabitoAndFecha(idHabito: Long, fecha: Date): ProgresoHabitoDiario?
}
