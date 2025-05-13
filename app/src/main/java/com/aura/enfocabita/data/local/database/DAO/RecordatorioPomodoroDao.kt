package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.RecordatorioPomodoro
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordatorioPomodoroDao {

        /** Reactivo: observa todos los recordatorios de Pomodoro */
        @Query("SELECT * FROM recordatorios_pomodoro")
        fun observeAllRecordatorios(): Flow<List<RecordatorioPomodoro>>

        /** Carga puntual de todos los recordatorios */
        @Query("SELECT * FROM recordatorios_pomodoro")
        suspend fun getAllRecordatorios(): List<RecordatorioPomodoro>

        /** Reactivo: observa recordatorios de una sesión Pomodoro */
        @Query("SELECT * FROM recordatorios_pomodoro WHERE id_pomodoro = :pomodoroId")
        fun observeByPomodoroId(pomodoroId: Long): Flow<List<RecordatorioPomodoro>>

        /** Carga puntual de recordatorios de una sesión Pomodoro */
        @Query("SELECT * FROM recordatorios_pomodoro WHERE id_pomodoro = :pomodoroId")
        suspend fun getRecordatoriosByPomodoroId(pomodoroId: Long): List<RecordatorioPomodoro>

        /** Obtiene un recordatorio por su ID */
        @Query("SELECT * FROM recordatorios_pomodoro WHERE idRdPomodoro = :id LIMIT 1")
        suspend fun getRecordatorioById(id: Long): RecordatorioPomodoro?

        /** Inserta o reemplaza, devolviendo el ID generado */
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertRecordatorio(recordatorio: RecordatorioPomodoro): Long

        /** Actualiza un recordatorio y devuelve el número de filas afectadas */
        @Update
        suspend fun updateRecordatorio(recordatorio: RecordatorioPomodoro): Int

        /** Elimina por ID y devuelve el número de filas borradas */
        @Query("DELETE FROM recordatorios_pomodoro WHERE idRdPomodoro = :id")
        suspend fun deleteRecordatorioById(id: Long): Int

        /**
         * Transacción compuesta: inserta/reemplaza y devuelve la lista actualizada
         */
        @Transaction
        suspend fun upsertAndGetAll(recordatorio: RecordatorioPomodoro): List<RecordatorioPomodoro> {
            insertRecordatorio(recordatorio)
            return getAllRecordatorios()
        }
    }