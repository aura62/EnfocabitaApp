package com.aura.enfocabita.presentation.pomodoro

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PomodoroScreen(
    userId: Long,
    navController: NavController,
    viewModel: PomodoroViewModel = koinViewModel(),
    onAgregarSesion: () -> Unit,
    onEditarSesion: (Long) -> Unit
) {
    val sesiones by viewModel.sesiones.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.observarSesiones(userId)

        viewModel.mensajeError.collect {
            snackbarHostState.showSnackbar(it)
        }

        viewModel.error.collect {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Mis Sesiones Pomodoro") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregarSesion) {
                Icon(Icons.Default.Add, contentDescription = "Nueva sesión")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            if (sesiones.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay sesiones registradas.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(sesiones) { sesion ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable { onEditarSesion(sesion.idPomodoro) }
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text("Tarea: ${sesion.tituloTarea}", style = MaterialTheme.typography.titleMedium)
                                Text("Sesiones: ${sesion.numSesiones}")
                                Text("Duración: ${sesion.duracion_ms / 60000} min")
                            }
                        }
                    }
                }
            }
        }
    }
}