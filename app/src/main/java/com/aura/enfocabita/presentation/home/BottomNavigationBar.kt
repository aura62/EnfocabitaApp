package com.aura.enfocabita.presentation.home

import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        HomeDestination.items.forEach { destination ->
            NavigationBarItem(
                selected = currentRoute?.startsWith(destination.route) == true,
                onClick = {
                    if (currentRoute?.startsWith(destination.route) != true) {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                label = { Text(destination.label) },
                icon = { Icon(destination.icon, contentDescription = destination.label) }
            )
        }
    }
}