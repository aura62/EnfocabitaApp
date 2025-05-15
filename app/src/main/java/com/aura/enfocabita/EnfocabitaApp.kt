package com.aura.enfocabita

import android.app.Application
import com.aura.enfocabita.di.databaseModule
import com.aura.enfocabita.di.repositoryModule
import com.aura.enfocabita.di.domainModule
import com.aura.enfocabita.di.viewModelModule
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
    }
}