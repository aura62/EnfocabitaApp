package com.aura.enfocabita.presentation.inicio

import java.util.Date

data class InicioUiState(
    val isLoading: Boolean = true,
    val habitosCompletados: Int = 0,
    val habitosTotales: Int = 0,
    val minutosPomodoro: Long = 0,
    val ultimaActividad: Date? = null,
    val nombreUsuario: String = ""
)
