package com.aura.enfocabita.utils
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.aura.enfocabita.data.local.database.entidades.*
import com.aura.enfocabita.data.repository.*
import com.aura.enfocabita.domain.security.PasswordHasher
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

/**
 * Carga inicial de datos de demostración desde un archivo JSON ubicado en assets.
 * Este metodo es ideal para pruebas, demostraciones o presentaciones.
 */
object DemoDataInitializer {

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarDesdeJson(
        context: Context,
        usuarioRepo: UsuarioRepository,
        habitoRepo: HabitoRepository,
        progresoRepo: ProgresoHabitoDiarioRepository,
        passwordHasher: PasswordHasher,
        pomodoroSesionRepo: PomodoroSesionRepository,
        pomodoroHistorialRepo: PomodoroHistorialRepository
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (usuarioRepo.getAllUsers().isNotEmpty()) return@launch

                // Leer JSON desde assets de forma segura
                val json = runCatching {
                    context.assets.open("demo_data.json")
                        .bufferedReader()
                        .use { it.readText() }
                }.getOrElse {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "No se encontró demo_data.json en assets/",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    return@launch
                }

                val demoData = Gson().fromJson(json, DemoData::class.java)

                // Crear usuario demo
                val userId = usuarioRepo.createUser(
                    Usuario(
                        nombre = demoData.usuario.nombre,
                        correo = demoData.usuario.correo,
                        encryptedPassword = passwordHasher.hash(demoData.usuario.password),
                        fechaRegistro = Date.from(
                            LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()
                        ),
                        tipoUsuario = UserType.STANDARD
                    )
                )

                // Crear hábitos
                val habitoIds = mutableListOf<Long>()
                for (hab in demoData.habitos) {
                    val habitoId = habitoRepo.createHabit(
                        Habito(
                            idUsuario = userId,
                            titulo = hab.titulo,
                            tipo = TypeHab.valueOf(hab.tipo.uppercase()),
                            frecuencia = hab.frecuencia,
                            periodo = PeriodUnit.valueOf(hab.periodo.uppercase()),
                            fechaRegistro = Date()
                        )
                    )
                    habitoIds.add(habitoId)
                }

                // Obtener fecha actual a las 00:00 (inicio del día)
                val startOfToday = Date.from(
                    LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()
                )

                // Insertar progresos referenciando los índices
                demoData.progresos.forEach { prog ->
                    val habitoId = habitoIds.getOrNull(prog.habitoIndex)
                    if (habitoId != null) {
                        progresoRepo.saveProgress(
                            ProgresoHabitoDiario(
                                idHab = habitoId,
                                fechaRegistro = startOfToday, // ✅ ya sin hora
                                completado = prog.completado
                            )
                        )
                    }
                }

                demoData.pomodoros.forEach { pomodoroDemo ->
                    val pomodoroId = pomodoroSesionRepo.saveSession(
                        PomodoroSesion(
                            idUsuario = userId,
                            tituloTarea = pomodoroDemo.titulo,
                            duracion_ms = pomodoroDemo.duracion,
                            dcorto_ms = pomodoroDemo.dcorto,
                            dLargo_ms = pomodoroDemo.dlargo,
                            numSesiones = pomodoroDemo.sesiones,
                            fechaCreacion = Date()
                        )
                    )

                    // Historial
                    pomodoroDemo.historial.forEach { registro ->
                        val fechaHora = "${registro.fecha}T${registro.hora}"
                        val instant = java.time.LocalDateTime.parse(fechaHora)
                            .atZone(java.time.ZoneId.systemDefault())
                            .toInstant()

                        pomodoroHistorialRepo.saveAndFetchAll(
                            PomodoroHistorial(
                                idPomodoro = pomodoroId,
                                fecha = Date.from(instant),
                                horaInicio = Date.from(instant)
                            )
                        )
                    }
                }


                // Mostrar confirmación visual
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "✅ Datos de demostración cargados correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "❌ Error cargando datos de demo",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}

