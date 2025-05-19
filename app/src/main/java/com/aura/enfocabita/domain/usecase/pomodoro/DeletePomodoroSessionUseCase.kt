package com.aura.enfocabita.domain.usecase.pomodoro

import com.aura.enfocabita.data.repository.PomodoroSesionRepository

/**
 * Caso de uso para eliminar una sesión Pomodoro por su ID.
 */
class DeletePomodoroSessionUseCase(
    private val repository: PomodoroSesionRepository
) {
    /**
     * Elimina la sesión con el ID especificado.
     *
     * @param id ID de la sesión a eliminar.
     * @return Número de filas eliminadas (debería ser 1 si fue exitoso).
     */
    suspend operator fun invoke(id: Long): Int {
        return repository.deleteSessionById(id)
    }
}