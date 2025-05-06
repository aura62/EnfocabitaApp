package com.aura.enfocabita.data.local.database.entidades

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Habito(
    @PrimaryKey(autoGenerate = true) val idHab: Int = 0,
    @ColumnInfo(name = "nombre") val nameHab: String?,
    @ColumnInfo(name = "tipo") val typ: String?,
    @ColumnInfo(name = "frecuencia") val  frecuency: Int?,
    @ColumnInfo(name = "fecha_registro") val fAlta: String?,
    @ColumnInfo(name = "idUsuario") val idUser: String?,
)
