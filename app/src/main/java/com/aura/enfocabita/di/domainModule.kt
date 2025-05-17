package com.aura.enfocabita.di

import com.aura.enfocabita.domain.security.PasswordHasher
import com.aura.enfocabita.domain.security.Pbkdf2PasswordHasher
import com.aura.enfocabita.domain.validation.AuthValidator
import com.aura.enfocabita.domain.validation.AuthValidatorImpl
import com.aura.enfocabita.domain.usecase.LoginUser
import com.aura.enfocabita.domain.usecase.RegisterUser
import com.aura.enfocabita.domain.usecase.inicio.GetLastActivityDateUseCase
import com.aura.enfocabita.domain.usecase.inicio.GetTodayHabitProgressUseCase
import com.aura.enfocabita.domain.usecase.inicio.GetTodayPomodoroTimeUseCase
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

    factory { GetTodayHabitProgressUseCase(get()) }
    factory { GetTodayPomodoroTimeUseCase(get()) }
    factory { GetLastActivityDateUseCase(get()) }
}