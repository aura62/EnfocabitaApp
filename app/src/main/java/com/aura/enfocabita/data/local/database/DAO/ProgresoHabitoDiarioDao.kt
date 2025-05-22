package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.ProgresoHabitoDiario
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ProgresoHabitoDiarioDao {

        /** Reactivo: observa todo el progreso diario */
        @Query("SELECT * FROM progreso_habito_diario")
        fun observeAllProgress(): Flow<List<ProgresoHabitoDiario>>

        /** Carga puntual de todo el progreso diario */
        @Query("SELECT * FROM progreso_habito_diario")
        suspend fun getAllProgress(): List<ProgresoHabitoDiario>

        /** Reactivo: observa progreso de un hábito concreto */
        @Query("SELECT * FROM progreso_habito_diario WHERE id_Habito = :habitId")
        fun observeProgressByHabit(habitId: Long): Flow<List<ProgresoHabitoDiario>>

        /** Carga puntual de progreso de un hábito */
        @Query("SELECT * FROM progreso_habito_diario WHERE id_Habito = :habitId")
        suspend fun getProgressByHabit(habitId: Long): List<ProgresoHabitoDiario>

        /** Obtiene el progreso de un hábito en una fecha dada */
        @Query("""
      SELECT * 
      FROM progreso_habito_diario 
      WHERE id_Habito = :habitId 
        AND fecha_registro = :date 
      LIMIT 1
    """)
        suspend fun getProgressByHabitAndDate(habitId: Long, date: Date): ProgresoHabitoDiario?

        @Query("""
    SELECT * FROM progreso_habito_diario 
    WHERE id_Habito = :habitId 
      AND fecha_registro BETWEEN :start AND :end 
    LIMIT 1
""")
        suspend fun getProgressByHabitAndDateRange(
                habitId: Long,
                start: Date,
                end: Date
        ): ProgresoHabitoDiario?


        /** Inserta o reemplaza, devolviendo el ID generado */
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertProgress(progress: ProgresoHabitoDiario): Long

        /** Actualiza y devuelve número de filas modificadas */
        @Update
        suspend fun updateProgress(progress: ProgresoHabitoDiario): Int

        /** Elimina por ID y devuelve número de filas borradas */
        @Query("DELETE FROM progreso_habito_diario WHERE idPrHab = :id")
        suspend fun deleteProgressById(id: Long): Int

        /**
         * Transacción: inserta/reemplaza y retorna el progreso de ese día
         */
        @Transaction
        suspend fun upsertAndGetByHabitAndDate(progress: ProgresoHabitoDiario): ProgresoHabitoDiario? {
            insertProgress(progress)
            return getProgressByHabitAndDate(progress.idHab, progress.fechaRegistro)
        }

        @Query("""
    SELECT COUNT(*) 
    FROM progreso_habito_diario 
    WHERE fecha_registro BETWEEN :start AND :end 
      AND completado = 1
      AND id_Habito IN (
        SELECT idHabito FROM habito WHERE id_Usuario = :userId
    )
""")
        suspend fun getHabitsCompletsToday(userId: Long, start: Date, end: Date): Int

        @Query("""
    SELECT COUNT(*) 
    FROM progreso_habito_diario 
    WHERE fecha_registro BETWEEN :start AND :end 
      AND id_Habito IN (
        SELECT idHabito FROM habito WHERE id_Usuario = :userId
    )
""")
        suspend fun getHabitsTotalToday(userId: Long, start: Date, end: Date): Int

}
