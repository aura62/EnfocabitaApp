package com.aura.enfocabita.di

import com.aura.enfocabita.data.local.preferences.DarkThemePreferences
import com.aura.enfocabita.data.local.preferences.PreferenceManager
import com.aura.enfocabita.presentation.configuration.ConfigurationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.aura.enfocabita.di.preferencesModule

val preferencesModule = module {
    single { PreferenceManager(get()) } // get() = contexto
}