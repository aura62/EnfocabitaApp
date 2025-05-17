package com.aura.enfocabita.domain.usecase.inicio

import com.aura.enfocabita.data.repository.EstadisticaGlobalRepository
import java.util.Date

class GetLastActivityDateUseCase(
    private val estadisticaGlobalRepository: EstadisticaGlobalRepository
) {
    suspend operator fun invoke(userId: Long): Date? {
        return estadisticaGlobalRepository.getLastProgressDate(userId)
    }
}