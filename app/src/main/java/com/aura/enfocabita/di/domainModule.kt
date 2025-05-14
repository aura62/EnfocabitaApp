package com.aura.enfocabita.di

import com.aura.enfocabita.data.repository.UsuarioRepository
import com.aura.enfocabita.domain.usecase.LoginUser
import com.aura.enfocabita.domain.usecase.RegisterUser
import com.aura.enfocabita.domain.validation.AuthValidator
import com.aura.enfocabita.domain.validation.AuthValidatorImpl
import org.koin.dsl.module
import java.util.Date



val domainModule = module {

    // 1) Validador único
    single<AuthValidator> { AuthValidatorImpl() }

    // 2) Registro de usuario (nuevo Use Case)
    factory {
        RegisterUser(
            repo           = get<UsuarioRepository>(),
            validator      = get<AuthValidatorImpl>(),
            nowProvider    = { Date() },
            passwordHasher = { plain -> plain }      // stub por ahora
        )
    }

    // 3) LoginUser (añádelo cuando lo implementes)
     factory {
       LoginUser(
         repo           = get<UsuarioRepository>(),
         validator      = get<AuthValidatorImpl>(),
         passwordHasher = { plain -> plain }
       )
     }
}