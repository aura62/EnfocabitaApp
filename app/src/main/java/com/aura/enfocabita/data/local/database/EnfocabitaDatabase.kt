package com.aura.enfocabita.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
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

@Database(entities = [Usuario::class, Habito::class, PomodoroSesion::class, ConfiguracionUsuario::class, EstadisticaGlobal::class,
    EstadisticaHabito::class, EstadisticaPomodoro::class, RecordatorioHabito::class, RecordatorioPomodoro::class, ProgresoHabitoDiario::class,
    PomodoroHistorial::class], version = 1)
abstract class EnfocabitaDatabase : RoomDatabase() {

    abstract fun getUsuarioDao(): UsuarioDao
    abstract fun getHabitoDao(): HabitoDao
    abstract fun getPomodoroSesionDao(): PomodoroSesionDao
    abstract fun getConfiguracionUsuarioDao(): ConfUsuarioDao
    abstract fun getEstadisticaGlobalDao(): EstadisticaGlobalDao
    abstract fun getEstadisticaHabitoDao(): EstadisticaHabitoDao
    abstract fun getEstadisticaPomodoroDao(): EstadisticaPomodoroDao
    abstract fun getRecordatorioHabitoDao(): RecordatorioHabitoDao
    abstract fun getRecordatorioPomodoroDao(): RecordatorioPomodoroDao
    abstract fun getProgresoHabitoDiarioDao(): ProgresoHabitoDiarioDao
    abstract fun getPomodoroHistorialDao(): PomodoroHistorialDao




}