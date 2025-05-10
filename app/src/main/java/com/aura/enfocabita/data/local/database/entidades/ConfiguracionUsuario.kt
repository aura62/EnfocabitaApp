package com.aura.enfocabita.data.local.database.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "configuracion_usuario",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["idUsuario"],
            childColumns = ["idUsuario"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ConfiguracionUsuario(
    @PrimaryKey @ColumnInfo(name = "idUsuario") val idUsuario: Long,
    @ColumnInfo(name = "modo_oscuro") val modoOscuro: Boolean,
    @ColumnInfo(name = "idioma") val idioma: String
)
