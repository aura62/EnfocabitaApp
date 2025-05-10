package com.aura.enfocabita.data.local.database.entidades
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "usuario")
@TypeConverters(ConvertType::class)
data class Usuario(
    @PrimaryKey(autoGenerate = true) val idUsuario: Long = 0L,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "correo_electronico") val correo: String,
    @ColumnInfo(name = "contrasena") val encryptedPassword: String,
    @ColumnInfo(name = "fecha_registro") val fechaRegistro: Date,
    @ColumnInfo(name = "tipo_usuario") val tipoUsuario: UserType
)

enum class UserType { ADMIN, STANDARD }



