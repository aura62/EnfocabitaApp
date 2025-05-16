package com.aura.enfocabita.UI.navigation

// ui/navigation/HomeDestinations.kt

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class HomeDestination(val route: String, val title: String, val icon: ImageVector) {
    object Dashboard    : HomeDestination("dashboard",    "Inicio",     Icons.Filled.Home)
    object Habitos      : HomeDestination("habitos",      "Hábitos",    Icons.Filled.CheckCircle)
    object Pomodoro     : HomeDestination("pomodoro",     "Pomodoro",   Icons.Filled.Timer)
    object Calendario : HomeDestination("calendario",     "Calendario", Icons.Filled.CalendarToday)
    object Estadisticas : HomeDestination("estadisticas", "Estadísticas", Icons.Filled.BarChart)
    object Configuracion: HomeDestination("configuracion","Perfil",     Icons.Filled.Settings)
}
