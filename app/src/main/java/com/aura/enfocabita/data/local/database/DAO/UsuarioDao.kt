package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.Usuario

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuario")
    suspend fun getAll(): List<Usuario>

    @Query("SELECT * FROM usuario WHERE idUsuario IN (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<Usuario>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: Usuario)

    @Query("SELECT * FROM usuario WHERE correo_electronico = :correo LIMIT 1")
    suspend fun findByCorreo(correo: String): Usuario?

    @Update
    suspend fun update(usuario: Usuario)

    @Delete
    suspend fun delete(usuario: Usuario)
}
