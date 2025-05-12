package com.aura.enfocabita.data.local.database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aura.enfocabita.data.local.database.entidades.ConfiguracionUsuario
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfUsuarioDao {

        @Query("SELECT * FROM configuracion_usuario")
        suspend fun getAllConfigs(): List<ConfiguracionUsuario>

        @Query("SELECT * FROM configuracion_usuario WHERE idUsuario = :id LIMIT 1")
        suspend fun getConfigForUser(id: Long): ConfiguracionUsuario?

        @Query("SELECT * FROM configuracion_usuario WHERE idUsuario = :id LIMIT 1")
        fun observeConfigForUser(id: Long): Flow<ConfiguracionUsuario?>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertConfig(confUsuario: ConfiguracionUsuario): Long

        @Update
        suspend fun updateConfig(confUsuario: ConfiguracionUsuario): Int

        @Delete
        suspend fun deleteConfig(confUsuario: ConfiguracionUsuario): Int


}