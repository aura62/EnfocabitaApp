package com.aura.enfocabita.data.repository

import com.aura.enfocabita.data.local.database.DAO.ConfUsuarioDao
import com.aura.enfocabita.data.local.database.entidades.ConfiguracionUsuario
import kotlinx.coroutines.flow.Flow

interface ConfUsuarioRepository {
    fun observeConfig(id: Long): Flow<ConfiguracionUsuario?>
    suspend fun getConfig(id: Long): ConfiguracionUsuario?
    suspend fun saveConfig(config: ConfiguracionUsuario): Long
    suspend fun deleteConfig(config: ConfiguracionUsuario): Int
    suspend fun updateConfig(config: ConfiguracionUsuario) : Int
}

class ConfUsuarioRepositoryImpl(
    private val dao: ConfUsuarioDao
) : ConfUsuarioRepository {
    override fun observeConfig(id: Long): Flow<ConfiguracionUsuario?> = dao.observeConfigForUser(id)
    override suspend fun getConfig(id: Long): ConfiguracionUsuario? = dao.getConfigForUser(id)
    override suspend fun saveConfig(config: ConfiguracionUsuario): Long = dao.insertConfig(config)
    override suspend fun deleteConfig(config: ConfiguracionUsuario): Int = dao.deleteConfig(config)
    override suspend fun updateConfig(config: ConfiguracionUsuario): Int = dao.updateConfig(config)
}