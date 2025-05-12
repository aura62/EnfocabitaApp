package com.aura.enfocabita.data.local.database.DAO

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.aura.enfocabita.data.local.database.entidades.Habito

@Dao
interface HabitoDao {

    /** Reactivo: observa todos los hábitos */
    @Query("SELECT * FROM habito")
    fun observeAll(): Flow<List<Habito>>

    /** Carga puntual de todos los hábitos */
    @Query("SELECT * FROM habito")
    suspend fun getAllHabitos(): List<Habito>

    /** Obtiene un hábito por su ID */
    @Query("SELECT * FROM habito WHERE idHabito = :id LIMIT 1")
    suspend fun getHabitoById(id: Long): Habito?

    /** Reactivo: observa hábitos de un usuario */
    @Query("SELECT * FROM habito WHERE id_Usuario = :userId")
    fun observeByUsuario(userId: Long): Flow<List<Habito>>

    /** Carga puntual de hábitos de un usuario */
    @Query("SELECT * FROM habito WHERE id_Usuario = :userId")
    suspend fun getHabitosByUsuario(userId: Long): List<Habito>

    /** Busca hábitos por nombre dentro de un usuario (pattern debe llevar %…%) */
    @Query("SELECT * FROM habito WHERE nombre LIKE :pattern AND id_Usuario = :userId")
    suspend fun buscarHabitosPorNombre(pattern: String, userId: Long): List<Habito>

    /** Inserta o reemplaza, devolviendo el ID generado */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabito(habito: Habito): Long

    /** Actualiza y devuelve el número de filas modificadas */
    @Update
    suspend fun updateHabito(habito: Habito): Int

    /** Elimina por ID y devuelve el número de filas borradas */
    @Query("DELETE FROM habito WHERE idHabito = :id")
    suspend fun deleteHabitoById(id: Long): Int

    /**
     * Transacción compuesta: inserta/reemplaza y devuelve la lista actualizada
     */
    @Transaction
    suspend fun upsertAndGetAll(habito: Habito): List<Habito> {
        insertHabito(habito)
        return getAllHabitos()
    }
}