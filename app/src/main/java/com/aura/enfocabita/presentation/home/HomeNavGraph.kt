package com.aura.enfocabita.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aura.enfocabita.presentation.inicio.InicioScreen
import com.aura.enfocabita.presentation.inicio.InicioViewModel

// Gráfico de navegación principal que contiene todas las pantallas accesibles después del login
// Importa otras pantallas...

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeNavGraph(
    navController: NavHostController,
    userId: Long,
    inicioViewModel: InicioViewModel // Puedes usar hiltViewModel() o Koin
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = HomeDestination.Inicio.route,
            builder = {
                composable(HomeDestination.Inicio.route) {
                    InicioScreen(
                        userId = userId,
                        viewModel = inicioViewModel,
                        modifier = androidx.compose.ui.Modifier.padding(paddingValues)
                    )
                }
                composable(HomeDestination.Habitos.route) {
                    // HabitosScreen()
                }
                composable(HomeDestination.Pomodoro.route) {
                    // PomodoroScreen()
                }
                composable(HomeDestination.Calendario.route) {
                    // CalendarioScreen()
                }
                composable(HomeDestination.Estadisticas.route) {
                    // EstadisticasScreen()
                }
                composable(HomeDestination.Configuracion.route) {
                    // ConfiguracionScreen()
                }
            }
        )
    }
}