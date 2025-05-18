package com.aura.enfocabita.presentation.habit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.aura.enfocabita.data.local.database.entidades.Habito
import org.koin.compose.viewmodel.koinViewModel

sealed class HabitoDestinos(val ruta: String) {
    object Lista : HabitoDestinos("habitos/lista")
    object Formulario : HabitoDestinos("habitos/formulario")
    object FormularioEditar : HabitoDestinos("habitos/formulario/{habitoId}") {
        fun editar(id: Long): String = "habitos/formulario/$id"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.habitNavGraph(
    userId: Long,
    navController: NavController
) {
    composable(HabitoDestinos.Lista.ruta) {

        val viewModel: HabitViewModel = koinViewModel()

        // ✅ Asegura que los hábitos se cargan al entrar a la pantalla
        LaunchedEffect(userId) {
            viewModel.observarHabitos(userId)
        }

        HabitScreen(
            userId = userId,
            navController = navController,
            onAgregarClick = {
                navController.navigate(HabitoDestinos.Formulario.ruta)
            },
            onEditar = { id ->
                navController.navigate(HabitoDestinos.FormularioEditar.editar(id))
            }
        )
    }

    composable(HabitoDestinos.Formulario.ruta) {
        val viewModel: HabitViewModel = koinViewModel()

        FormHabitScreen(
            userId = userId,
            onGuardar = { habito ->
                viewModel.crearHabito(
                    userId = habito.idUsuario,
                    titulo = habito.titulo,
                    tipo = habito.tipo.name,
                    frecuencia = habito.frecuencia,
                    periodo = habito.periodo.name
                )
                navController.popBackStack()
            },
            onCancelar = { navController.popBackStack() }
        )
    }

    composable(
        route = HabitoDestinos.FormularioEditar.ruta,
        arguments = listOf(navArgument("habitoId") { type = NavType.LongType })
    ) { backStackEntry ->
        val habitoId = backStackEntry.arguments?.getLong("habitoId") ?: return@composable
        val viewModel: HabitViewModel = koinViewModel()

        var habito by remember { mutableStateOf<Habito?>(null) }
        val isLoading by remember { derivedStateOf { habito == null } }

        LaunchedEffect(habitoId) {
            habito = viewModel.cargarHabitoPorId(habitoId)
        }

        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            FormHabitScreen(
                userId = userId,
                habitoExistente = habito,
                onGuardar = {
                    viewModel.actualizarHabito(it)
                    navController.popBackStack()
                },
                onCancelar = { navController.popBackStack() },
                onEliminar = {
                    viewModel.eliminarHabito(habitoId)
                    navController.popBackStack()
                }
            )
        }
    }
}