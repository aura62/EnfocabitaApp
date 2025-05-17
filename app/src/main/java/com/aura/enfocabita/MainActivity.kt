package com.aura.enfocabita

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.aura.enfocabita.presentation.home.HomeNavGraph
import com.aura.enfocabita.di.databaseModule
import com.aura.enfocabita.di.domainModule
import com.aura.enfocabita.di.repositoryModule
import com.aura.enfocabita.di.viewModelModule
import com.aura.enfocabita.presentation.auth.AuthNavGraph
import com.aura.enfocabita.ui.theme.EnfocabitaTheme
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.context.startKoin

/**
 * Actividad principal de la aplicación. Inicia Koin y define
 * el punto de entrada para la UI según el estado de autenticación del usuario.
 */

class MainActivity : ComponentActivity() {
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
