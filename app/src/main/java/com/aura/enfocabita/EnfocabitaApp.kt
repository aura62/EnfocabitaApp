package com.aura.enfocabita

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.aura.enfocabita.di.databaseModule
import com.aura.enfocabita.di.repositoryModule
import com.aura.enfocabita.di.domainModule
import com.aura.enfocabita.di.viewModelModule
import com.aura.enfocabita.utils.DemoDataInitializer
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.aura.enfocabita.data.repository.PomodoroSesionRepository
import com.aura.enfocabita.data.repository.PomodoroHistorialRepository
import com.aura.enfocabita.di.preferencesModule

class EnfocabitaApp : Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@EnfocabitaApp)
            modules(
                listOf(
                    databaseModule,
                    repositoryModule,
                    domainModule,
                    viewModelModule,
                    preferencesModule
                )
            )
        }

        // Ejecutar demo después de iniciar Koin y el sistema
        android.os.Handler(mainLooper).post {
            DemoDataInitializer.cargarDesdeJson(
                context = this,
                usuarioRepo = org.koin.core.context.GlobalContext.get().get(),
                habitoRepo = org.koin.core.context.GlobalContext.get().get(),
                progresoRepo = org.koin.core.context.GlobalContext.get().get(),
                passwordHasher = org.koin.core.context.GlobalContext.get().get(),
                pomodoroSesionRepo = org.koin.core.context.GlobalContext.get().get(),
                pomodoroHistorialRepo = org.koin.core.context.GlobalContext.get().get()
            )
        }
    }
}