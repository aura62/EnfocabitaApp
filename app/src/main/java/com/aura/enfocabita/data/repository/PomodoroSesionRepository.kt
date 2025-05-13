package com.aura.enfocabita.data.repository

import com.aura.enfocabita.data.local.database.entidades.PomodoroSesion
import com.aura.enfocabita.data.local.database.DAO.PomodoroSesionDao
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface PomodoroSesionRepository {
    /** Reactivo: observa todas las sesiones */
    fun observeAllSessions(): Flow<List<PomodoroSesion>>

    /** Reactivo: observa sesiones de un usuario */
    fun observeSessionsByUser(userId: Long): Flow<List<PomodoroSesion>>

    /** Carga puntual de todas las sesiones */
    suspend fun getAllSessions(): List<PomodoroSesion>

    /** Obtiene una sesión concreta por su ID */
    suspend fun getSessionById(id: Long): PomodoroSesion?

    /** Carga puntual de sesiones de un usuario */
    suspend fun getSessionsByUser(userId: Long): List<PomodoroSesion>

    /** Busca sesiones por nombre en un usuario (pasa sólo el texto, sin %%) */
    suspend fun searchSessionsByName(name: String, userId: Long): List<PomodoroSesion>

    /** Inserta o actualiza una sesión, devolviendo su ID */
    suspend fun saveSession(session: PomodoroSesion): Long

    /** Actualiza una sesión y devuelve número de filas afectadas */
    suspend fun updateSession(session: PomodoroSesion): Int

    /** Elimina una sesión por su ID */
    suspend fun deleteSessionById(id: Long): Int
}

class PomodoroSesionRepositoryImpl (private  val dao: PomodoroSesionDao) : PomodoroSesionRepository {
    override fun observeAllSessions(): Flow<List<PomodoroSesion>> = dao.observeAll()

    override fun observeSessionsByUser(userId: Long): Flow<List<PomodoroSesion>> = dao.observeByUsuario(userId)

    override suspend fun getAllSessions(): List<PomodoroSesion> = dao.getAllSesiones()

    override suspend fun getSessionById(id: Long): PomodoroSesion? = dao.getSesionById(id)

    override suspend fun getSessionsByUser(userId: Long): List<PomodoroSesion> = dao.getSesionesByUsuario(userId)

    override suspend fun searchSessionsByName(name: String, userId: Long): List<PomodoroSesion> = dao.searchSesionesByNombre(pattern = "%${name}%", userId = userId)

    override suspend fun saveSession(session: PomodoroSesion): Long = dao.insertSesion(sesion = session)

    override suspend fun updateSession(session: PomodoroSesion): Int = dao.updateSesion(sesion = session)

    override suspend fun deleteSessionById(id: Long): Int = dao.deleteSesionById(id = id)

}