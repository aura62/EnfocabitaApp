package com.aura.enfocabita.presentation.pomodoro

import com.aura.enfocabita.data.local.database.entidades.PomodoroSesion
import com.aura.enfocabita.data.repository.PomodoroSesionRepository

class GetPomodoroSessionByIdUseCase(
    private val repository: PomodoroSesionRepository
) {
    suspend operator fun invoke(id: Long): PomodoroSesion? {
        return repository.getSessionById(id)
    }
}