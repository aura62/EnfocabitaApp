package com.aura.enfocabita.UI.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// ui/screens/DashboardScreen.kt
@Composable
fun DashboardScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("📊 Bienvenido al Dashboard")
    }
}

// ui/screens/HabitosScreen.kt
@Composable
fun HabitosScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("✅ Aquí van tus hábitos")
    }
}

@Composable
fun CalendarioScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("✅ Aquí va tu calendario")
    }
}

@Composable
fun PomodoroScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("✅ Aquí van tu temporizador")
    }
}

@Composable
fun EstadisticasScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("✅ Aquí va reflejado tu progreso o estadisticas")
    }
}

@Composable
fun ConfiguracionScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("✅ Aquí van tus hábitos")
    }
}