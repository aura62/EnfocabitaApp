package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.RecordatorioHabito

@Dao
interface RecordatorioHabitoDao {

    @Query("SELECT * FROM recordatorios_habito")
    suspend fun getAll(): List<RecordatorioHabito>

    @Query("SELECT * FROM recordatorios_habito WHERE idHabito = :idHabito")
    suspend fun getByHabito(idHabito: Long): List<RecordatorioHabito>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rdHabito: RecordatorioHabito)

    @Update
    suspend fun update(rdHabito: RecordatorioHabito)

    @Query("DELETE FROM recordatorios_habito WHERE idRecordatorioHabito = :id")
    suspend fun deleteById(id: Long)
}