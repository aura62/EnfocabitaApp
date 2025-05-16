package com.aura.enfocabita.presentation.home

// AppNavigation.kt

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.getViewModel
import com.aura.enfocabita.presentation.auth.AuthViewModel
import com.aura.enfocabita.presentation.auth.LoginScreen
import com.aura.enfocabita.presentation.auth.RegisterScreen
import com.aura.enfocabita.UI.navigation.HomeNavGraph

@Composable
fun AppNavGraph(startDestination: String = "login") {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        // 1) Login
        composable("login") {
            val vm: AuthViewModel = getViewModel()
            LoginScreen(
                viewModel = vm,
                onNavigateToRegister = { navController.navigate("register") },
                onLoginSuccess      = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // 2) Register
        composable("register") {
            val vm: AuthViewModel = getViewModel()
            RegisterScreen(
                viewModel = vm,
                onNavigateToLogin   = { navController.popBackStack() },
                onRegisterSuccess   = {
                    navController.navigate("home") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        // 3) Home (tu bottom-nav o pantallas principales)
        composable("home") {
            HomeNavGraph()
        }
    }
}