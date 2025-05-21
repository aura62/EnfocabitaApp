package com.aura.enfocabita.presentation.configuration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aura.enfocabita.presentation.auth.AuthEvent
import com.aura.enfocabita.presentation.auth.AuthViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@ExperimentalMaterial3Api
@Composable
fun ConfigurationScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val sesionActiva by authViewModel.sesionActiva.collectAsState()

    // Redirigir si no hay sesión activa
    LaunchedEffect(Unit) {
        authViewModel.events.collectLatest { event ->
            if (event is AuthEvent.NavigateToLogin) {
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Configuración") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Preferencias", style = MaterialTheme.typography.titleMedium)

            ConfigItem(
                icon = Icons.Default.DarkMode,
                label = "Modo oscuro",
                onClick = { /* Aquí puedes mostrar un switch o modal */ }
            )

            ConfigItem(
                icon = Icons.Default.Notifications,
                label = "Notificaciones",
                onClick = { /* Lógica futura */ }
            )

            ConfigItem(
                icon = Icons.Default.Alarm,
                label = "Duración predeterminada de Pomodoro",
                onClick = { /* Abrir selector de duración */ }
            )

            Divider()

            ConfigItem(
                icon = Icons.Default.ExitToApp,
                label = "Cerrar sesión",
                color = MaterialTheme.colorScheme.error,
                onClick = {
                    authViewModel.cerrarSesion()
                }
            )
        }
    }
}

@Composable
fun ConfigItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = color)
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, style = MaterialTheme.typography.bodyLarge)
    }
}
