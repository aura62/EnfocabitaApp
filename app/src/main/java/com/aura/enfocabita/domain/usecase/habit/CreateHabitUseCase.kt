package com.aura.enfocabita.domain.usecase.habit

import com.aura.enfocabita.data.local.database.entidades.PeriodUnit
import com.aura.enfocabita.data.local.database.entidades.TypeHab

import com.aura.enfocabita.data.local.database.entidades.Habito
import com.aura.enfocabita.data.repository.HabitoRepository
import java.util.Date

/**
 * Caso de uso para crear un nuevo hábito en la base de datos.
 */
class CreateHabitUseCase(
    private val repository: HabitoRepository
) {
    suspend operator fun invoke(
        userId: Long,
        titulo: String,
        tipo: String,
        frecuencia: Int,
        periodo: String
    ): Long {
        val habito = Habito(
            idUsuario = userId,
            titulo = titulo.trim(),
            tipo = runCatching { TypeHab.valueOf(tipo.uppercase()) }.getOrDefault(TypeHab.ADQUIRIR),
            frecuencia = frecuencia,
            periodo = runCatching { PeriodUnit.valueOf(periodo.uppercase()) }.getOrDefault(PeriodUnit.DIARIO),
            fechaRegistro = Date()
        )

        return repository.createHabit(habito)
    }
}