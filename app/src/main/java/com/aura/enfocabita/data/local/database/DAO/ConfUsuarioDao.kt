package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.ConfiguracionUsuario

@Dao
interface ConfUsuarioDao {

        @Query("SELECT * FROM configuracion_usuario")
        suspend fun getAll(): List<ConfiguracionUsuario>

        @Query("SELECT * FROM configuracion_usuario WHERE idUsuario = :id LIMIT 1")
        suspend fun getByUserId(id: Long): ConfiguracionUsuario?

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(confUsuario: ConfiguracionUsuario)

        @Update
        suspend fun update(confUsuario: ConfiguracionUsuario)

        @Delete
        suspend fun delete(confUsuario: ConfiguracionUsuario)

}