package com.aura.enfocabita.domain.error


/**
 * Base para todos los errores de autenticación.
 */
sealed class AuthException(message: String?) : Exception(message)

/** El nombre no puede quedar vacío. */
class InvalidNameException(message: String) : AuthException(message)

/** El correo no cumple el formato requerido. */
class InvalidEmailException(message: String) : AuthException(message)

/** La contraseña no cumple los requisitos mínimos. */
class WeakPasswordException(message: String) : AuthException(message)

/** Ya existe un usuario con el correo dado. */
class UserAlreadyExistsException(email: String) : AuthException("El usuario con correo '$email' ya existe.")

/** Error en la capa de persistencia de usuarios. */
class DatabaseException(message: String, cause: Throwable? = null) : AuthException(message) {
    init {
        if (cause != null) {
            initCause(cause)
        }
    }
}