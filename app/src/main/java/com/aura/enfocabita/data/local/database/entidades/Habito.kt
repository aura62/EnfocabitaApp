package com.aura.enfocabita.data.local.database.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(
    tableName = "habito",
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
data class Habito(
    @PrimaryKey(autoGenerate = true) val idHabito: Long = 0L,
    @ColumnInfo(name = "id_Usuario") val idUsuario: Long,
    @ColumnInfo(name = "nombre") val titulo: String,
    @ColumnInfo(name = "tipo") val tipo: TypeHab,
    @ColumnInfo(name = "frecuencia") val frecuencia: Int,
    @ColumnInfo(name = "periodo") val periodo: PeriodUnit = PeriodUnit.DIARIO,
    @ColumnInfo(name = "fecha_registro") val fechaRegistro: Date
)

enum class TypeHab { ADQUIRIR, MANTENER, ABANDONAR }

enum class PeriodUnit { DIARIO, SEMANAL, MENSUAL }

