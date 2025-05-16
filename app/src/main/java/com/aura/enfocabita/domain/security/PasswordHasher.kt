package com.aura.enfocabita.domain.security

interface PasswordHasher {
    /** Devuelve un string que incluye salt + hash (p. ej. “iterations:salt:hash”). */
    fun hash(password: String): String

    /** Comprueba si la contraseña en claro coincide con el hash almacenado. */
    fun verify(password: String, stored: String): Boolean
}
