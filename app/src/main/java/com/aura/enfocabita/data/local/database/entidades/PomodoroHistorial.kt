package com.aura.enfocabita.data.local.database.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(
    tableName = "pomodoro_historial",
    foreignKeys = [
        ForeignKey(
            entity = PomodoroSesion::class,
            parentColumns = ["idPomodoro"],
            childColumns = ["id_pomodoro"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("id_pomodoro"), Index("fecha"), Index("hora_inicio")]
)
@TypeConverters(ConvertType::class)
data class PomodoroHistorial(
    @PrimaryKey(autoGenerate = true) val idHistorial: Long = 0L,
    @ColumnInfo(name = "id_pomodoro") val idPomodoro: Long,
    @ColumnInfo(name = "fecha") val fecha: Date,
    @ColumnInfo(name = "hora_inicio") val horaInicio: Date
)
