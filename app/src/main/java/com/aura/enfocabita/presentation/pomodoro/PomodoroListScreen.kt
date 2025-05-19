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
import com.aura.enfocabita.data.local.database.entidades.PomodoroSesion
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.getValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PomodoroListScreen(
    userId: Long,
    viewModel: PomodoroViewModel = koinViewModel(),
    onAgregarClick: () -> Unit,
    onEditar: (Long) -> Unit
) {
    val sesiones by viewModel.sesiones.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.observarSesiones(userId)
        viewModel.mensajeError.collect { snackbarHostState.showSnackbar(it) }
        viewModel.mensajeError.collect { snackbarHostState.showSnackbar(it) }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Pomodoros") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregarClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar sesión")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                PomodoroList(sesiones = sesiones, onEditar = onEditar)
            }
        }
    }
}

@Composable
fun PomodoroList(
    sesiones: List<PomodoroSesion>,
    onEditar: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(sesiones) { sesion ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onEditar(sesion.idPomodoro) }
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(text = sesion.tituloTarea, style = MaterialTheme.typography.titleMedium)
                    Text("Trabajo: ${sesion.duracion_ms / 60000} min | Sesiones: ${sesion.numSesiones}")
                }
            }
        }
    }
}