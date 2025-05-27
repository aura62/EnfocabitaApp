package com.aura.enfocabita.presentation.inicio

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.*

/**
 * Pantalla de Inicio que muestra un resumen del día del usuario.
 *
 * Incluye información sobre hábitos completados, tiempo en sesiones Pomodoro
 * y la última fecha de actividad registrada.
 *
 * @param userId ID del usuario autenticado.
 * @param viewModel ViewModel que gestiona los datos y el estado de la pantalla.
 * @param modifier Modifier opcional para personalizar el layout.
 */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InicioScreen(
    userId: Long,
    viewModel: InicioViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // Estado reactivo expuesto por el ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val fraseDelDia by viewModel.fraseDelDia.collectAsState()


    // Contenido principal
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        uiState.nombreUsuario?.let { GreetingSection(nombre = it) }

        Spacer(modifier = Modifier.height(20.dp))

        if (uiState.isLoading) {
            // Indicador de carga mientras se obtienen los datos
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Resumen del día", style = MaterialTheme.typography.titleMedium)

                    Text("✅ Hábitos completados: ${uiState.habitosCompletados} de ${uiState.habitosTotales}")
                    Text("⏱️ Minutos Pomodoro: ${uiState.minutosPomodoro} min")

                    Text(
                        text = "📅 Última actividad: ${
                            uiState.ultimaActividad?.let {
                                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
                            } ?: "Sin datos"
                        }"
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            StreakCard(racha = uiState.rachaActual)

            Spacer(modifier = Modifier.height(12.dp))

            fraseDelDia?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Text(
                        text = "\"$it\"",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { navController.navigate("habitos/lista") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Ver hábitos")
                }

                Button(
                    onClick = { navController.navigate("pomodoro/lista") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Pomodoro")
                }
            }
        }
    }