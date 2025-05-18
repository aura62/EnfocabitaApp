package com.aura.enfocabita.presentation.habit

import com.aura.enfocabita.data.local.database.entidades.Habito

data class HabitUiState(
    val isLoading: Boolean = false,
    val habitos: List<Habito> = emptyList(),
    val mensaje: String? = null
)