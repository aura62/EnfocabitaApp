package com.aura.enfocabita.presentation.configuration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
//import org.koin.androidx.compose.koinViewModel
import org.koin.compose.viewmodel.koinViewModel

@ExperimentalMaterial3Api
@Composable
fun ConfigurationScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Configuración") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ConfigItem(icon = Icons.Default.Lock, label = "Privacidad") { /* TODO */ }
            ConfigItem(icon = Icons.Default.Star, label = "Calificar aplicación") { /* TODO */ }
            ConfigItem(icon = Icons.Default.Description, label = "Licencia") { /* TODO */ }

            ModoOscuroToggle()

            ConfigItem(icon = Icons.Default.Email, label = "Contacto") { /* TODO */ }
            ConfigItem(icon = Icons.Default.Share, label = "Compartir aplicación") { /* TODO */ }
            ConfigItem(icon = Icons.Default.Feedback, label = "Retroalimentación") { /* TODO */ }
            ConfigItem(icon = Icons.Default.Help, label = "Ayuda") { /* TODO */ }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            ConfigItem(
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                label = "Cerrar sesión",
                onClick = {
                    // TODO: Lógica para cerrar sesión
                }
            )

        }
    }
}

@Composable
fun ConfigItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = label)
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun ModoOscuroToggle() {
    var isDarkMode by remember { mutableStateOf(false) } // Reemplaza con persistencia real si deseas

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.DarkMode, contentDescription = "Modo oscuro")
        Spacer(modifier = Modifier.width(16.dp))
        Text("Modo oscuro", modifier = Modifier.weight(1f))
        Switch(checked = isDarkMode, onCheckedChange = { isDarkMode = it })
    }
}
//fun ConfigurationScreen(
//    viewModel: ConfiguracionViewModel = koinViewModel(),
//    onNavegarA: (ConfiguracionDestino) -> Unit
//) {
//    val modoOscuro by viewModel.modoOscuro.collectAsState()
//
//    Scaffold(
//        topBar = { TopAppBar(title = { Text("Configuración") }) }
//    ) { padding ->
//        Column(
//            modifier = Modifier
//                .padding(padding)
//                .padding(16.dp)
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            ConfigItem(icon = Icons.Default.Lock, texto = "Privacidad") {
//                onNavegarA(ConfiguracionDestino.Privacidad)
//            }
//            ConfigItem(icon = Icons.Default.Star, texto = "Calificar Aplicación") {
//                viewModel.calificarApp()
//            }
//            ConfigItem(icon = Icons.Default.Description, texto = "Licencia") {
//                onNavegarA(ConfiguracionDestino.Licencia)
//            }
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(Icons.Default.DarkMode, contentDescription = null)
//                    Spacer(Modifier.width(8.dp))
//                    Text("Modo oscuro")
//                }
//                Switch(checked = modoOscuro, onCheckedChange = { viewModel.toggleModoOscuro() })
//            }
//            ConfigItem(icon = Icons.Default.Email, texto = "Contacto") {
//                viewModel.enviarCorreo()
//            }
//            ConfigItem(icon = Icons.Default.Share, texto = "Compartir aplicación") {
//                viewModel.compartirApp()
//            }
//            ConfigItem(icon = Icons.Default.Feedback, texto = "Retroalimentación") {
//                viewModel.enviarRetro()
//            }
//            ConfigItem(icon = Icons.Default.Help, texto = "Ayuda") {
//                onNavegarA(ConfiguracionDestino.Ayuda)
//            }
//        }
//    }
//}
//
//@Composable
//fun ConfigItem(icon: ImageVector, texto: String, onClick: () -> Unit) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { onClick() }
//            .padding(vertical = 8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(icon, contentDescription = null)
//        Spacer(Modifier.width(12.dp))
//        Text(texto, style = MaterialTheme.typography.bodyLarge)
//    }
//}
//
//sealed class ConfiguracionDestino {
//    object Privacidad : ConfiguracionDestino()
//    object Licencia : ConfiguracionDestino()
//    object Ayuda : ConfiguracionDestino()
//}