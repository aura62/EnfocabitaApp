package com.aura.enfocabita.domain.usecase.pomodoro

import com.aura.enfocabita.data.local.database.entidades.PomodoroSesion
import com.aura.enfocabita.data.repository.PomodoroSesionRepository

/**
 * Caso de uso que actualiza una sesión Pomodoro existente.
 */
class UpdatePomodoroSessionUseCase(
    private val repository: PomodoroSesionRepository
) {
    /**
     * Ejecuta la actualización de la sesión dada.
     *
     * @param sesion La sesión modificada a guardar.
     * @return Número de filas afectadas (debería ser 1 si fue exitoso).
     */
    suspend operator fun invoke(sesion: PomodoroSesion): Int {
        return repository.updateSession(sesion)
    }
}