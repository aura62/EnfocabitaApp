package com.aura.enfocabita.presentation.auth

/**
 * Representa eventos únicos que ocurren en el flujo de autenticación,
 * como navegación hacia la pantalla principal (Inicio).
 *
 * Estos eventos son emitidos desde el ViewModel y consumidos una sola vez
 * por las pantallas de login o registro para tomar acciones como navegar.
 */

sealed class AuthEvent {
    /**
     * Evento que indica que el usuario ha iniciado sesión o se ha registrado
     * exitosamente, y debe navegar hacia la pantalla principal.
     *
     * @param userId ID del usuario autenticado, necesario para cargar sus datos.
     */
    data class NavigateToHome(val userId: Long) : AuthEvent()
}
