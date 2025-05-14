package com.aura.enfocabita

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.aura.enfocabita.data.local.database.DAO.HabitoDao
import com.aura.enfocabita.data.local.database.EnfocabitaDatabase
import com.aura.enfocabita.data.local.database.entidades.Habito
import com.aura.enfocabita.data.local.database.entidades.PeriodUnit
import com.aura.enfocabita.data.local.database.entidades.TypeHab
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class HabitoDaoTest {
    private lateinit var db: EnfocabitaDatabase
    private lateinit var dao: HabitoDao

    @Before fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            EnfocabitaDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        dao = db.habitoDao()
    }

    @After fun tearDown() {
        db.close()
    }

    @Test fun insertAndQueryHabitos() = runBlocking {
        // 1) Inserto datos de prueba
        val hab1 = Habito(
            idUsuario    = 1L,
            titulo       = "Leer un libro",
            tipo         = TypeHab.MANTENER,
            frecuencia   = 1,
            periodo      = PeriodUnit.DIARIO,
            fechaRegistro= Date()
        )
        val hab2 = hab1.copy(titulo = "Hacer ejercicio")
        val id1 = dao.insertHabito(hab1)
        val id2 = dao.insertHabito(hab2)

        // 2) Compruebo que getAll devuelve exactamente esos dos hábitos
        val all = dao.getAllHabitos()
        val assertEquals = assertEquals(2, all.size)
        val assertTrue = assertTrue(all.any { it.idHabito == id1 && it.titulo == "Leer un libro" })
        assertTrue(all.any { it.idHabito == id2 && it.titulo == "Hacer ejercicio" })

        // 3) Pruebo búsquedas, filtros, etc.
        val mantenimientos = dao.buscarHabitosPorNombre("%libro%", 1L)
        assertEquals(1, mantenimientos.size)
        assertEquals("Leer un libro", mantenimientos[0].titulo)
    }
}