package com.aura.enfocabita.domain.usecase.pomodoro

import com.aura.enfocabita.data.local.database.entidades.PomodoroSesion
import com.aura.enfocabita.data.repository.PomodoroSesionRepository
import kotlinx.coroutines.flow.Flow

/**
 * Caso de uso que obtiene las sesiones Pomodoro de un usuario en tiempo real.
 */
class GetPomodoroSessionsByUserUseCase(
    private val repository: PomodoroSesionRepository
) {
    /**
     * @param userId ID del usuario autenticado.
     * @return Flow<List<PomodoroSesion>> reactivo.
     */
    operator fun invoke(userId: Long): Flow<List<PomodoroSesion>> {
        return repository.observeSessionsByUser(userId)
    }
}