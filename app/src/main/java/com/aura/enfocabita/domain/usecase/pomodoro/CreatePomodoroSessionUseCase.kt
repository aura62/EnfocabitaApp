package com.aura.enfocabita.domain.usecase.pomodoro

import com.aura.enfocabita.data.local.database.entidades.PomodoroSesion
import com.aura.enfocabita.data.repository.PomodoroSesionRepository

/**
 * Caso de uso para crear o guardar una sesión Pomodoro.
 */
class CreatePomodoroSessionUseCase(
    private val repository: PomodoroSesionRepository
) {
    /**
     * Guarda una sesión y devuelve su ID generado.
     *
     * @param sesion PomodoroSesion con los datos de la sesión.
     * @return ID de la sesión creada.
     */
    suspend operator fun invoke(sesion: PomodoroSesion): Long {
        return repository.saveSession(sesion)
    }
}
