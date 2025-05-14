package com.aura.enfocabita.di

import com.aura.enfocabita.data.repository.ConfUsuarioRepository
import com.aura.enfocabita.data.repository.ConfUsuarioRepositoryImpl
import com.aura.enfocabita.data.repository.EstadisticaGlobalRepository
import com.aura.enfocabita.data.repository.EstadisticaGlobalRepositoryImpl
import com.aura.enfocabita.data.repository.EstadisticaHabitoRepository
import com.aura.enfocabita.data.repository.EstadisticaHabitoRepositoryImpl
import com.aura.enfocabita.data.repository.EstadisticaPomodoroRepository
import com.aura.enfocabita.data.repository.EstadisticaPomodoroRepositoryImpl
import com.aura.enfocabita.data.repository.HabitoRepository
import com.aura.enfocabita.data.repository.HabitoRepositoryImpl
import com.aura.enfocabita.data.repository.PomodoroHistorialRepository
import com.aura.enfocabita.data.repository.PomodoroHistorialRepositoryImpl
import com.aura.enfocabita.data.repository.PomodoroSesionRepository
import com.aura.enfocabita.data.repository.PomodoroSesionRepositoryImpl
import com.aura.enfocabita.data.repository.ProgresoHabitoDiarioRepository
import com.aura.enfocabita.data.repository.ProgresoHabitoDiarioRepositoryImpl
import com.aura.enfocabita.data.repository.RecordatorioHabitoRepository
import com.aura.enfocabita.data.repository.RecordatorioHabitoRepositoryImpl
import com.aura.enfocabita.data.repository.RecordatorioPomodoroRepository
import com.aura.enfocabita.data.repository.RecordatorioPomodoroRepositoryImpl
import com.aura.enfocabita.data.repository.UsuarioRepository
import com.aura.enfocabita.data.repository.UsuarioRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<UsuarioRepository>                      { UsuarioRepositoryImpl(get()) }
    single<ConfUsuarioRepository>             { ConfUsuarioRepositoryImpl(get()) }
    single<HabitoRepository>                    { HabitoRepositoryImpl(get()) }
    single<ProgresoHabitoDiarioRepository>      { ProgresoHabitoDiarioRepositoryImpl(get()) }
    single<RecordatorioHabitoRepository>        { RecordatorioHabitoRepositoryImpl(get()) }
    single<PomodoroSesionRepository>            { PomodoroSesionRepositoryImpl(get()) }
    single<PomodoroHistorialRepository>         { PomodoroHistorialRepositoryImpl(get()) }
    single<RecordatorioPomodoroRepository>      { RecordatorioPomodoroRepositoryImpl(get()) }
    single<EstadisticaHabitoRepository>         { EstadisticaHabitoRepositoryImpl(get()) }
    single<EstadisticaPomodoroRepository>       { EstadisticaPomodoroRepositoryImpl(get()) }
    single<EstadisticaGlobalRepository>         { EstadisticaGlobalRepositoryImpl(get()) }
}