package com.aura.enfocabita.domain.usecase.habit

import com.aura.enfocabita.data.repository.HabitoRepository

/**
 * Caso de uso que elimina un hábito por su ID.
 */
class DeleteHabitUseCase(
    private val repository: HabitoRepository
) {
    /**
     * Elimina el hábito con el ID especificado.
     *
     * @param habitId ID del hábito a eliminar.
     * @return Número de filas eliminadas (debería ser 1 si fue exitoso).
     */
    suspend operator fun invoke(habitId: Long): Int {
        return repository.deleteHabitById(habitId)
    }
}