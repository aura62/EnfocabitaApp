package com.aura.enfocabita.domain.usecase.pomodoro

sealed class PomodoroFase {
    data class Trabajo(val sesionActual: Int) : PomodoroFase()
    data class DescansoCorto(val sesionActual: Int) : PomodoroFase()
    object DescansoLargo : PomodoroFase()
}