package com.aura.enfocabita.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceManager(private val context: Context) {

    companion object {
        private val Context.dataStore by preferencesDataStore(name = "user_preferences")
        val MODO_OSCURO = booleanPreferencesKey("modo_oscuro")
    }

    val isDarkMode: Flow<Boolean> = context.dataStore.data.map {
        it[MODO_OSCURO] ?: false
    }
    val KEY_NOTIFICACIONES = booleanPreferencesKey("modo_notificaciones")
    val notificacionesActivas: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[KEY_NOTIFICACIONES] ?: true }


    suspend fun guardarModoOscuro(enabled: Boolean) {
        context.dataStore.edit { it[MODO_OSCURO] = enabled }
    }

    suspend fun setNotificacionesActivas(activo: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_NOTIFICACIONES] = activo
        }
    }
}