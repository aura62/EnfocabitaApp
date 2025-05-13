package com.aura.enfocabita.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aura.enfocabita.data.local.database.DAO.ConfUsuarioDao
import com.aura.enfocabita.data.local.database.DAO.EstadisticaGlobalDao
import com.aura.enfocabita.data.local.database.DAO.EstadisticaHabitoDao
import com.aura.enfocabita.data.local.database.DAO.EstadisticaPomodoroDao
import com.aura.enfocabita.data.local.database.DAO.HabitoDao
import com.aura.enfocabita.data.local.database.DAO.PomodoroHistorialDao
import com.aura.enfocabita.data.local.database.DAO.PomodoroSesionDao
import com.aura.enfocabita.data.local.database.DAO.ProgresoHabitoDiarioDao
import com.aura.enfocabita.data.local.database.DAO.RecordatorioHabitoDao
import com.aura.enfocabita.data.local.database.DAO.RecordatorioPomodoroDao
import com.aura.enfocabita.data.local.database.DAO.UsuarioDao
import com.aura.enfocabita.data.local.database.entidades.ConfiguracionUsuario
import com.aura.enfocabita.data.local.database.entidades.ConvertType
import com.aura.enfocabita.data.local.database.entidades.EstadisticaGlobal
import com.aura.enfocabita.data.local.database.entidades.EstadisticaHabito
import com.aura.enfocabita.data.local.database.entidades.EstadisticaPomodoro
import com.aura.enfocabita.data.local.database.entidades.Habito
import com.aura.enfocabita.data.local.database.entidades.PomodoroHistorial
import com.aura.enfocabita.data.local.database.entidades.PomodoroSesion
import com.aura.enfocabita.data.local.database.entidades.ProgresoHabitoDiario
import com.aura.enfocabita.data.local.database.entidades.RecordatorioHabito
import com.aura.enfocabita.data.local.database.entidades.RecordatorioPomodoro
import com.aura.enfocabita.data.local.database.entidades.Usuario

@Database(
    entities = [
        Usuario::class,
        ConfiguracionUsuario::class,
        Habito::class,
        ProgresoHabitoDiario::class,
        RecordatorioHabito::class,
        PomodoroSesion::class,
        PomodoroHistorial::class,
        RecordatorioPomodoro::class,
        EstadisticaHabito::class,
        EstadisticaPomodoro::class,
        EstadisticaGlobal::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(ConvertType::class)
abstract class EnfocabitaDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun configuracionUsuarioDao(): ConfUsuarioDao
    abstract fun habitoDao(): HabitoDao
    abstract fun progresoHabitoDiarioDao(): ProgresoHabitoDiarioDao
    abstract fun recordatorioHabitoDao(): RecordatorioHabitoDao
    abstract fun pomodoroSesionDao(): PomodoroSesionDao
    abstract fun pomodoroHistorialDao(): PomodoroHistorialDao
    abstract fun recordatorioPomodoroDao(): RecordatorioPomodoroDao
    abstract fun estadisticaHabitoDao(): EstadisticaHabitoDao
    abstract fun estadisticaPomodoroDao(): EstadisticaPomodoroDao
    abstract fun estadisticaGlobalDao(): EstadisticaGlobalDao

    // TODO: añadir .addMigrations(...) cuando incrementes la versión
}