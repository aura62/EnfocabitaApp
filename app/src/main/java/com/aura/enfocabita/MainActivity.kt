package com.aura.enfocabita


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aura.enfocabita.UI.navigation.HomeNavGraph
import com.aura.enfocabita.di.databaseModule
import com.aura.enfocabita.di.domainModule
import com.aura.enfocabita.di.repositoryModule
import com.aura.enfocabita.di.viewModelModule
import com.aura.enfocabita.presentation.auth.AuthNavGraph
import com.aura.enfocabita.ui.theme.EnfocabitaTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1) Inicializar Koin
        startKoin {
            androidContext(this@MainActivity)
            modules(
                databaseModule,
                repositoryModule,
                domainModule,
                viewModelModule
            )
        }

        // 2) Arrancar la UI
        setContent {
            EnfocabitaTheme {
                AuthNavGraph(
                    onNavigateToHome = {
                        // Aquí, al dispararse, navegamos directamente al Home
                        HomeNavGraph()
                    }
                )
            }
        }
    }
}
