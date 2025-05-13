package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.RecordatorioHabito
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordatorioHabitoDao {

    /** Reactivo: observa todos los recordatorios de hábito */
    @Query("SELECT * FROM recordatorios_habito")
    fun observeAllRecordatorios(): Flow<List<RecordatorioHabito>>

    /** Carga puntual de todos los recordatorios */
    @Query("SELECT * FROM recordatorios_habito")
    suspend fun getAllRecordatorios(): List<RecordatorioHabito>

    /** Reactivo: observa los recordatorios de un hábito concreto */
    @Query("SELECT * FROM recordatorios_habito WHERE idHabito = :habitId")
    fun observeByHabitoId(habitId: Long): Flow<List<RecordatorioHabito>>

    /** Carga puntual de recordatorios de un hábito */
    @Query("SELECT * FROM recordatorios_habito WHERE idHabito = :habitId")
    suspend fun getRecordatoriosByHabito(habitId: Long): List<RecordatorioHabito>

    /** Inserta o reemplaza, devolviendo el ID generado */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecordatorio(recordatorio: RecordatorioHabito): Long

    /** Actualiza y devuelve el número de filas afectadas */
    @Update
    suspend fun updateRecordatorio(recordatorio: RecordatorioHabito): Int

    /** Elimina por ID y devuelve el número de filas borradas */
    @Query("DELETE FROM recordatorios_habito WHERE idRecordatorioHabito = :id")
    suspend fun deleteRecordatorioById(id: Long): Int

    /**
     * Transacción compuesta: inserta/reemplaza y devuelve la lista completa actualizada
     */
    @Transaction
    suspend fun upsertAndGetAll(recordatorio: RecordatorioHabito): List<RecordatorioHabito> {
        insertRecordatorio(recordatorio)
        return getAllRecordatorios()
    }
}