@file:OptIn(ExperimentalMaterial3Api::class)

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

private const val MIN_TRABAJO = 5
private const val MAX_TRABAJO = 90
private const val MIN_DESCANSO_CORTO = 1
private const val MAX_DESCANSO_CORTO = 15
private const val MIN_DESCANSO_LARGO = 5
private const val MAX_DESCANSO_LARGO = 30
private const val MIN_SESIONES = 1
private const val MAX_SESIONES = 8

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
                    onValueChange = {
                        trabajoMin = it.toLongOrNull() ?: trabajoMin
                    },
                    label = { Text("Trabajo (min)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    isError = trabajoMin !in MIN_TRABAJO..MAX_TRABAJO
                )
                OutlinedTextField(
                    value = descansoCortoMin.toString(),
                    onValueChange = {
                        descansoCortoMin = it.toLongOrNull() ?: descansoCortoMin
                    },
                    label = { Text("Desc. Corto (min)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    isError = descansoCortoMin !in MIN_DESCANSO_CORTO..MAX_DESCANSO_CORTO
                )
                OutlinedTextField(
                    value = descansoLargoMin.toString(),
                    onValueChange = {
                        descansoLargoMin = it.toLongOrNull() ?: descansoLargoMin
                    },
                    label = { Text("Desc. Largo (min)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    isError = descansoLargoMin !in MIN_DESCANSO_LARGO..MAX_DESCANSO_LARGO
                )
            }

            OutlinedTextField(
                value = sesiones.toString(),
                onValueChange = {
                    sesiones = it.toIntOrNull() ?: sesiones
                },
                label = { Text("Número de sesiones") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                isError = sesiones !in MIN_SESIONES..MAX_SESIONES
            )

            Button(
                onClick = {
                    when {
                        titulo.isBlank() -> {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("El nombre no puede estar vacío.")
                            }
                        }
                        trabajoMin !in MIN_TRABAJO..MAX_TRABAJO -> {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("El tiempo de Trabajo debe estar entre $MIN_TRABAJO y $MAX_TRABAJO minutos.")
                            }
                        }
                        descansoCortoMin !in MIN_DESCANSO_CORTO..MAX_DESCANSO_CORTO -> {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("El Descanso corto debe estar entre $MIN_DESCANSO_CORTO y $MAX_DESCANSO_CORTO minutos.")
                            }
                        }
                        descansoLargoMin !in MIN_DESCANSO_LARGO..MAX_DESCANSO_LARGO -> {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("El Descanso largo debe estar entre $MIN_DESCANSO_LARGO y $MAX_DESCANSO_LARGO minutos.")
                            }
                        }
                        sesiones !in MIN_SESIONES..MAX_SESIONES -> {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Las sesiones deben estar entre $MIN_SESIONES y $MAX_SESIONES.")
                            }
                        }
                        else -> {
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
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (sesionExistente != null) "Actualizar" else "Guardar")
            }

            OutlinedButton(onClick = onCancelar, modifier = Modifier.fillMaxWidth()) {
                Text("Cancelar")
            }

            if (sesionExistente != null && onEliminar != null) {
                Button(
                    onClick = { mostrarConfirmacion = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth()
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