package com.aura.enfocabita.presentation.auth

import com.aura.enfocabita.data.local.database.entidades.Usuario

sealed class AuthState {
    object Idle                : AuthState()
    object Loading             : AuthState()
    data class Registered(val userId: Long) : AuthState()
    data class LoggedIn(val user: Usuario)  : AuthState()
    object InvalidCredentials  : AuthState()
    data class Error(val message: String)  : AuthState()
}
