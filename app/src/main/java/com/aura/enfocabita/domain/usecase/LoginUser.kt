package com.aura.enfocabita.domain.usecase

import com.aura.enfocabita.data.local.database.entidades.Usuario
import com.aura.enfocabita.data.repository.UsuarioRepository
import com.aura.enfocabita.domain.validation.AuthValidatorImpl

class LoginUser (

    private val repo: UsuarioRepository,
    private val validator: AuthValidatorImpl,
    private val passwordHasher: (String) -> String
) {
    suspend operator fun invoke(
        correo: String,
        plainPassword: String
    ): Usuario? {
        // 1) Validamos formato
        val email = correo.trim()
        validator.validateEmail(email)
        validator.validatePassword(plainPassword)

        // 2) Intentamos recuperar el usuario
        val user = repo.findByEmail(email) ?: return null

        // 3) Comparamos hashes
        val hashedInput = passwordHasher(plainPassword)
        return if (hashedInput == user.encryptedPassword) user
        else null


    }
}