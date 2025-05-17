package com.aura.enfocabita

import android.app.Application
import com.aura.enfocabita.di.databaseModule
import com.aura.enfocabita.di.repositoryModule
import com.aura.enfocabita.di.domainModule
import com.aura.enfocabita.di.viewModelModule
import com.aura.enfocabita.utils.DemoDataInitializer
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EnfocabitaApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@EnfocabitaApp)
            modules(
                listOf(
                    databaseModule,
                    repositoryModule,
                    domainModule,
                    viewModelModule
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
                passwordHasher = org.koin.core.context.GlobalContext.get().get()
            )
        }
    }
}