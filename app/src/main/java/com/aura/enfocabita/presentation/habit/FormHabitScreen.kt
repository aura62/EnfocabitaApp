package com.aura.enfocabita.presentation.habit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aura.enfocabita.data.local.database.entidades.Habito
import com.aura.enfocabita.data.local.database.entidades.PeriodUnit
import com.aura.enfocabita.data.local.database.entidades.TypeHab
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormHabitScreen(
    userId: Long,
    habitoExistente: Habito? = null,
    onGuardar: (Habito) -> Unit,
    onCancelar: () -> Unit,
    onEliminar: (() -> Unit)? = null
) {
    val viewModel: HabitViewModel = koinViewModel()
    var titulo by remember { mutableStateOf(habitoExistente?.titulo ?: "") }
    var frecuencia by remember { mutableStateOf(habitoExistente?.frecuencia?.toString() ?: "1") }
    var periodo by remember { mutableStateOf(habitoExistente?.periodo ?: PeriodUnit.DIARIO) }
    var tipo by remember { mutableStateOf(habitoExistente?.tipo ?: TypeHab.ADQUIRIR) }
    var errorTitulo by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    var mostrarConfirmacion by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var completadoHoy by remember { mutableStateOf(false) }

    LaunchedEffect(habitoExistente?.idHabito) {
        if (habitoExistente != null) {
            completadoHoy = viewModel.estaCompletadoHoy(habitoExistente.idHabito)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(if (habitoExistente != null) "Editar Hábito" else "Nuevo Hábito")
            })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = {
                    titulo = it
                    errorTitulo = false
                },
                label = { Text("Título del hábito") },
                isError = errorTitulo,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = frecuencia,
                onValueChange = { frecuencia = it },
                label = { Text("Frecuencia") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Periodo")
            DropdownMenuSelector(
                current = periodo.name,
                options = PeriodUnit.entries.map { it.name },
                onSelected = { periodo = PeriodUnit.valueOf(it) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Tipo")
            DropdownMenuSelector(
                current = tipo.name,
                options = TypeHab.entries.map { it.name },
                onSelected = { tipo = TypeHab.valueOf(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (habitoExistente != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Checkbox(
                        checked = completadoHoy,
                        onCheckedChange = { isChecked ->
                            completadoHoy = isChecked
                            viewModel.actualizarEstadoHabito(habitoExistente.idHabito, isChecked)
                        }
                    )

                    Text("Marcar como completado hoy")
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    if (titulo.isBlank()) {
                        errorTitulo = true
                        return@Button
                    }

                    val freq = frecuencia.toIntOrNull()
                    if (freq == null || freq <= 0) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("La frecuencia debe ser un número válido mayor a 0.")
                        }
                        return@Button
                    }

                    val habit = habitoExistente?.copy(
                        titulo = titulo,
                        frecuencia = freq,
                        periodo = periodo,
                        tipo = tipo
                    ) ?: Habito(
                        idUsuario = userId,
                        titulo = titulo,
                        frecuencia = freq,
                        periodo = periodo,
                        tipo = tipo,
                        fechaRegistro = Date()
                    )

                    onGuardar(habit)
                }) {
                    Text(if (habitoExistente != null) "Actualizar" else "Guardar")
                }

                OutlinedButton(onClick = onCancelar) {
                    Text("Cancelar")
                }
            }

            if (habitoExistente != null && onEliminar != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { mostrarConfirmacion = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Eliminar hábito")
                }
            }
        }
    }

    if (mostrarConfirmacion) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacion = false },
            title = { Text("¿Eliminar hábito?") },
            text = { Text("Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = {
                    mostrarConfirmacion = false
                    onEliminar?.invoke()
                }) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarConfirmacion = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}