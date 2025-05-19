package com.aura.enfocabita.presentation.pomodoro

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aura.enfocabita.data.local.database.entidades.PomodoroSesion
import kotlinx.coroutines.launch
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormPomodoroScreen(
    userId: Long,
    sesionExistente: PomodoroSesion? = null,
    onGuardar: (PomodoroSesion) -> Unit,
    onCancelar: () -> Unit,
    onEliminar: (() -> Unit)? = null
) {
    var titulo by remember { mutableStateOf(sesionExistente?.tituloTarea ?: "") }
    var trabajoMin by remember { mutableStateOf((sesionExistente?.duracion_ms ?: 1500000L) / 60000L) }
    var descansoCortoMin by remember { mutableStateOf((sesionExistente?.dcorto_ms ?: 300000L) / 60000L) }
    var descansoLargoMin by remember { mutableStateOf((sesionExistente?.dLargo_ms ?: 900000L) / 60000L) }
    var sesiones by remember { mutableStateOf(sesionExistente?.numSesiones ?: 4) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(if (sesionExistente != null) "Editar Pomodoro" else "Nuevo Pomodoro")
            })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Nombre de la tarea") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = trabajoMin.toString(),
                    onValueChange = { trabajoMin = it.toLongOrNull() ?: 25 },
                    label = { Text("Trabajo (min)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = descansoCortoMin.toString(),
                    onValueChange = { descansoCortoMin = it.toLongOrNull() ?: 5 },
                    label = { Text("Desc. Corto (min)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = descansoLargoMin.toString(),
                    onValueChange = { descansoLargoMin = it.toLongOrNull() ?: 15 },
                    label = { Text("Desc. Largo (min)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = sesiones.toString(),
                onValueChange = { sesiones = it.toIntOrNull() ?: 4 },
                label = { Text("Número de sesiones") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    if (titulo.isBlank()) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("El nombre no puede estar vacío")
                        }
                        return@Button
                    }

                    val nueva = sesionExistente?.copy(
                        tituloTarea = titulo,
                        duracion_ms = trabajoMin * 60000,
                        dcorto_ms = descansoCortoMin * 60000,
                        dLargo_ms = descansoLargoMin * 60000,
                        numSesiones = sesiones
                    ) ?: PomodoroSesion(
                        idUsuario = userId,
                        tituloTarea = titulo,
                        duracion_ms = trabajoMin * 60000,
                        dcorto_ms = descansoCortoMin * 60000,
                        dLargo_ms = descansoLargoMin * 60000,
                        numSesiones = sesiones
                    )
                    onGuardar(nueva)
                }) {
                    Text(if (sesionExistente != null) "Actualizar" else "Guardar")
                }

                OutlinedButton(onClick = onCancelar) {
                    Text("Cancelar")
                }
            }

            if (sesionExistente != null && onEliminar != null) {
                Button(
                    onClick = { mostrarConfirmacion = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Eliminar sesión")
                }
            }
        }
    }

    if (mostrarConfirmacion) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacion = false },
            title = { Text("¿Eliminar sesión?") },
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