package com.aura.enfocabita.data.local.database.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(
    tableName = "estadistica_global",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["idUsuario"],
            childColumns = ["idUsuario"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("idUsuario"), Index("fecha_ultimo_progreso")]
)
@TypeConverters(ConvertType::class)
data class EstadisticaGlobal(
    @PrimaryKey(autoGenerate = true) val idEstGlobal: Long = 0L,
    @ColumnInfo(name = "idUsuario") val idUsuario: Long,
    @ColumnInfo(name = "total_habitos_completados") val totalHabitosCompletados: Int,
    @ColumnInfo(name = "promedio_habitos_completados") val promedioHabitosCompletados: Float,
    @ColumnInfo(name = "total_pomodoros_completados") val totalPomodorosCompletados: Int,
    @ColumnInfo(name = "promedio_pomodoros_diarios") val promedioPomodorosDiarios: Float,
    @ColumnInfo(name = "total_dias_activos") val totalDiasActivos: Int,
    @ColumnInfo(name = "fecha_ultimo_progreso") val fechaUltimoProgreso: Date
)

