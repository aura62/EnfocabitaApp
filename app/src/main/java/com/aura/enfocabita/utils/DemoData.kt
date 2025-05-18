package com.aura.enfocabita.utils

data class DemoData(
    val usuario: DemoUsuario,
    val habitos: List<DemoHabito>,
    val progresos: List<DemoProgreso>,
    val pomodoros: List<DemoPomodoro> // ✅ nuevo
)

data class DemoUsuario(
    val nombre: String,
    val correo: String,
    val password: String
)

data class DemoHabito(
    val titulo: String,
    val tipo: String,
    val frecuencia: Int,
    val periodo: String
)

data class DemoProgreso(
    val habitoIndex: Int,
    val completado: Boolean
)

data class DemoPomodoro(
    val titulo: String,
    val duracion: Long,
    val dcorto: Long,
    val dlargo: Long,
    val sesiones: Int,
    val historial: List<DemoHistorialPomodoro>
)

data class DemoHistorialPomodoro(
    val fecha: String, // formato: "2025-05-16"
    val hora: String   // formato: "10:00:00"
)