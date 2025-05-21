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
import androidx.navigation.NavHostController
import com.aura.enfocabita.presentation.auth.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

@ExperimentalMaterial3Api
@Composable
fun ConfigurationScreen(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val configViewModel: ConfigurationViewModel = koinViewModel()
    val isDarkMode by configViewModel.isDarkMode.collectAsState(initial = false)
    val notificaciones by configViewModel.notificacionesActivas.collectAsState()

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

            ConfigItemSwitch(
                icon = Icons.Default.DarkMode,
                label = "Modo oscuro",
                checked = isDarkMode,
                onCheckedChange = { configViewModel.cambiarModoOscuro(it) }
            )

            ConfigItemSwitch(
                icon = Icons.Default.Notifications,
                label = "Notificaciones",
                checked = notificaciones,
                onCheckedChange = { configViewModel.cambiarNotificacionesActivas(it) }
            )

            ConfigItem(
                icon = Icons.Default.Lock,
                label = "Privacidad",
                onClick = { /* Lógica futura */ }
            )

            ConfigItem(
                icon = Icons.Default.Description,
                label = "Licencia",
                onClick = { /* Lógica futura */ }
            )

            ConfigItem(
                icon = Icons.Default.QuestionMark,
                label = "Ayuda",
                onClick = { /* Lógica futura */ }
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

@Composable
fun ConfigItemSwitch(
    icon: ImageVector,
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
