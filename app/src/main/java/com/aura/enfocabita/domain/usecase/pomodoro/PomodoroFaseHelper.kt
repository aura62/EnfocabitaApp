package com.aura.enfocabita.domain.usecase.pomodoro

import com.aura.enfocabita.data.local.database.entidades.PomodoroSesion

fun avanzarAFaseSiguiente(
    faseActual: PomodoroFase,
    sesion: PomodoroSesion
): PomodoroFase {
    return when (faseActual) {
        is PomodoroFase.Trabajo -> {
            if (faseActual.sesionActual < sesion.numSesiones) {
                PomodoroFase.DescansoCorto(faseActual.sesionActual)
            } else {
                PomodoroFase.DescansoLargo
            }
        }

        is PomodoroFase.DescansoCorto -> {
            PomodoroFase.Trabajo(faseActual.sesionActual + 1)
        }

        is PomodoroFase.DescansoLargo -> {
            PomodoroFase.Trabajo(1) // reinicia el ciclo
        }
    }
}