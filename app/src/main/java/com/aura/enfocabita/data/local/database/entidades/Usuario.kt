package com.aura.enfocabita.data.local.database.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val idUser: Int = 0,
    @ColumnInfo(name = "nombre") val name: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "contrasena") val  encryptedPassword: String?,
    @ColumnInfo(name = "fecha_registro") val fAlta: String?,
    @ColumnInfo(name = "tipo_usuario") val tipoUsr : UsrType
    )

enum class UsrType { ADMIN, STANDARD }
