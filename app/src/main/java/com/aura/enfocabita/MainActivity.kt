package com.aura.enfocabita

import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.aura.enfocabita.presentation.home.HomeNavGraph
import com.aura.enfocabita.presentation.auth.AuthNavGraph
import com.aura.enfocabita.ui.theme.EnfocabitaTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * Actividad principal de la aplicación. Inicia Koin y define
 * el punto de entrada para la UI según el estado de autenticación del usuario.
 */


class MainActivity : ComponentActivity() {
    @ExperimentalMaterial3Api
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        // 2. Carga el tema y define la navegación inicial (Auth → Home)
        setContent {
            EnfocabitaTheme {
                val navController = rememberNavController()
                var userId by remember { mutableStateOf<Long?>(null) }

                if (userId == null) {
                    AuthNavGraph(
                        onNavigateToHome = { id ->
                            userId = id
                        }
                    )
                } else {
                    HomeNavGraph(
                        navController = navController,
                        userId = userId!!,
                        inicioViewModel = getViewModel()
                    )
                }
            }
        }

    }

    }
