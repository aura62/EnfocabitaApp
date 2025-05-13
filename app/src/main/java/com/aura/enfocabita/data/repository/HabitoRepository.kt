package com.aura.enfocabita.data.repository

import com.aura.enfocabita.data.local.database.DAO.HabitoDao
import com.aura.enfocabita.data.local.database.entidades.Habito
import kotlinx.coroutines.flow.Flow

interface HabitoRepository {
    fun observeHabitsByUser(userId: Long): Flow<List<Habito>>
    fun observeAllHabits(): Flow<List<Habito>>
    suspend fun getAllHabits(): List<Habito>
    suspend fun getHabitById(id: Long): Habito?
    suspend fun getHabitsByUser(userId: Long): List<Habito>
    suspend fun searchHabits(namePattern: String, userId: Long): List<Habito>
    suspend fun createHabit(habit: Habito): Long
    suspend fun updateHabit(habit: Habito): Int
    suspend fun deleteHabitById(id: Long): Int
    suspend fun upsertAndReload(habit: Habito): List<Habito>
}

class HabitoRepositoryImpl(
    private val dao: HabitoDao
) : HabitoRepository {
    override fun observeHabitsByUser(userId: Long): Flow<List<Habito>> = dao.observeByUsuario(userId)
    override fun observeAllHabits(): Flow<List<Habito>> = dao.observeAll()
    override suspend fun getAllHabits(): List<Habito> = dao.getAllHabitos()
    override suspend fun getHabitById(id: Long): Habito? = dao.getHabitoById(id)
    override suspend fun getHabitsByUser(userId: Long): List<Habito> = dao.getHabitosByUsuario(userId)
    override suspend fun searchHabits(namePattern: String, userId: Long): List<Habito> = dao.buscarHabitosPorNombre("%${namePattern}%", userId)
    override suspend fun createHabit(habit: Habito): Long = dao.insertHabito(habit)
    override suspend fun updateHabit(habit: Habito): Int = dao.updateHabito(habit)
    override suspend fun deleteHabitById(id: Long): Int = dao.deleteHabitoById(id)
    override suspend fun upsertAndReload(habit: Habito): List<Habito> = dao.upsertAndGetAll(habit)
}