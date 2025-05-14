package com.aura.enfocabita.presentation.auth

sealed class AuthEvent {
    object NavigateToHome : AuthEvent()
}
