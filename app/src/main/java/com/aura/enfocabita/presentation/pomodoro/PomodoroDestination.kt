package com.aura.enfocabita.presentation.pomodoro

// PomodoroDestination.kt

sealed class PomodoroDestination(val ruta: String) {
    object Lista : PomodoroDestination("pomodoro/lista")
    object Form : PomodoroDestination("pomodoro/form")
    object Editar : PomodoroDestination("pomodoro/form/{pomodoroId}") {
        fun conId(id: Long) = "pomodoro/form/$id"
    }
}
