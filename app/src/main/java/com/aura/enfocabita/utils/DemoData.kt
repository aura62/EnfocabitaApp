package com.aura.enfocabita.utils

data class DemoData(
    val usuario: DemoUsuario,
    val habitos: List<DemoHabito>,
    val progresos: List<DemoProgreso>
)

data class DemoUsuario(
    val nombre: String,
    val correo: String,
    val password: String
)

data class DemoHabito(
    val titulo: String,
    val tipo: String,     // "ADQUIRIR", "MANTENER", "ABANDONAR"
    val frecuencia: Int,
    val periodo: String   // "DIARIO", "SEMANAL", etc.
)

data class DemoProgreso(
    val habitoIndex: Int,
    val completado: Boolean
)