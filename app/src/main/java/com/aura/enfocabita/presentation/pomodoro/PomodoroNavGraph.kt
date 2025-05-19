package com.aura.enfocabita.presentation.pomodoro

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.navigation.*
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.aura.enfocabita.data.local.database.entidades.PomodoroSesion
import com.aura.enfocabita.presentation.pomodoro.PomodoroViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.pomodoroNavGraph(
    userId: Long,
    navController: NavController
) {
    composable(PomodoroDestination.Lista.ruta) {
        PomodoroListScreen(
            userId = userId,
            onAgregarClick = { navController.navigate(PomodoroDestination.Form.ruta) },
            onEditar = { id -> navController.navigate(PomodoroDestination.Editar.conId(id)) }
        )
    }

    composable(PomodoroDestination.Form.ruta) {
        val viewModel: PomodoroViewModel = koinViewModel()
        FormPomodoroScreen(
            userId = userId,
            onGuardar = {
                viewModel.crearSesion(it)
                navController.popBackStack()
            },
            onCancelar = { navController.popBackStack() }
        )
    }

    composable(
        route = PomodoroDestination.Editar.ruta,
        arguments = listOf(navArgument("pomodoroId") { type = NavType.LongType })
    ) { backStackEntry ->
        val viewModel: PomodoroViewModel = koinViewModel()
        val pomodoroId = backStackEntry.arguments?.getLong("pomodoroId") ?: return@composable

        var sesionExistente by remember { mutableStateOf<PomodoroSesion?>(null) }
        var isLoading by remember { mutableStateOf(true) }

        LaunchedEffect(pomodoroId) {
            // Simulamos el comportamiento como si tuvieras un "getSesionPorId"
            sesionExistente = viewModel.cargarSesionPorId(pomodoroId)
            isLoading = false
        }

        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (sesionExistente != null) {
            FormPomodoroScreen(
                userId = userId,
                sesionExistente = sesionExistente!!,
                onGuardar = {
                    viewModel.actualizarSesion(it)
                    navController.popBackStack()
                },
                onCancelar = { navController.popBackStack() },
                onEliminar = {
                    viewModel.eliminarSesion(pomodoroId)
                    navController.popBackStack()
                }
            )
        } else {
            // Manejo de error si no se encuentra la sesión
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Sesión no encontrada")
            }
        }
    }
}