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
import org.koin.compose.viewmodel.koinViewModel


/**
 * Define la navegación de autenticación: login, registro y home.
 */

@Composable
fun AuthNavGraph(
    startDestination: String = "login",
    onNavigateToHome: @Composable () -> Unit   // <-- ahora es un Composable lambda
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(
                viewModel = koinViewModel(),
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToHome = onNavigateToHome
            )
        }
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
 * Pantalla de login usando AuthViewModel.
 */
@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onNavigateToHome: @Composable (() -> Unit),
    onNavigateToRegister: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = viewModel.events) {
        viewModel.events.collect { event ->
            if (event is AuthEvent.NavigateToHome) onNavigateToHome()
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
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
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