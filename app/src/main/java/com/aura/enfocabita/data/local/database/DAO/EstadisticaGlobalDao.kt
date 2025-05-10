package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.EstadisticaGlobal
import java.util.Date

@Dao
interface EstadisticaGlobalDao {

    @Query("SELECT * FROM estadistica_global")
    suspend fun getAll(): List<EstadisticaGlobal>

    @Query("SELECT * FROM estadistica_global WHERE idUsuario = :idUsuario")
    suspend fun getByUsuario(idUsuario: Long): EstadisticaGlobal?

    @Query("""
    SELECT COUNT(DISTINCT fecha)
    FROM (
        SELECT fecha FROM pomodoro_historial WHERE id_pomodoro IN (
            SELECT idPomodoro FROM pomodoro_sesion WHERE id_Usuario = :idUsuario
        )
        UNION
        SELECT fecha_registro FROM progreso_habito_diario WHERE id_Habito IN (
            SELECT idHabito FROM habito WHERE id_Usuario = :idUsuario
        )
    )
""")
    suspend fun obtenerTotalDiasActivos(idUsuario: Long): Int

    @Query("""
    SELECT MAX(fecha)
    FROM (
        SELECT fecha FROM pomodoro_historial WHERE id_pomodoro IN (
            SELECT idPomodoro FROM pomodoro_sesion WHERE id_Usuario = :idUsuario
        )
        UNION ALL
        SELECT fecha_registro FROM progreso_habito_diario WHERE id_Habito IN (
            SELECT idHabito FROM habito WHERE id_Usuario = :idUsuario
        )
    )
""")
    suspend fun obtenerFechaUltimoProgreso(idUsuario: Long): Date?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(estadisticaGlobal: EstadisticaGlobal)

    @Update
    suspend fun update(estadisticaGlobal : EstadisticaGlobal)

    @Query("DELETE FROM estadistica_global WHERE idUsuario = :idUsuario")
    suspend fun deleteByUsuario(idUsuario: Long)

}