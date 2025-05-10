package com.aura.enfocabita.data.local.database.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "estadistica_habito",
    foreignKeys = [
        ForeignKey(
            entity = Habito::class,
            parentColumns = ["idHabito"],
            childColumns = ["idHabito"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("idHabito"), Index("totalCompletados")]
)
data class EstadisticaHabito(
    @PrimaryKey(autoGenerate = true) val idEstHabito: Long = 0L,
    @ColumnInfo(name = "idHabito") val idHabito: Long,
    @ColumnInfo(name = "totalCompletados") val completados: Int,
    @ColumnInfo(name = "promedioCompletados") val promedio: Float
)

