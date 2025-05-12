package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuario")
    suspend fun getAll(): List<Usuario>

    @Query("SELECT * FROM usuario WHERE idUsuario IN (:userIds)")
    suspend fun loadAllByIds(userIds: LongArray): List<Usuario>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: Usuario) : Long

    @Query("SELECT * FROM usuario WHERE correo_electronico = :correo LIMIT 1")
    suspend fun findByCorreo(correo: String): Usuario?

    @Query("SELECT * FROM usuario")
    fun observeAll(): Flow<List<Usuario>>


    @Update
    suspend fun update(usuario: Usuario) : Int

    @Delete
    suspend fun delete(usuario: Usuario) : Int
}
