package com.aura.enfocabita.data.local.database.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(
    tableName = "pomodoro_sesion",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["idUsuario"],
            childColumns = ["id_Usuario"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("id_Usuario")]
)
@TypeConverters(ConvertType::class)
data class PomodoroSesion(
    @PrimaryKey(autoGenerate = true) val idPomodoro: Long = 0L,
    @ColumnInfo(name = "id_Usuario") val idUsuario: Long,
    @ColumnInfo(name = "nombre_tarea") val tituloTarea: String,
    @ColumnInfo(name = "duracion_trabajo") val duracion_ms: Long,
    @ColumnInfo(name = "descanso_corto") val dcorto_ms: Long,
    @ColumnInfo(name = "descanso_largo") val dLargo_ms: Long,
    @ColumnInfo(name = "numero_sesiones") val numSesiones: Int,
    @ColumnInfo(name = "fecha_creacion") val fechaCreacion: Date = Date()
)
