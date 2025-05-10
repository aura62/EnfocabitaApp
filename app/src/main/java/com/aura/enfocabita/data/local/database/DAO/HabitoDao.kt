package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.Habito
import com.aura.enfocabita.data.local.database.entidades.Usuario

@Dao
interface HabitoDao {

    @Query("SELECT * FROM habito")
    suspend fun getAll(): List<Habito>

    @Query("SELECT * FROM habito WHERE idHabito = :idHab LIMIT 1")
    suspend fun getById(idHab: Long): Habito?

    @Query("SELECT * FROM habito WHERE id_Usuario = :idUsr")
    suspend fun getByUsuario(idUsr: Long): List<Habito>

    @Query("SELECT * FROM habito WHERE nombre LIKE '%' || :nombre || '%' AND id_Usuario = :idUsr")
    suspend fun buscarPorNombre(nombre: String, idUsr: Long): List<Habito>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habito: Habito)

    @Query("DELETE FROM habito WHERE idHabito = :id")
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(habito: Habito)


}