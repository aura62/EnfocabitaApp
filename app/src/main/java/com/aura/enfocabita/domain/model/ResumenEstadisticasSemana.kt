package com.aura.enfocabita.domain.model

data class ResumenEstadisticasSemana(
    val totalHabitos: Int,
    val completadosSemana: Int,
    val totalEsperado: Int,
    val cumplimientoPorcentaje: Float,
    val promedioDiario: Float,
    val diasActivos: Int
)
