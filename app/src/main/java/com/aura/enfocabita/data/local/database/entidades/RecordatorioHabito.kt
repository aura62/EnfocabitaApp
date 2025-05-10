package com.aura.enfocabita.data.local.database.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(
    tableName = "recordatorios_habito",
    foreignKeys = [
        ForeignKey(
            entity = Habito::class,
            parentColumns = ["idHabito"],
            childColumns = ["idHabito"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("idHabito")]
)
@TypeConverters(ConvertType::class)
data class RecordatorioHabito(
    @PrimaryKey(autoGenerate = true) val idRecordatorioHabito: Long = 0L,
    @ColumnInfo(name = "idHabito") val idHabito: Long,
    @ColumnInfo(name = "hora") val hora: Date,
    @ColumnInfo(name = "mensaje") val mensaje: String
)
