package com.aura.enfocabita.di

import com.aura.enfocabita.data.repository.UsuarioRepository
import com.aura.enfocabita.domain.usecase.LoginUser
import com.aura.enfocabita.domain.usecase.RegisterUser
import com.aura.enfocabita.domain.validation.AuthValidator
import com.aura.enfocabita.domain.validation.AuthValidatorImpl
import org.koin.dsl.module
import java.util.Date

val domainModule = module {

    // 1) Validador único (interfaz → implementación)
    single<AuthValidator> { AuthValidatorImpl() }

    // 2) Registro de usuario
    factory {
        RegisterUser(
            repo           = get<UsuarioRepository>(),
            validator      = get<AuthValidator>(),
            nowProvider    = { Date() },
            passwordHasher = { plain -> plain }      // stub por ahora
        )
    }

    // 3) Login de usuario
    factory {
        LoginUser(
            repo = get<UsuarioRepository>(),
            validator = get<AuthValidator>(),
            passwordHasher = { plain -> plain }      // mismo stub
        )
    }
}