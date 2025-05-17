package com.aura.enfocabita.presentation.home

import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        HomeDestination.items.forEach { destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = {
                    if (currentRoute != destination.route) {
                        navController.navigate(destination.route) {
                            popUpTo(HomeDestination.Inicio.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                },
                label = { Text(destination.label) },
                icon = { Icon(androidx.compose.material.icons.Icons.Default.Home, contentDescription = destination.label) }
            )
        }
    }
}