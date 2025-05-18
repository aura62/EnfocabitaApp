package com.aura.enfocabita.presentation.habit

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
import com.aura.enfocabita.data.local.database.entidades.Habito
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitScreen(
    userId: Long,
    navController: NavController,
    viewModel: HabitViewModel = koinViewModel(),
    onAgregarClick: () -> Unit,
    onEditar: (Long) -> Unit
) {
    val habitos by viewModel.habitos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.observarHabitos(userId)
        viewModel.mensaje.collect {
            snackbarHostState.showSnackbar(it)
        }
        viewModel.mensajeError.collect {
            snackbarHostState.showSnackbar(it)
        }

    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mis Hábitos") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregarClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar hábito")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                HabitList(
                    habitos = habitos,
                    onEditar = onEditar
                )
            }
        }
    }
}

@Composable
fun HabitList(
    habitos: List<Habito>,
    onEditar: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(habitos) { habito ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onEditar(habito.idHabito) }
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(habito.titulo, style = MaterialTheme.typography.titleLarge)
                    Text("Frecuencia: ${habito.frecuencia} - ${habito.periodo}")
                    Text("Tipo: ${habito.tipo}")
                }
            }
        }
    }
}