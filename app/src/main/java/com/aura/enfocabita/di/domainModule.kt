package com.aura.enfocabita.di

import com.aura.enfocabita.domain.security.PasswordHasher
import com.aura.enfocabita.domain.security.Pbkdf2PasswordHasher
import com.aura.enfocabita.domain.validation.AuthValidator
import com.aura.enfocabita.domain.validation.AuthValidatorImpl
import com.aura.enfocabita.domain.usecase.LoginUser
import com.aura.enfocabita.domain.usecase.RegisterUser
import com.aura.enfocabita.domain.usecase.habit.*
import com.aura.enfocabita.domain.usecase.inicio.*
import com.aura.enfocabita.domain.usecase.pomodoro.*
import com.aura.enfocabita.domain.usecase.pomodoro.GetPomodoroSessionByIdUseCase
import org.koin.dsl.module
import java.util.Date

val domainModule = module {
    // 1) El validador de nombre, email y contraseña
    single<AuthValidator> { AuthValidatorImpl() }

    // 2) El servicio de hashing de contraseñas
    single<PasswordHasher> { Pbkdf2PasswordHasher() }

    // 3) Caso de uso de registro
    factory {
        RegisterUser(
            repo           = get(),                  // UsuarioRepository
            validator      = get(),                  // AuthValidator
            nowProvider    = { Date() },             // fecha actual
            passwordHasher = get<PasswordHasher>()::hash
        )
    }

    // 4) Caso de uso de login
    factory {
        LoginUser(
            repo           = get(),                  // UsuarioRepository
            validator      = get(),                  // AuthValidator
            passwordHasher = get<PasswordHasher>()    // si LoginUser recibe un PasswordHasher
        )
    }

    // 📆 Inicio
    factory { GetTodayHabitProgressUseCase(get()) }
    factory { GetTodayPomodoroTimeUseCase(get()) }
    factory { GetLastActivityDateUseCase(get()) }

    // 📋 Hábitos
    factory { CreateHabitUseCase(get()) }
    factory { UpdateHabitUseCase(get()) }
    factory { DeleteHabitUseCase(get()) }
    factory { GetHabitsByUserUseCase(get()) }
    factory { GetHabitByIdUseCase(get()) }
    factory { ToggleHabitCompletionUseCase(get()) }


    // ⏱ Pomodoro
    factory { CreatePomodoroSessionUseCase(get()) }
    factory { GetPomodoroSessionsByUserUseCase(get()) }
    factory { UpdatePomodoroSessionUseCase(get()) }
    factory { DeletePomodoroSessionUseCase(get()) }
    factory { GetPomodoroSessionByIdUseCase(get()) }

    //configuracion

}