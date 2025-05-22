package com.aura.enfocabita.domain.usecase.habit

import com.aura.enfocabita.data.local.database.DAO.ProgresoHabitoDiarioDao
import com.aura.enfocabita.data.local.database.entidades.ProgresoHabitoDiario
import java.util.Date

class ToggleHabitCompletionUseCase(
    private val progresoDao: ProgresoHabitoDiarioDao
) {
    suspend fun obtenerEstadoActual(habitId: Long, fecha: Date = Date()): Boolean {
        return progresoDao.getProgressByHabitAndDate(habitId, fecha)?.completado == true
    }

    suspend fun actualizarEstado(habitId: Long, completado: Boolean, fecha: Date = Date()) {
        val existente = progresoDao.getProgressByHabitAndDate(habitId, fecha)
        val nuevoProgreso = if (existente != null) {
            existente.copy(completado = completado)
        } else {
            ProgresoHabitoDiario(
                idHab = habitId,
                fechaRegistro = fecha,
                completado = completado
            )
        }
        progresoDao.insertProgress(nuevoProgreso)
    }
}
