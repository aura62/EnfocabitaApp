package com.aura.enfocabita.presentation.home
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class HomeDestination(

    val route: String,
    val label: String,
    val icon: ImageVector // Puedes usar @DrawableRes o pasar directamente el icono composable si usas Icon()
) {
    object Inicio : HomeDestination("inicio", "Inicio", Icons.Filled.Home)
    object Habitos : HomeDestination("habitos", "Hábitos", Icons.Filled.CheckCircle)
    object Pomodoro : HomeDestination("pomodoro", "Pomodoro", Icons.Filled.AccessTimeFilled)
    object Calendario : HomeDestination("calendario", "Calendario", Icons.Filled.CalendarToday)
    object Estadisticas : HomeDestination("estadisticas", "Estadísticas", Icons.Filled.BarChart)
    object Configuracion : HomeDestination("configuracion", "Configuración", Icons.Filled.AccountCircle)

    companion object {
        val items = listOf(Inicio, Habitos, Pomodoro, Calendario, Estadisticas, Configuracion)
    }
}