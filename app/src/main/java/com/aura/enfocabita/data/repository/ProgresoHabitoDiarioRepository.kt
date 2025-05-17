package com.aura.enfocabita.data.repository

import com.aura.enfocabita.data.local.database.DAO.ProgresoHabitoDiarioDao
import com.aura.enfocabita.data.local.database.entidades.ProgresoHabitoDiario
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ProgresoHabitoDiarioRepository {
    fun observeProgressByHabit(habitId: Long): Flow<List<ProgresoHabitoDiario>>
    suspend fun getAllProgress(): List<ProgresoHabitoDiario>
    suspend fun getProgressByHabit(habitId: Long): List<ProgresoHabitoDiario>
    suspend fun getProgressByHabitAndDate(habitId: Long, date: Date): ProgresoHabitoDiario?
    suspend fun saveProgress(progress: ProgresoHabitoDiario): ProgresoHabitoDiario?
    suspend fun deleteProgressById(id: Long): Int
    suspend fun getHabitsCompletsToday(userId: Long, start: Date, end: Date): Int
    suspend fun getHabitsTotalToday(userId: Long, start: Date, end: Date): Int
    fun observeAllProgress(): Flow<List<ProgresoHabitoDiario>>
}

class ProgresoHabitoDiarioRepositoryImpl (
    private val dao: ProgresoHabitoDiarioDao
) : ProgresoHabitoDiarioRepository {
    override fun observeProgressByHabit(habitId: Long): Flow<List<ProgresoHabitoDiario>> = dao.observeProgressByHabit(habitId)
    override suspend fun getAllProgress(): List<ProgresoHabitoDiario> = dao.getAllProgress()
    override suspend fun getProgressByHabit(habitId: Long): List<ProgresoHabitoDiario> = dao.getProgressByHabit(habitId)
    override suspend fun getProgressByHabitAndDate(habitId: Long, date: Date): ProgresoHabitoDiario? = dao.getProgressByHabitAndDate(habitId = habitId, date = date)
    override suspend fun saveProgress(progress: ProgresoHabitoDiario): ProgresoHabitoDiario? = dao.upsertAndGetByHabitAndDate(progress = progress)
    override suspend fun deleteProgressById(id: Long): Int = dao.deleteProgressById(id = id)
    override suspend fun getHabitsCompletsToday(userId: Long, start: Date, end: Date): Int = dao.getHabitsCompletsToday(userId,start,end)
    override suspend fun getHabitsTotalToday(userId: Long, start: Date, end: Date): Int = dao.getHabitsTotalToday(userId,start,end)
    override fun observeAllProgress(): Flow<List<ProgresoHabitoDiario>> = dao.observeAllProgress()
}