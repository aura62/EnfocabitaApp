package com.aura.enfocabita.domain.usecase

import android.database.sqlite.SQLiteException
import com.aura.enfocabita.data.local.database.entidades.UserType
import com.aura.enfocabita.data.local.database.entidades.Usuario
import com.aura.enfocabita.data.repository.UsuarioRepository
import com.aura.enfocabita.domain.error.DatabaseException
import com.aura.enfocabita.domain.error.UserAlreadyExistsException
import com.aura.enfocabita.domain.validation.AuthValidator
import com.aura.enfocabita.domain.validation.AuthValidatorImpl
import java.util.Date

class RegisterUser(
    private val repo: UsuarioRepository,
    private val validator: AuthValidatorImpl,
    private val nowProvider: () -> Date = { Date() },
    private val passwordHasher: (String) -> String
) {
    suspend operator fun invoke(
        nombre: String,
        correo: String,
        plainPassword: String
    ): Long {
        // 1) Validaciones usando AuthValidator
        val name = nombre.trim()
        validator.validateName(name)

        val email = correo.trim()
        validator.validateEmail(email)

        validator.validatePassword(plainPassword) // sin trim aquí

        // 2) Comprobar usuario existente
        repo.findByEmail(email)?.let {
            throw UserAlreadyExistsException(email)
        }

        // 3) Hash de la contraseña
        val hashed = passwordHasher(plainPassword)

        // 4) Construir entidad con fecha y tipo
        val newUser = Usuario(
            nombre             = name,
            correo             = email,
            encryptedPassword  = hashed,
            fechaRegistro      = nowProvider(),
            tipoUsuario        = UserType.STANDARD
        )

        // 5) Persistir
        return try {
            repo.createUser(newUser)
        } catch (e: SQLiteException) {
            throw DatabaseException("Error al crear el usuario en la base de datos.", e)
        }
    }
}