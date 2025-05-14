package com.aura.enfocabita.di

import androidx.room.Room
import com.aura.enfocabita.data.local.database.EnfocabitaDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

    // Módulo para proveer la base de datos y los DAOs
    val databaseModule = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                EnfocabitaDatabase::class.java,
                "enfocabita.db"
            )
                // Manejo de migraciones destructivas para desarrollo
                .fallbackToDestructiveMigration()
                .build()
        }

        single { get<EnfocabitaDatabase>().usuarioDao() }
        single { get<EnfocabitaDatabase>().configuracionUsuarioDao() }
        single { get<EnfocabitaDatabase>().habitoDao() }
        single { get<EnfocabitaDatabase>().progresoHabitoDiarioDao() }
        single { get<EnfocabitaDatabase>().recordatorioHabitoDao() }
        single { get<EnfocabitaDatabase>().pomodoroSesionDao() }
        single { get<EnfocabitaDatabase>().pomodoroHistorialDao() }
        single { get<EnfocabitaDatabase>().recordatorioPomodoroDao() }
        single { get<EnfocabitaDatabase>().estadisticaHabitoDao() }
        single { get<EnfocabitaDatabase>().estadisticaPomodoroDao() }
        single { get<EnfocabitaDatabase>().estadisticaGlobalDao() }
    }