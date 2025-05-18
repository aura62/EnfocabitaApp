package com.aura.enfocabita.domain.usecase.habit

import com.aura.enfocabita.data.local.database.entidades.Habito
import com.aura.enfocabita.data.repository.HabitoRepository

/**
 * Caso de uso que actualiza un hábito existente en la base de datos.
 */
class UpdateHabitUseCase(
    private val repository: HabitoRepository
) {
    /**
     * Ejecuta la actualización del hábito dado.
     *
     * @param habito El hábito ya modificado que se desea guardar.
     * @return Número de filas afectadas (debería ser 1 si fue exitoso).
     */
    suspend operator fun invoke(habito: Habito): Int {
        return repository.updateHabit(habito)
    }
}