package com.aura.enfocabita.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel


/**
 * Gráfico de navegación para el flujo de autenticación.
 *
 * Contiene las rutas hacia las pantallas de login y registro.
 * Permite transicionar a la sección principal (Inicio) una vez
 * el usuario se ha autenticado exitosamente.
 *
 * @param startDestination Ruta inicial del grafo (por defecto: "login").
 * @param onNavigateToHome Callback que se ejecuta tras login/registro exitoso,
 *                         recibiendo el ID del usuario autenticado.
 */

@Composable
fun AuthNavGraph(
    startDestination: String = "login",
    onNavigateToHome: (Long) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Pantalla de login
        composable("login") {
            LoginScreen(
                viewModel = koinViewModel(),
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToHome = onNavigateToHome
            )
        }

        // Pantalla de registro
        composable("register") {
            RegisterScreen(
                viewModel = koinViewModel(),
                onNavigateToLogin = { navController.popBackStack() },
                onNavigateToHome = onNavigateToHome
            )
        }
    }
}

/**
 * Pantalla de inicio de sesión para usuarios existentes.
 *
 * Permite ingresar correo y contraseña, y muestra el estado actual de la operación.
 * Navega a la pantalla principal (Inicio) si el login es exitoso,
 * o muestra mensajes de error si ocurre algún problema.
 *
 * @param viewModel ViewModel que maneja el estado y lógica del login.
 * @param onNavigateToHome Callback que se dispara con el ID del usuario tras login exitoso.
 * @param onNavigateToRegister Callback para navegar a la pantalla de registro.
 */
@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onNavigateToHome: (Long) -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    // Observa eventos de navegación únicos desde el ViewModel
    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            if (event is AuthEvent.NavigateToHome) {
                onNavigateToHome(event.userId)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Bienvenido", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        when (uiState) {
            AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Error -> Text(
                text = (uiState as AuthState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
            AuthState.InvalidCredentials -> Text(
                text = "Credenciales no válidas",
                color = MaterialTheme.colorScheme.error
            )
            else -> {}
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { viewModel.login(email, password) },
            enabled = uiState != AuthState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }

        TextButton(
            onClick = onNavigateToRegister,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}