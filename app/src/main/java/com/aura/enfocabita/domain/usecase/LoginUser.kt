package com.aura.enfocabita.domain.usecase

import com.aura.enfocabita.data.local.database.entidades.Usuario
import com.aura.enfocabita.data.repository.UsuarioRepository
import com.aura.enfocabita.domain.security.PasswordHasher
import com.aura.enfocabita.domain.validation.AuthValidator

class LoginUser(
    private val repo: UsuarioRepository,
    private val validator: AuthValidator,
    private val passwordHasher: PasswordHasher
) {
    suspend operator fun invoke(email: String, plainPassword: String): Usuario? {
        validator.validateEmail(email)
        validator.validatePassword(plainPassword)
        val user = repo.findByEmail(email) ?: return null
        return if (passwordHasher.verify(plainPassword, user.encryptedPassword))
            user
        else
            null
    }
}