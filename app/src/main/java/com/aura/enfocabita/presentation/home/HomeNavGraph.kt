package com.aura.enfocabita.presentation.home


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aura.enfocabita.presentation.inicio.InicioScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import com.aura.enfocabita.presentation.auth.AuthViewModel
import com.aura.enfocabita.presentation.configuration.ConfigurationScreen
import com.aura.enfocabita.presentation.habit.HabitoDestinos
import com.aura.enfocabita.presentation.habit.habitNavGraph
import com.aura.enfocabita.presentation.inicio.InicioViewModel
import com.aura.enfocabita.presentation.pomodoro.PomodoroDestination
import com.aura.enfocabita.presentation.pomodoro.pomodoroNavGraph
import com.aura.enfocabita.presentation.calendar.CalendarScreen
import com.aura.enfocabita.presentation.stats.StatsScreen

// Gráfico de navegación principal que contiene todas las pantallas accesibles después del login
// Importa otras pantallas...

@ExperimentalMaterial3Api
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeNavGraph(
    navController: NavHostController,
    userId: Long,
    inicioViewModel: InicioViewModel,
    authViewModel: AuthViewModel
) {
    val currentEntry = navController.currentBackStackEntryAsState().value // ✅ MOVER AQUÍ

    LaunchedEffect(currentEntry) {
        if (currentEntry?.destination?.route == HomeDestination.Inicio.route) {
            inicioViewModel.cargarResumen(userId)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeDestination.Inicio.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(HomeDestination.Inicio.route) {
                val currentEntry = navController.currentBackStackEntryAsState().value

                LaunchedEffect(currentEntry) {
                    if (currentEntry?.destination?.route == HomeDestination.Inicio.route) {
                        inicioViewModel.reiniciarEstado()
                        inicioViewModel.cargarResumen(userId)
                    }
                }

                InicioScreen(
                    userId = userId,
                    viewModel = inicioViewModel,
                    navController = navController
                )
            }

            navigation(
                startDestination = HabitoDestinos.Lista.ruta,
                route = HomeDestination.Habitos.route
            ) {
                habitNavGraph(userId = userId, navController = navController)
            }

            navigation(
                startDestination = PomodoroDestination.Lista.ruta,
                route = HomeDestination.Pomodoro.route
            ) {
                pomodoroNavGraph(userId = userId, navController = navController)
            }

            composable(HomeDestination.Calendario.route) {
                CalendarScreen(userId = userId)
            }

            composable("estadisticas") {
                StatsScreen(userId = userId)
            }

            composable(HomeDestination.Configuracion.route) {
                ConfigurationScreen(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }
        }
    }
}
