package com.aura.enfocabita.data.local.database.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "estadistica_pomodoro",
    foreignKeys = [
        ForeignKey(
            entity = PomodoroSesion::class,
            parentColumns = ["idPomodoro"],
            childColumns = ["idPomodoro"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("idPomodoro"), Index("promedioDiario")]
)
data class EstadisticaPomodoro(
    @PrimaryKey(autoGenerate = true) val idEstPomodoro: Long = 0L,
    @ColumnInfo(name = "idPomodoro") val idPomodoro: Long,
    @ColumnInfo(name = "totalSesiones") val totalSesiones: Int,
    @ColumnInfo(name = "promedioDiario") val promedio: Float
)
