package com.aura.enfocabita.data.local.database.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(
    tableName = "progreso_habito_diario",
    foreignKeys = [
        ForeignKey(
            entity = Habito::class,
            parentColumns = ["idHabito"],
            childColumns = ["id_Habito"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["id_Habito"]), Index(value = ["fecha_registro"])]
)
@TypeConverters(ConvertType::class)
data class ProgresoHabitoDiario(
    @PrimaryKey(autoGenerate = true) val idPrHab: Long = 0L,
    @ColumnInfo(name = "id_Habito") val idHab: Long,
    @ColumnInfo(name = "fecha_registro") val fechaRegistro: Date,
    @ColumnInfo(name = "completado") val completado: Boolean
)
