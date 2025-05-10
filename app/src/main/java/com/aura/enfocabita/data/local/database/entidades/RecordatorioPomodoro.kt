package com.aura.enfocabita.data.local.database.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(
    tableName = "recordatorios_pomodoro",
    foreignKeys = [
        ForeignKey(
            entity = PomodoroSesion::class,
            parentColumns = ["idPomodoro"],
            childColumns = ["id_pomodoro"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("id_pomodoro")]
)
@TypeConverters(ConvertType::class)
data class RecordatorioPomodoro(
    @PrimaryKey(autoGenerate = true) val idRdPomodoro: Long = 0L,
    @ColumnInfo(name = "id_pomodoro") val idPomodoro: Long,
    @ColumnInfo(name = "hora_inicio") val hora: Date,
    @ColumnInfo(name = "mensaje") val mensaje: String
)

