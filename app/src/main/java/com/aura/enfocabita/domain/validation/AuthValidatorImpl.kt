package com.aura.enfocabita.domain.validation

import com.aura.enfocabita.domain.error.InvalidEmailException
import com.aura.enfocabita.domain.error.InvalidNameException
import com.aura.enfocabita.domain.error.WeakPasswordException

class AuthValidatorImpl : AuthValidator {

    private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex()

    override fun validateName(name: String) {
        if (name.trim().isEmpty()) {
            throw InvalidNameException("El nombre no puede estar vacío.")
        }
    }

    override fun validateEmail(email: String) {
        val trimmed = email.trim()
        if (!EMAIL_REGEX.matches(trimmed)) {
            throw InvalidEmailException("El correo '$trimmed' no tiene un formato válido.")
        }
    }

    override fun validatePassword(password: String) {
        // Aquí no trimamos porque los espacios pueden ser parte de la contraseña
        if (password.length < 6) {
            throw WeakPasswordException("La contraseña debe tener al menos 6 caracteres.")
        }
    }
}