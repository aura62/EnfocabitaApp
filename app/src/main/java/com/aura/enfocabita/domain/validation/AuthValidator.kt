package com.aura.enfocabita.domain.validation

import com.aura.enfocabita.domain.error.InvalidEmailException
import com.aura.enfocabita.domain.error.InvalidNameException
import com.aura.enfocabita.domain.error.WeakPasswordException

interface AuthValidator {
    @Throws(InvalidNameException::class)
    fun validateName(name: String)

    @Throws(InvalidEmailException::class)
    fun validateEmail(email: String)

    @Throws(WeakPasswordException::class)
    fun validatePassword(password: String)

    fun validName(nombre: String): Boolean
}