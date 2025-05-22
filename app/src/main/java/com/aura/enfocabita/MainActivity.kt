package com.aura.enfocabita

import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.aura.enfocabita.presentation.home.HomeNavGraph
import com.aura.enfocabita.presentation.auth.AuthNavGraph
import com.aura.enfocabita.presentation.auth.AuthViewModel
import com.aura.enfocabita.presentation.configuration.ConfigurationViewModel
import com.aura.enfocabita.presentation.inicio.InicioViewModel
import com.aura.enfocabita.ui.theme.EnfocabitaTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Actividad principal de la aplicación. Inicia Koin y define
 * el punto de entrada para la UI según el estado de autenticación del usuario.
 */


class MainActivity : ComponentActivity() {
    @ExperimentalMaterial3Api
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authViewModel: AuthViewModel by viewModel()

        setContent {
            val configViewModel: ConfigurationViewModel by viewModel()
            val isDarkMode by configViewModel.isDarkMode.collectAsState(initial = false)

            EnfocabitaTheme(darkTheme = isDarkMode){
                val navController = rememberNavController()
                val userId by authViewModel.sesionActiva.collectAsState()
                val currenUser = userId

                if (userId == null) {
                    AuthNavGraph(
                        onNavigateToHome = { id ->
                            authViewModel.iniciarSesion(id)
                        }
                    )
                } else {
                    currenUser?.toLong()?.let {
                        HomeNavGraph(
                            navController = navController,
                            userId = it,
                            inicioViewModel = getViewModel(),
                            authViewModel = authViewModel
                        )
                    }
                }
            }
        }
    }
}
