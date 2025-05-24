package com.aura.enfocabita.domain.usecase.stats

import com.aura.enfocabita.data.repository.HabitoRepository
import com.aura.enfocabita.data.repository.ProgresoHabitoDiarioRepository
import com.aura.enfocabita.domain.model.ResumenEstadisticasSemana
import java.util.*

class GetResumenEstadisticasSemanaUseCase(
    private val habitoRepo: HabitoRepository,
    private val progresoRepo: ProgresoHabitoDiarioRepository
) {
    suspend operator fun invoke(userId: Long, inicio: Date, fin: Date): ResumenEstadisticasSemana {
        val habitos = habitoRepo.getHabitsByUser(userId)
        val totalHabitos = habitos.size

        val completados = progresoRepo.getHabitsCompletsToday(userId, inicio, fin)
        val totalEsperado = progresoRepo.getHabitsTotalToday(userId, inicio, fin)

        val porcentaje = if (totalEsperado > 0) (completados.toFloat() / totalEsperado) * 100 else 0f
        val promedio = completados.toFloat() / 7

        val diasActivos = completados / (if (totalHabitos > 0) totalHabitos else 1)

        return ResumenEstadisticasSemana(
            totalHabitos = totalHabitos,
            completadosSemana = completados,
            totalEsperado = totalEsperado,
            cumplimientoPorcentaje = porcentaje,
            promedioDiario = promedio,
            diasActivos = diasActivos
        )
    }
}