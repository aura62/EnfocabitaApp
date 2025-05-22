package com.aura.enfocabita.domain.usecase.habit

import android.os.Build
import androidx.annotation.RequiresApi
import com.aura.enfocabita.data.local.database.DAO.ProgresoHabitoDiarioDao
import com.aura.enfocabita.data.local.database.entidades.ProgresoHabitoDiario
import com.aura.enfocabita.utils.obtenerRangoDelDia
import java.util.Date

class ToggleHabitCompletionUseCase(
    private val progresoDao: ProgresoHabitoDiarioDao
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun obtenerEstadoActual(habitId: Long, fecha: Date = Date()): Boolean {
        val (start, end) = obtenerRangoDelDia()
        return progresoDao.getProgressByHabitAndDateRange(habitId, start, end)?.completado == true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun actualizarEstado(habitId: Long, completado: Boolean, fecha: Date = Date()) {
        val (start, end) = obtenerRangoDelDia()
        val existente = progresoDao.getProgressByHabitAndDateRange(habitId, start, end)

        val nuevoProgreso = existente?.copy(completado = completado)
            ?: ProgresoHabitoDiario(
                idHab = habitId,
                fechaRegistro = Date(),
                completado = completado
            )

        if (existente != null) {
            progresoDao.updateProgress(nuevoProgreso)
        } else {
            progresoDao.insertProgress(nuevoProgreso)
        }
    }
}

