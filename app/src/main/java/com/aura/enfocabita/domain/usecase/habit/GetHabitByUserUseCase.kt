package com.aura.enfocabita.domain.usecase.habit

import com.aura.enfocabita.data.local.database.entidades.Habito
import com.aura.enfocabita.data.repository.HabitoRepository
import kotlinx.coroutines.flow.Flow

/**
 * Caso de uso que obtiene en tiempo real la lista de hábitos de un usuario.
 */
class GetHabitsByUserUseCase(
    private val repository: HabitoRepository
) {
    /**
     * Devuelve un flujo reactivo con los hábitos del usuario dado.
     *
     * @param userId ID del usuario autenticado.
     * @return Flow<List<Habito>> reactivo.
     */
    operator fun invoke(userId: Long): Flow<List<Habito>> {
        return repository.observeHabitsByUser(userId)
    }
}