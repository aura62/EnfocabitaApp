package com.aura.enfocabita.UI.navigation

import androidx.compose.foundation.layout.padding

// ui/navigation/HomeNavGraph.kt

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*

@Composable
fun HomeNavGraph() {
    val navController = rememberNavController()
    val items = listOf(
        HomeDestination.Dashboard,
        HomeDestination.Habitos,
        HomeDestination.Calendario,
        HomeDestination.Pomodoro,
        HomeDestination.Estadisticas,
        HomeDestination.Configuracion
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    // evita crear muchas copias en el backstack
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeDestination.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(HomeDestination.Dashboard.route) {
                DashboardScreen()
            }
            composable(HomeDestination.Habitos.route) {
                HabitosScreen()
            }

            composable(HomeDestination.Calendario.route) {
                CalendarioScreen()
            }

            composable(HomeDestination.Pomodoro.route) {
                PomodoroScreen()
            }
            composable(HomeDestination.Estadisticas.route) {
                EstadisticasScreen()
            }
            composable(HomeDestination.Configuracion.route) {
                ConfiguracionScreen()
            }
        }
    }
}