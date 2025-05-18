package com.aura.enfocabita.domain.usecase.habit

import com.aura.enfocabita.data.local.database.entidades.Habito
import com.aura.enfocabita.data.repository.HabitoRepository

class GetHabitByIdUseCase(
    private val repository: HabitoRepository
) {
    suspend operator fun invoke(id: Long): Habito? {
        return repository.getHabitById(id)
    }
}
