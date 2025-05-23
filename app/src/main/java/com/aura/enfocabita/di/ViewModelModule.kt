package com.aura.enfocabita.di

import com.aura.enfocabita.presentation.auth.AuthViewModel
import com.aura.enfocabita.presentation.calendar.CalendarViewModel
import com.aura.enfocabita.presentation.configuration.ConfigurationViewModel
import com.aura.enfocabita.presentation.habit.HabitViewModel
import com.aura.enfocabita.presentation.inicio.InicioViewModel
import com.aura.enfocabita.presentation.pomodoro.PomodoroViewModel
import com.aura.enfocabita.presentation.stats.StatsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get(), get()) }
    viewModel { InicioViewModel(get(), get(), get()) }
    viewModel { HabitViewModel(get(), get(), get(), get(),get(), get()) }
    viewModel { PomodoroViewModel(get(), get(), get(), get(), get()) }
    viewModel { ConfigurationViewModel(get()) }
    viewModel { CalendarViewModel(get(), get()) }
    viewModel { StatsViewModel(get(), get()) }

}