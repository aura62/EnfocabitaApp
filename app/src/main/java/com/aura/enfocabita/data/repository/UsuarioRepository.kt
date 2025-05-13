package com.aura.enfocabita.data.repository

import com.aura.enfocabita.data.local.database.DAO.UsuarioDao
import com.aura.enfocabita.data.local.database.entidades.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioRepository {
    suspend fun getAllUsers(): List<Usuario>
    suspend fun getUserById(id: Long): Usuario?
    suspend fun findByEmail(email: String): Usuario?
    suspend fun createUser(user: Usuario): Long
    suspend fun updateUser(user: Usuario): Int
    suspend fun deleteUser(user: Usuario): Int

    // Streams para Compose/LiveData
    fun observeAllUsers(): Flow<List<Usuario>>
    fun observeUserById(id: Long): Flow<Usuario?>
}

class UsuarioRepositoryImpl (
    private val dao: UsuarioDao
) : UsuarioRepository {
    override fun observeAllUsers(): Flow<List<Usuario>> = dao.observeAll()
    override fun observeUserById(id: Long): Flow<Usuario?> = dao.observeById(id)
    override suspend fun getAllUsers(): List<Usuario> = dao.getAll()
    override suspend fun getUserById(id: Long): Usuario? = dao.getById(id)
    override suspend fun findByEmail(email: String): Usuario? = dao.findByCorreo(email)
    override suspend fun createUser(user: Usuario): Long = dao.insert(user)
    override suspend fun updateUser(user: Usuario): Int = dao.update(user)
    override suspend fun deleteUser(user: Usuario): Int = dao.delete(user)

}
