package com.aura.enfocabita.domain.usecase.stats

import com.aura.enfocabita.data.repository.HabitoRepository
import com.aura.enfocabita.data.local.database.entidades.TypeHab

class GetHabitCountByTypeUseCase(
    private val habitoRepo: HabitoRepository
) {
    suspend operator fun invoke(userId: Long): Map<TypeHab, Int> {
        val habitos = habitoRepo.getHabitsByUser(userId)
        return habitos.groupingBy { it.tipo }.eachCount()
    }
}